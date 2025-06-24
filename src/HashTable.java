import java.util.ArrayList;

public class HashTable<T> {
    private int numElements;
    private ArrayList<LinkedList<T>> table;

    // **** CONSTRUCTORS **** //
    /**
     * Creates an empty HashTable
     * @param size the size of the HashTable
     * @throws IllegalArgumentException when size is negative
     */
    public HashTable(int size) throws IllegalArgumentException {
        if (size < 0) {
            throw new IllegalArgumentException("Hashtable cannot have a negative size");
        }

        table = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            table.add(new LinkedList<>());
        }

        numElements = 0;
    }

    /**
     * Array constructor for a HashTable
     * @param array an array of elements to insert
     * @param size the size of the table
     * @precondition size > 0
     * @throws IllegalArgumentException when size <= 0
     */
    public HashTable(T[] array, int size) throws IllegalArgumentException {
        this(size);

        if (size <= 0) {
            throw new IllegalArgumentException("Hashtable array constructor cannot have negative or zero size");
        }

        if (array == null) {
            return;
        }

        for (T element : array) {
            add(element);
        }
    }

    // **** ACCESSORS **** //
    /**
     * Returns the number of elements in the HashTable
     * @return the number of elements
     */
    public int getNumElements() {
        return numElements;
    }

    /**
     * Accesses a specified element in the table
     * @param element the element to locate
     * @precondition element != null
     * @return the bucket number where the element is located or -1 if it is not found
     * @throws NullPointerException when the precondition is violated
     */
    public int find(T element) throws NullPointerException {
        if (element == null) {
            throw new NullPointerException("find(...): element cannot be null");
        }

        int index = hash(element);
        LinkedList<T> list = table.get(index);

        if (list.find(element) != -1) {
            return index;
        }

        return -1;
    }

    /**
     * Accesses a specified key in the HashTable
     * @param element the key to search for
     * @precondition element != null
     * @return the value to which the specified key is mapped, or null if this table contains no mapping for the key
     * @throws NullPointerException when the precondition is violated
     */
    public T get(T element) {
        if (element == null) {
            throw new NullPointerException("get(...): element cannot be null");
        }

        if (numElements == 0) {
            return null;
        }

        int bucketIndex = hash(element);
        LinkedList<T> bucket = table.get(bucketIndex);

        int index = bucket.find(element);
        if (index == -1) {
            return null;
        }

        bucket.advanceIteratorToIndex(index);
        return bucket.getIterator();
    }

    /**
     * Counts the number of elements at this index
     * @param index the index in the table
     * @precondition 0 <= index < table.size()
     * @return the count of elements at this index
     * @throws IndexOutOfBoundsException when the precondition is violated
     */
    public int countBucket(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= table.size()) {
            throw new IndexOutOfBoundsException("countBucket(" + index + "): index is out of bounds");
        }

        return table.get(index).getLength();
    }

    /**
     * Determines whether a specified element is in the table
     * @param element the element to locate
     * @precondition element != null
     * @throws NullPointerException when the precondition is violated
     * @return whether the element is in the table
     */
    public boolean contains(T element) throws NullPointerException {
        if (element == null) {
            throw new NullPointerException("contains(): element cannot be null");
        }

        return find(element) != -1;
    }

    /**
     * Returns the hash of a given object
     * @param obj the object to get the hash of
     * @return the object's hash
     */
    private int hash(T obj) {
        return Math.abs(obj.hashCode()) % table.size();
    }

    // **** MUTATORS **** //
    /**
     * Adds a new element to the HashTable
     * @param element the element to add
     * @precondition element != null
     * @throws NullPointerException when the precondition is violated
     */
    public void add(T element) {
        if (element == null) {
            throw new NullPointerException("add(): element is null");
        }

        numElements++;
        LinkedList<T> bucket = table.get(hash(element));
        bucket.addLast(element);
    }

    /**
     * Removes the given element from the table
     * @param element the element to remove
     * @precondition element != null
     * @return whether element exists and was removed from the table
     * @throws NullPointerException when the precondition is violated
     */
    public boolean delete(T element) throws NullPointerException {
        if (element == null) {
            throw new NullPointerException("delete(): element cannot be null");
        }

        int index = find(element);

        if (index == -1) {
            return false;
        }

        LinkedList<T> list = table.get(index);
        int listIndex = list.find(element);
        list.advanceIteratorToIndex(listIndex);
        list.removeIterator();
        numElements--;

        return true;
    }

    /**
     * Resets the HashTable to an empty state
     */
    public void clear() {
        numElements = 0;
        int oldSize = table.size();
        table = new ArrayList<>();

        for (int i = 0; i < oldSize; i++) {
            table.add(new LinkedList<>());
        }
    }

    // **** ADDITIONAL METHODS **** //
    /**
     * Gets the load factor
     * @return the load factor
     */
    public double getLoadFactor() {
        return (double) numElements / table.size();
    }

    /**
     * Returns a bucket's string
     * @param bucket the index in the table
     * @precondition 0 <= bucket < table.size()
     * @return a String of elements, separated by spaces with a new line character at the end
     * @throws IndexOutOfBoundsException when the bucket is out of bounds
     */
    public String bucketToString(int bucket) {
        if (bucket < 0 || bucket >= table.size()) {
            throw new IndexOutOfBoundsException("bucketToString(" + bucket + "): bucket is out of bounds");
        }

        return table.get(bucket).toString();
    }

    /**
     * Creates a String of the bucket number and first element at each bucket
     * @return a string of the bucket number and first element
     */
    public String rowToString() {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < table.size(); i++) {
            str.append("Bucket ").append(i).append(": ");

            LinkedList<T> list = table.get(i);

            if (list.getLength() == 0) {
                str.append("empty");
            } else {
                str.append(list.getFirst().toString());
            }

            str.append("\n");
        }

        return str.toString();
    }

    /**
     * Returns a string of the HashTable
     * @return a string of the HashTable
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for (LinkedList<T> list : table) {
            if (list.getLength() != 0) {
                str.append(list);
            }
        }

        str.append("\n");
        return str.toString();
    }
}
