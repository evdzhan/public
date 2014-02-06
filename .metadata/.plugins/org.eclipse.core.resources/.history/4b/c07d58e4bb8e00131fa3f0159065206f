package mustafa.evdzhan.AbstractDataTypes;

 
 


public class LinkedList {

	private Node first = new Node();
	
	
	/* Checks to see if the linked list is empty */
	public boolean isEmpty(){
		 return first.getNext() == null;
		
	}
	/**  @return the first element in the list  */
	public Node getFront() {
		if(isEmpty()) { throw new Error("Linked list empty"); }
		return first.getNext();
	}
	/**  @return the last element in the list  */
	public Node getBack() {
		if(isEmpty()) { throw new Error("Linked list empty"); }
	  Node node= first.getNext();
	  while(node.getNext() != null) {
		  node=node.getNext();
	  }
	 return node;
	}
	/** Adds to front. If the Linked list has no elements, the added one becomes first and last. */
	public void addToFront(Object data) {
	    Node node = new Node();
		node.setData(data);
		if(isEmpty()) {
			first.setNext(node);
		} 
		else {
		 node.setNext(first.getNext());
		 first.setNext(node);
		}
		
	}
	/** Remvoves the first element. If the list is empty throws error.
	 * If only 1 element is present, the list becomes empty. */
	public void removeFromFront(){
		if(isEmpty()) { throw new Error("Linked list empty"); }
		if(first.getNext().getNext() == null) {
			first.setNext(null);
		} else {
		first.setNext(first.getNext().getNext());
		}
		
		
	}
	
	/** Adds to the back. If the Linked list has no elements, the added one becomes first and last. */
	public void addToBack(Object data){
		Node node = new Node();
		node.setData(data);
		if(isEmpty()) {
			first.setNext(node);
		} else {
			Node current=first.getNext();
			while(current.getNext() != null){
				current = current.getNext();
			}
			current.setNext(node);
		 }
	}
	/** Remvoves the last element. If the list is empty throws error.
	 * If only 1 element is present, the list becomes empty. */
	public void removeFromBack() {
		if(isEmpty()) { throw new Error("Linked list empty"); }
		Node temp = first;
		while(temp.getNext().getNext() != null) {
			temp=temp.getNext();
		} 
		temp.setNext(null);
	}
	
	/** Returns textual representation of the linked list. */
	public String toString() {
		if(isEmpty()) { throw new Error("Linked list empty"); }
		StringBuffer sb= new StringBuffer();
		for(Node current=first.getNext();current != null;current = current.getNext()) {
			sb.append(current.toString());
			sb.append("\n");
		 }
		return sb.toString();
	}
	
	/** Returns the number of elements in the Linked list*/
	public int getSize() {
		int size = 0 ;
		for(Node current=first.getNext();current != null;current = current.getNext()) 
		{
			size ++;
		}
		 
		return size;
	}
	/** @param element the object searched for 
	 *  @return the node that contains the object */
	public Node find(Object element) {
		if(isEmpty()) { throw new Error("Linked list empty"); }
		Node current = first.getNext();
		while(current != null
				&& (!current.getData().equals(element))) 
		{
			current=current.getNext();
			
		}
		return current;
	}
	/** Tests this linked with with otherList for deep equality */
	public boolean equals(LinkedList otherList){
		Node current1=this.getFront();
		Node current2=otherList.getFront();
		while((current1 != null) && (current2 != null)) {
			if(!(current1.getData().equals(current2.getData()))) {
				return false;
			}
			current1=current1.getNext();
			current2=current2.getNext();
		}
		
		
		return true;
	}
	 
	 
	
	
}
