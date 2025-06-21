import java.util.NoSuchElementException;

public class Queue<T> {
    private class Node {
        private final T data;
        private Node next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private int size;
    private Node front;
    private Node end;

    /**
     * Default constructor for a Queue object.
     */
    public Queue() {
        this.size = 0;
        this.front = null;
        this.end = null;
    }

    /**
     * Array constructor for a Queue object.
     * @param data the array to create a Queue object on
     */
    public Queue(T[] data) {
        this();

        if (data == null) {
            return;
        }

        for (T element : data) {
            enqueue(element);
        }
    }

    /**
     * Copy constructor for a Queue object.
     * @param queue the Queue object to copy
     */
    public Queue(Queue<T> queue) {
        this();

        if (queue == null) {
            return;
        }

        Node currentNode = queue.front;

        while (currentNode != null) {
            this.enqueue(currentNode.data);
            currentNode = currentNode.next;
        }
    }

    /**
     * Returns the front element of the queue.
     * @precondition !isEmpty()
     * @return the front element of the queue.
     */
    public T getFront() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("getFront(): Queue is empty, no first element exists");
        }

        return front.data;
    }

    /**
     * Returns the size of the queue.
     * @return the current size of the queue
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns whether the queue is empty.
     * @return whether the queue has no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Adds a new element to the end of the queue.
     * @param data the object to add to the end of the queue
     * @postcondition a new element is added to the end of the queue.
     */
    public void enqueue(T data) {
        Node node = new Node(data);

        if (front == null && end == null) {
            front = end = node;
        } else if (front == end) {
            front.next = node;
            end = node;
        } else {
            end.next = node;
            end = node;
        }

        size++;
    }

    /**
     * Removes the front element from the queue.
     * @precondition !isEmpty()
     * @postcondition the front element of the queue is removed
     */
    public void dequeue() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("dequeue(): Queue is empty, no first element to dequeue");
        }

        if (front == end) {
            front = end = null;
        } else {
            front = front.next;
        }

        size--;
    }

    /**
     * Returns the string representation of the Queue.
     * @return the queue as a string
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        Node currentNode = front;

        while (currentNode != null) {
            str.append(currentNode.data).append(" ");
            currentNode = currentNode.next;
        }

        str.append('\n');

        return str.toString();
    }

    /**
     * Returns whether the given object equals this Queue.
     * @param obj the object to test for equality
     * @return whether the two objects are equal
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Queue)) {
            return false;
        }

        Queue<T> other = (Queue<T>) obj;

        if (this.size != other.size) {
            return false;
        }

        Node thisNode = this.front;
        Node otherNode = other.front;

        while (thisNode != null) {
            if (!thisNode.data.equals(otherNode.data)) {
                return false;
            }

            thisNode = thisNode.next;
            otherNode = otherNode.next;
        }

        return true;
    }
}