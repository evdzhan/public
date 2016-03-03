package abtract_data_types;


/**
 * Node used by the in DoublyLinkedList .
 *
 * @author Evdzhan Mustafa
 * @version 1.0
 * @see DoublyLinkedList
 */
@SuppressWarnings("unused")
public class DoublyNode {

    private Object data;
    private DoublyNode next;
    private DoublyNode previous;

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String toString() {
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
