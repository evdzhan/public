package abtract_data_types;

/** 
 * @author Evdzhan Mustafa 
 * Node used by the Singly Linked List .
 * @see LinkedList
 * @version 1.0
 */
public class Node {

	private Object data;
	private Node next;  
	
	public Node getNext() {
		return next;
	}
	public void setNext(Node next) {
		this.next = next;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String toString(){
		return String.valueOf(data);
	}
}
