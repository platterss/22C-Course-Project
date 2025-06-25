import java.util.Comparator;
import java.util.NoSuchElementException;

public class BST<T> {
    private class Node {
        private T data;
        private Node left;
        private Node right;

        public Node(T data) {
            this.data = data;
            left = null;
            right = null;
        }
    }

    private Node root;

    // **** CONSTRUCTORS **** //
    /**
     * Creates a new BST
     */
    public BST() {
        root = null;
    }

    /**
     * Creates a BST of minimal height from the given array
     * @param array the list of values to insert.
     * @param cmp the way the tree is organized.
     * @precondition array must be sorted in ascending order
     * @throws IllegalArgumentException when the array is unsorted.
     */
    public BST(T[] array, Comparator<T> cmp) {
        this();

        if (array == null) {
            return;
        }

        if (!isSorted(array, cmp)) {
            throw new IllegalArgumentException("BST(T[], Comparator<T>): array is not sorted");
        }

        root = arrayHelper(0, array.length - 1, array);
    }

    /**
     * Recursive helper for the array constructor.
     * @param begin the beginning array index
     * @param end the ending array index
     * @param array the array to search
     * @return the newly created Node
     */
    private Node arrayHelper(int begin, int end, T[] array) {
        if (begin > end) {
            return null;
        }

        int mid = begin + (end - begin) / 2;
        Node node = new Node(array[mid]);

        node.left = arrayHelper(begin, mid - 1, array);
        node.right = arrayHelper(mid + 1, end, array);

        return node;
    }

    /**
     * Copy constructor for the BST
     * @param bst the BST of which to make a copy
     * @param c the way the tree is organized
     */
    public BST(BST<T> bst, Comparator<T> c) {
        this();

        if (bst == null) {
            return;
        }

        copyHelper(bst.root, c);
    }

    /**
     * Helper method for copy constructor
     * @param node the node containing data to copy
     * @param cmp the way the tree is organized
     */
    private void copyHelper(Node node, Comparator<T> cmp) {
        if (node == null) {
            return;
        }

        insert(node.data, cmp);
        copyHelper(node.left, cmp);
        copyHelper(node.right, cmp);
    }

    // **** ACCESSORS **** //
    /**
     * Returns the data stored in the root.
     * @precondition !isEmpty()
     * @return the data stored in the root.
     * @throws NoSuchElementException when precondition is violated.
     */
    public T getRoot() {
        if (isEmpty()) {
            throw new NoSuchElementException("getRoot(): BST is empty");
        }

        return root.data;
    }

    /**
     * Returns the height of the tree as an int
     * @return the height of the tree
     */
    public int getHeight() {
        return getHeight(root);
    }

    /**
     * Helper method for getHeight().
     * @param node the current node whose height to count
     * @return the height of the tree
     */
    private int getHeight(Node node) {
        if (node == null) {
            return -1;
        }

        int leftHeight = getHeight(node.left);
        int rightHeight = getHeight(node.right);

        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * Searches for a specified value in the tree.
     * @param data the value to search for
     * @param cmp the Comparator that indicates the way the data in the tree was ordered.
     * @return the data stored in the Node of the tree, otherwise null
     */
    public T search(T data, Comparator<T> cmp) {
        return search(data, root, cmp);
    }

    /**
     * Helper method for the search() method.
     * @param data the value to search for.
     * @param node the current node to check.
     * @param cmp the Comparator that indicates the way the data in the tree was ordered/
     * @return the  data stored in that Node of thetree, otherwise null
     */
    private T search(T data, Node node, Comparator<T> cmp) {
        if (node == null) {
            return null;
        }

        int comparison = cmp.compare(data, node.data);

        if (comparison == 0) {
            return node.data;
        }

        if (comparison > 0) {
            return search(data, node.right, cmp);
        }

        return search(data, node.left, cmp);
    }

    /**
     * Returns the current size of the tree (number of nodes).
     * @return the size of the tree.
     */
    public int getSize() {
        return getSize(root);
    }

    /**
     * Helper method for the getSize() method.
     * @param node the current node to count.
     * @return the size of the tree.
     */
    private int getSize(Node node) {
        if (node == null) {
            return 0;
        }

        return 1 + getSize(node.left) + getSize(node.right);
    }

    /**
     * Determines whether the tree is empty.
     * @return whether the tree is empty.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Returns the smallest value in the tree
     * @precondition !isEmpty()
     * @return the smallest value in the tree
     * @throws NoSuchElementException when the precondition is violated
     */
    public T findMin() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("findMin(): BST is empty");
        }

        return findMin(root);
    }

    /**
     * Recursive helper method to findMin method
     * @param node the current node to check if it is the smallest
     * @return the smallest value in the tree
     */
    private T findMin(Node node) {
        if (node.left == null) {
            return node.data;
        }

        return findMin(node.left);
    }

    /**
     * Returns the largest value in the tree.
     * @precondition !isEmpty()
     * @return the largest value in the tree
     * @throws NoSuchElementException when the precondition is violated
     */
    public T findMax() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("findMax(): BST is empty");
        }

        return findMax(root);
    }

    /**
     * Recursive helper method to findMax method
     * @param node the current node to check if it is the largest
     * @return the largest value in the tree
     */
    private T findMax(Node node) {
        if (node.right == null) {
            return node.data;
        }

        return findMax(node.right);
    }

    // **** MUTATORS **** //
    /**
     * Inserts a new node in the tree.
     * @param data the data to insert
     * @param cmp the Comparator indicating how much data in the tree is ordered
     */
    public void insert(T data, Comparator<T> cmp) {
        if (root == null) {
            root = new Node(data);
        } else {
            insert(data, root, cmp);
        }
    }

    /**
     * Inserts a new value in the tree recursively.
     * Helper method for insert().
     * @param data the data to insert
     * @param node the current node
     * @param cmp the Comparator indicating how much data in the tree is ordered
     */
    private void insert(T data, Node node, Comparator<T> cmp) {
        if (cmp.compare(data, node.data) > 0) {
            if (node.right == null) {
                node.right = new Node(data);
            } else {
                insert(data, node.right, cmp);
            }
        } else {
            if (node.left == null) {
                node.left = new Node(data);
            } else {
                insert(data, node.left, cmp);
            }
        }
    }

    /**
     * Removes a value from the BST
     * @precondition !isEmpty()
     * @param data the value to remove
     * @param cmp the Comparator indicating how much data in the tree is organized
     * @throws NoSuchElementException when the precondition is violated
     */
    public void remove(T data, Comparator<T> cmp) throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("remove(): BST is empty");
        }

        root = remove(data, root, cmp);
    }

    /**
     * Helper method to the remove method
     * @param data the data to remove
     * @param node the current node
     * @param cmp the Comparator indicating how much data in the tree is organized
     * @return an updated reference
     */
    private Node remove(T data, Node node, Comparator<T> cmp) {
        if (node == null) {
            return node;
        }

        if (cmp.compare(data, node.data) < 0) {
            node.left = remove(data, node.left, cmp);
        } else if (cmp.compare(data, node.data) > 0) {
            node.right = remove(data, node.right, cmp);
        } else {
            if (node.left == null && node.right == null) {
                node = null;
            } else if (node.right == null) {
                node = node.left;
            } else if (node.left == null) {
                node = node.right;
            } else {
                T min = findMin(node.right);
                node.data = min;
                node.right = remove(min, node.right, cmp);
            }
        }

        return node;
    }

    // **** ADDITONAL OPERATIONS **** //
    /**
     * Determines whether the array is sorted.
     * @param array the array to check
     * @param cmp the Comparator indicating how the data is sorted
     * @return whether the array is sorted.
     */
    public boolean isSorted(T[] array, Comparator<T> cmp) {
        if (array == null || array.length < 2) {
            return true;
        }

        for (int i = 0; i < array.length - 1; i++) {
            if (cmp.compare(array[i], array[i + 1]) > 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Creates a String that is a height order
     * traversal of the data in the tree starting at
     * the Node with the largest height (the root)
     * down to Nodes of smallest height - with
     * Nodes of equal height added from left to right.
     * @return the level order traversal as a String
     */
    public String levelOrderString() {
        Queue<Node> queue = new Queue<>();
        StringBuilder stringBuilder = new StringBuilder();

        queue.enqueue(root);
        levelOrderString(queue, stringBuilder);

        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    /**
     * Helper method to levelOrderString.
     * Inserts the data in level order into a String.
     * @param queue the Queue in which to store the data.
     * @param heightTraverse a StringBuilder containing the data.
     */
    private void levelOrderString(Queue<Node> queue, StringBuilder heightTraverse) {
        if (!queue.isEmpty()) {
            Node node = queue.getFront();
            queue.dequeue();
            if (node != null) {
                queue.enqueue(node.left);
                queue.enqueue(node.right);
                heightTraverse.append(node.data).append(" ");
            }
            levelOrderString(queue, heightTraverse);
        }
    }

    /**
     * Returns a string containing the data in preorder
     * @return a String of data in preorder
     */
    public String preOrderString() {
        StringBuilder str = new StringBuilder();

        preOrderString(root, str);
        str.append("\n");

        return str.toString();
    }

    /**
     * Returns a String containing the data in order
     * @return a String containing the data in order
     */
    public String inOrderString() {
        StringBuilder str = new StringBuilder();

        inOrderString(root, str);

        return str.toString();
    }

    /**
     * Returns a String containing the data in post order
     * @return a String containing the data in post order
     */
    public String postOrderString() {
        StringBuilder str = new StringBuilder();

        postOrderString(root, str);
        str.append("\n");

        return str.toString();
    }

    /**
     * Helper method to preOrderString method
     * @param node the current node
     * @param preOrder a StringBuilder containing the data
     */
    private void preOrderString(Node node, StringBuilder preOrder) {
        if (node == null) {
            return;
        }

        preOrder.append(node.data).append(" ");
        preOrderString(node.left, preOrder);
        preOrderString(node.right, preOrder);
    }

    /**
     * Helper method for inOrderString
     * @param node the current node
     * @param inOrder a StringBuilder containing the data
     */
    private void inOrderString(Node node, StringBuilder inOrder) {
        if (node == null) {
            return;
        }

        inOrderString(node.left, inOrder);
        inOrder.append(node.data).append("\n");
        inOrderString(node.right, inOrder);
    }

    /**
     * Helper method for postOrderString
     * @param node the current node
     * @param postOrder a StringBuilder containing the data
     */
    private void postOrderString(Node node, StringBuilder postOrder) {
        if (node == null) {
            return;
        }

        postOrderString(node.left, postOrder);
        postOrderString(node.right, postOrder);
        postOrder.append(node.data).append(" ");
    }
}
