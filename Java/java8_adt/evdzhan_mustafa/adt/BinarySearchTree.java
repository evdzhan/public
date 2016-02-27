package evdzhan_mustafa.adt;

import java.util.function.Consumer;

/**
 * By Evdzhan Mustafa. Created on 27/02/16.
 */
public class BinarySearchTree<T extends Comparable<? super T>> {

  public static void main(String[] args) {
    BinarySearchTree<Integer> tree = new BinarySearchTree<>();

    tree.insert(5);

    tree.insert(3);
    tree.insert(7);

    tree.insert(4);
    tree.insert(2);

    tree.insert(6);
    tree.insert(8);

    tree.traverseTree(System.out::println);


  }

  private TreeNode root = null;

  public BinarySearchTree() {
  }

  public void traverseTree(Consumer<T> func) {
    traverse(root, func);
  }

  private void traverse(TreeNode node, Consumer<T> func) {

    if (node == null) return;

    func.accept(node.data);

    traverse(node.left, func);
    traverse(node.right, func);

  }

  public void insert(T data) {
    if (root != null) {
      root.insert(data);
    } else {
      root = new TreeNode(null, null, data);
    }
  }


  private class TreeNode {
    TreeNode left = null;
    TreeNode right = null;
    T data;
    long count = 1;

    TreeNode(TreeNode left, TreeNode right, T data) {
      this.left = left;
      this.right = right;
      this.data = data;
    }


    void insert(T data) {
      int comparison = this.data.compareTo(data);

      if (comparison == 0) {
        count++;
      } else if (comparison > 0) {
        if (left == null)
          left = new TreeNode(null, null, data);
        else
          left.insert(data);

      } else {
        if (right == null)
          right = new TreeNode(null, null, data);
        else
          right.insert(data);

      }
    }
  }
}
