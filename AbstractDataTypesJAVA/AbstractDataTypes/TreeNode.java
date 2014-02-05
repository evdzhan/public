package mustafa.evdzhan.AbstractDataTypes;

public class TreeNode {

    private Object data;
	private TreeNode left;
	private TreeNode right;
	private int key;
	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}
	/**
	 * @return the left
	 */
	public TreeNode getLeft() {
		return left;
	}
	/**
	 * @param left the left to set
	 */
	public void setLeft(TreeNode left) {
		this.left = left;
	}
	/**
	 * @return the right
	 */
	public TreeNode getRight() {
		return right;
	}
	/**
	 * @param right the right to set
	 */
	public void setRight(TreeNode right) {
		this.right = right;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(int key) {
		this.key = key;
	}
 public TreeNode (int key,Object data){
		this.key=key;
		this.data=data;
	}
	public String toString() {
		return data.toString();
	}
	
	public int getKey() {
		return key;
	}
}
