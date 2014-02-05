package mustafa.evdzhan.AbstractDataTypes;

public class DoublyNode {

	private Object data;
	private DoublyNode next;
	private DoublyNode previous;
	
	public Object getData(){
		return this.data;
	}
	public void setData(Object data){
		this.data=data;
	}
	public String toString(){
		return String.valueOf(data);
	}
	public DoublyNode getNext() {
		return next;
	}
	public void setNext(DoublyNode next) {
		this.next = next;
	}
	public DoublyNode getPrevious() {
		return previous;
	}
	public void setPrevious(DoublyNode previous) {
		this.previous = previous;
	}
}
