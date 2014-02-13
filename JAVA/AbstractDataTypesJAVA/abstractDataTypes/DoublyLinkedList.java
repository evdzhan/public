
package abstractDataTypes;


/** 
 * Abstract data type representing Doubly Linked List.
 * Each node apart from the first and the last, has two node pointers inside of it,namely next, and previous.
 * I have chosen to use Sentinel node as a pointer to the beginning of the linked list.
 * @author Evdzhan Mustafa 
 * @see DoublyNode
 * @version 1.0
 */
public class DoublyLinkedList {
	
	
	private DoublyNode first = new DoublyNode();
	
	public boolean isEmpty(){
		return first.getNext() == null;
	}
	
	public void addToFront(Object data){
		DoublyNode  node = new DoublyNode();
		node.setData(data);
	if(this.isEmpty()) {
		first.setNext(node);
	 }
	else if(first.getNext().getNext() == null){
		node.setNext(first.getNext());
		first.setNext(node);
		node.getNext().setPrevious(node);
	 } 
	else {
		first.getNext().setPrevious(node);
		node.setNext(first.getNext());
		first.setNext(node);
	 }
	 }
	
	public void removeFromFront(){
	if(isEmpty()) { throw new Error("Linked list empty"); }
	 
	  if(first.getNext().getNext() == null) {
		  first.setNext(null);
	 } else {
		 first.getNext().getNext().setPrevious(null);
		 first.setNext( first.getNext().getNext());
	 }
	 
	}
	
	public void addToBack(Object data){
		DoublyNode node = new DoublyNode();
		node.setData(data);
		if(this.isEmpty()) {
			first.setNext(node);
		 }
		else if(first.getNext().getNext() == null){
			first.getNext().setNext(node);
			node.setPrevious(first.getNext().getNext());
		 }
		else {
			DoublyNode last=first.getNext();
			while(last.getNext() != null) {
				last=last.getNext();
			}
			last.setNext(node);
			node.setPrevious(last);
			
		}
	 
	}

	public void removeFromBack() {
		if(isEmpty()) { throw new Error("Linked list empty"); }
		
	if(first.getNext().getNext() == null) {
			  first.setNext(null);
		 }
	else {
		DoublyNode last=first.getNext();
		while(last.getNext().getNext() != null) {
			last=last.getNext();
		}
		last.setNext(null);
		
		
	}
		
	}
	public String toString() {
		if(isEmpty()) { throw new Error("Linked list empty"); }
		StringBuffer sb= new StringBuffer();
		for(DoublyNode current=first.getNext();current != null;current = current.getNext()) {
			sb.append(current.toString());
			sb.append("\n");
		 }
		return sb.toString();
	}
	

}
