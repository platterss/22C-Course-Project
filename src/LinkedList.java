import java.util.NoSuchElementException;

public class LinkedList<T> {
    private class Node {
        private final T data;
        private Node next;
        private Node prev;

        public Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    private int length;
    private Node first;
    private Node last;
    private Node iterator;

    // **** CONSTRUCTORS **** //

    /**
     * Instantiates a new LinkedList with default values
     * @postcondition LinkedList is set up and empty
     */
    public LinkedList() {
        first = null;
        last = null;
        iterator = null;
        length = 0;
    }

    /**
     * Converts the given array into a LinkedList
     * @param array an array of values
     * @postcondition a new LinkedList object will be created from the array
     */
    public LinkedList(T[] array) {
        this();

        if (array == null) {
            return;
        }

        for (T element : array) {
            addLast(element);
        }
    }

    /**
     * Instantiates a new LinkedList by copying another List
     * @param original the LinkedList to copy
     * @postcondition an identical copy to the original LinkedList
     */
    public LinkedList(LinkedList<T> original) {
        this();

        if (original != null && original.length != 0) {
            Node current = original.first;

            while (current != null) {
                addLast(current.data);
                current = current.next;
            }
        }
    }

    // **** ACCESSORS **** //
    /**
     * Returns the value stored in the first node
     * @precondition LinkedList is not empty
     * @return the value stored at node first
     * @throws NoSuchElementException when LinkedList is empty
     */
    public T getFirst() throws NoSuchElementException {
        if (length == 0) {
            throw new NoSuchElementException("getFirst(): List is empty. No first element.");
        }

        return first.data;
    }

    /**
     * Returns the value stored in the last node
     * @precondition the LinkedList is not empty
     * @return the value stored in the node last
     * @throws NoSuchElementException when LinkedList is empty
     */
    public T getLast() throws NoSuchElementException {
        if (length == 0) {
            throw new NoSuchElementException("getLast(): List is empty. No last element");
        }

        return last.data;
    }

    /**
     * Returns the data stored in the iterator node
     * @precondition iterator is not off end
     * @return the data stored in the iterator node
     * @throws NullPointerException if iterator is off end
     */
    public T getIterator() throws NullPointerException {
        if (offEnd()) {
            throw new NullPointerException("getIterator(): iterator is off end");
        }

        return iterator.data;
    }

    /**
     * Returns the current length of the LinkedList
     * @return the length of the LinkedList from 0 to n
     */
    public int getLength() {
        return length;
    }

    /**
     * Returns whether the LinkedList is currently empty
     * @return whether the LinkedList is empty
     */
    public boolean isEmpty() {
        return length == 0;
    }

    /**
     * Returns whether the iterator is offEnd, i.e. null
     * @return whether the iterator is null
     */
    public boolean offEnd() {
        return iterator == null;
    }

    // **** MUTATORS **** //
    /**
     * Adds a new element to the beginning
     * @param data the data to insert at the front of the LinkedList
     * @postcondition a new element is added to the front of the LinkedList
     */
    public void addFirst(T data) {
        if (length == 0) {
            first = last = new Node(data);
        } else {
            Node newNode = new Node(data);
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
        }

        length++;
    }

    /**
     * Adds a new element to the end
     * @param data the data to insert at the end of the LinkedList
     * @postcondition a new element is added to the end of the LinkedList
     */
    public void addLast(T data) {
        if (length == 0) {
            first = last = new Node(data);
        } else {
            Node newNode = new Node(data);
            newNode.prev = last;
            last.next = newNode;
            last = newNode;
        }

        length++;
    }

    /**
     * Inserts a new element after the iterator
     * @param data the data to insert
     * @precondition iterator is not off end
     * @throws NullPointerException if iterator is off end
     */
    public void addIterator(T data) throws NullPointerException {
        if (offEnd()) {
            throw new NullPointerException("addIterator(): iterator is off end");
        }

        Node newNode = new Node(data);
        newNode.next = iterator.next;
        newNode.prev = iterator;

        if (iterator.next != null) {
            iterator.next.prev = newNode;
        } else {
            last = newNode;
        }

        iterator.next = newNode;

        length++;
    }

    /**
     * Removes the element at the front of the LinkedList
     * @precondition LinkedList is not empty
     * @postcondition the first element of the LinkedList is removed
     * @throws NoSuchElementException when LinkedList is empty
     */
    public void removeFirst() throws NoSuchElementException {
        if (length == 0) {
            throw new NoSuchElementException("removeFirst(): Cannot remove from an empty list");
        }

        if (length == 1) {
            first = last = null;
        } else {
            first = first.next;
            first.prev = null;
        }

        length--;
    }

    /**
     * Removes the element at the end of the LinkedList
     * @precondition LinkedList is not empty
     * @postcondition the last element of the LinkedList is removed
     * @throws NoSuchElementException when LinkedList is empty
     */
    public void removeLast() throws NoSuchElementException {
        if (length == 0) {
            throw new NoSuchElementException("removeFirst(): Cannot remove from an empty list");
        }

        if (length == 1) {
            first = last = null;
        } else {
            last = last.prev;
            last.next = null;
        }

        length--;
    }

    /**
     * Removes the element referenced by the iterator
     * @precondition iterator is not off end
     * @postcondition iterator's element is removed from the LinkedList and is pointing to the next
     * @throws NullPointerException if iterator is off end
     */
    public void removeIterator() throws NullPointerException {
        if (offEnd()) {
            throw new NullPointerException("removeIterator(): iterator is off end");
        }

        Node temp = iterator;

        if (temp == first) {
            first = first.next;

            if (first != null) {
                first.prev = null;
            } else {
                last = null;
            }
        } else if (temp == last) {
            last = last.prev;

            if (last != null) {
                last.next = null;
            }
        } else {
            temp.prev.next = temp.next;
            temp.next.prev = temp.prev;
        }

        iterator = null;

        length--;
    }

    /**
     * Places the iterator at the first node
     * @postcondition iterator is placed at the first node
     */
    public void positionIterator() {
        iterator = first;
    }

    /**
     * Moves the iterator one node towards the last
     * @precondition iterator is not off end
     * @postcondition iterator gets moved one node towards the last
     * @throws NullPointerException if iterator is already at the end
     */
    public void advanceIterator() throws NullPointerException {
        if (offEnd()) {
            throw new NullPointerException("advanceIterator(): already reached end of list");
        }

        iterator = iterator.next;
    }

    /**
     * Moves the iterator one node towards the first
     * @precondition iterator is not off end
     * @postcondition iterator is moved to the left
     * @throws NullPointerException if the iterator is off end
     */
    public void reverseIterator() throws NullPointerException {
        if (offEnd()) {
            throw new NullPointerException("reverseIterator(): iterator is off end");
        }

        iterator = iterator.prev;
    }

    // **** ADDITIONAL OPERATIONS **** //
    /**
     * Returns each element in the LinkedList along with its numerical position from 1 to n
     * @return the numbered linkedList elements as a String
     */
    public String numberedListString() {
        StringBuilder str = new StringBuilder();

        int index = 1;
        for (Node currentNode = first; currentNode != null; currentNode = currentNode.next, index++) {
            str.append(index).append(". ").append(currentNode.data).append("\n");
        }

        str.append("\n");
        return str.toString();
    }

    /**
     * Resets LinkedList to empty
     */
    public void clear() {
        first = null;
        last = null;
        iterator = null;
        length = 0;
    }

    /**
     * Converts the LinkedList to a String, with each value separated by a blank
     * line At the end of the String, place a new line character
     * @return the LinkedList as a String
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        Node temp = first;

        while (temp != null) {
            result.append(temp.data).append(" ");
            temp = temp.next;
        }

        return result.toString() + '\n';
    }

    /**
     * Determines whether the given Object is equal to this LinkedList
     * @param obj another Object
     * @return whether there is equality
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof LinkedList)) {
            return false;
        }

        LinkedList<T> other = (LinkedList<T>) obj;

        if (length != other.length) {
            return false;
        }

        Node temp1 = first;
        Node temp2 = other.first;

        while (temp1 != null) {
            if (temp1.data == null || temp2.data == null) {
                if (temp1.data != temp2.data) {
                    return false;
                }
            } else if (!temp1.data.equals(temp2.data)) {
                return false;
            }

            temp1 = temp1.next;
            temp2 = temp2.next;
        }

        return true;
    }

    /**
     * Searches the LinkedList for a given element's index
     * @param data the data whose index to locate
     * @return the index of the data or -1 if the data is not contained in the LinkedList
     */
    public int find(T data) {
        int index = 0;

        for (Node currentNode = first; currentNode != null; currentNode = currentNode.next, index++) {
            if (data.equals(currentNode.data)) {
                return index;
            }
        }

        return -1;
    }

    /**
     * Advances the iterator to the given index
     * @precondition index >= 0, index < length
     * @param index the index to place the iterator
     * @throws IndexOutOfBoundsException when the index is out of bounds
     */
    public void advanceIteratorToIndex(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("advanceIteratorToIndex(" + index + "): index is out of bounds");
        }

        positionIterator();
        for (int i = 0; i < index; i++) {
            advanceIterator();
        }
    }
}