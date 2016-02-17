package abtract_data_types;

/**
 * A AVL tree class. A rewritten version of Mark Allen Weiss's AVLTree.
 *
 * @param <T> must implement comparable
 * @author Evdzhan Mustafa enm3@aber.ac.uk
 */

public class AVLTree<T extends Comparable<? super T>> {

    private AVLNode<T> root;

    public static void main(String[] args) {
        AVLTree<Integer> t = new AVLTree<>();
        t.insert(50);
        t.insert(30);
        t.insert(80);
        t.insert(40);
        t.insert(60);
        t.insert(90);
        t.insert(95);
        t.insert(96);
        t.insert(97);
        t.insert(99);
        t.insert(75);
        t.onOrderTraverse(t.root);
    }

    public void insert(T data) {
        root = this.insert(root, data);

    }

    private AVLNode<T> insert(AVLNode<T> parent, T data) {
        if (parent == null) {
            return new AVLNode<>(data, null, null);
        }  // base case

        int comparison = data.compareTo(parent.data);

        if (comparison < 0) {
            parent.left = insert(parent.left, data);
        } else if (comparison > 0) {
            parent.right = insert(parent.right, data);
        } else {
            throw new Error("must fix.");
        }

        return balanceTree(parent);
    }

    private AVLNode<T> balanceTree(AVLNode<T> root) {

        if (root == null) {
            return null;
        }

		/* determine if left is higher than the right */
        if (getHeight(root.left) - getHeight(root.right) > 1) {

			/* determine which left subtree makes the imbalance  */
            if (getHeight(root.left.left) >= getHeight(root.left.right)) {

                root = this.singleRotateLeft(root); // LEFT LEFT imbalance

            } else {

                root = this.doubleRotateLeft(root); // LEFT RIGHT imbalance
            }
			 
			 /* determine if right is higher than the left */
        } else if (getHeight(root.right) - getHeight(root.left) > 1) {
			 
			 /* determine which right subtree makes the imbalance  */
            if (getHeight(root.right.right) >= getHeight(root.right.left)) {

                root = this.singleRotateRight(root);  // RIGHT RIGHT imbalance
            } else {
                root = this.doubleRotateRight(root);  // RIGHT LEFT imbalance
            }
        }

        root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
        return root;
    }

    private AVLNode<T> doubleRotateRight(AVLNode<T> root) {
        root.right = singleRotateLeft(root.right);
        return singleRotateRight(root);
    }

    private AVLNode<T> doubleRotateLeft(AVLNode<T> root) {
        root.left = singleRotateRight(root.left);
        return singleRotateLeft(root);
    }

    private int getHeight(AVLNode<T> node) {

        return node == null ? -1 : node.height;
    }

    private AVLNode<T> singleRotateRight(AVLNode<T> root) {
        AVLNode<T> newRoot = root.right;
        root.right = newRoot.left;
        newRoot.left = root;
        root.height = Math.max(getHeight(root.right), getHeight(root.left)) + 1;
        newRoot.height = Math.max(getHeight(newRoot.right), root.height) + 1;
        return newRoot;
    }

    private AVLNode<T> singleRotateLeft(AVLNode<T> root) {
        AVLNode<T> newRoot = root.left;
        root.left = newRoot.right;
        newRoot.right = root;
        root.height = Math.max(getHeight(root.left), getHeight(newRoot)) + 1;
        newRoot.height = Math.max(root.height, getHeight(newRoot.right)) + 1;
        return newRoot;
    }

    private void onOrderTraverse(AVLNode<T> t) {
        if (t != null) {
            onOrderTraverse(t.left);

            System.out.println(t.data);

            onOrderTraverse(t.right);
        }
    }

    @SuppressWarnings("unused")
    private static class AVLNode<T> {


        AVLNode<T> left;
        AVLNode<T> right;
        T data;
        int height;

        AVLNode(T data) {
            this(data, null, null);

        }

        AVLNode(T data, AVLNode<T> left, AVLNode<T> right) {
            this.left = left;   // the left child
            this.right = right;  // the right child
            this.data = data;   // the data stored in the node
            this.height = 0;      // the Height
        }

        public String toString() {
            return data.toString();
        }
    }
}

		