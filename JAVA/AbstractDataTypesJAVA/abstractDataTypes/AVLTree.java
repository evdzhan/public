package abstractDataTypes;

public class AVLTree<E extends Comparable<E>> {

	 public AVLNode<E> root;
	public static final int LEFT_HIGH  = -1 ;
	public static final int RIGHT_HIGH =  1 ;
	public static final int EQUAL_HIGH =  0 ;
	public AVLTree() {
		root = null;
	}
	
	public void insert(E data) {
		AVLNode<E> node = new AVLNode<>(data);
		
		if(root == null) {
			root = node;
		} else {
			
			AVLNode<E> current = root;
			AVLNode<E> parent ;
			
			while(true) {
				parent = current;
				
				if(node.compareTo(parent) <= 0){
					current.balanceFactor += LEFT_HIGH;
					current = parent.left;
					if(current == null) {
						if(root.balanceFactor > 1) {
							
						}
						parent.left=node;
						return;
					}
					
					
				} else {
					current.balanceFactor += RIGHT_HIGH;
					current = parent.right;
					if(current == null) {
						parent.right=node;
						return;
					}
					
					
				}
					
			}
			
			
			
			
		}
		
		
	}
	
	public void orderTraverse(AVLNode<E> node ) {
    	if(node != null) {
    		
    		System.out.println(node.toString());
    		
    		orderTraverse(node.left);
    		
    	    orderTraverse(node.right);
    	
    	
    	}
    }
	
	
	
	
	
	
	
	
	
	
	
	
}