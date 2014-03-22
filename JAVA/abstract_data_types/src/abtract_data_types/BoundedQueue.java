package abtract_data_types;


/** 
 *  Abstract data type representing Queue. 
 * The queue is bounded i.e. it will run out of space eventually.
 * @author Evdzhan Mustafa 
 * @version 1.0
 */

public class BoundedQueue {

	private final static int DEFAULT_MAX_SIZE=10;
	private Object[] theQueue;
	private int first;
	private int firstFree;
	private int length;
	private int maxSize;
	public BoundedQueue(){
		this(DEFAULT_MAX_SIZE);
	 }
	
	public BoundedQueue(int size){
		this.maxSize=size;
		this.theQueue= new Object[size];
		first=0;
		firstFree=0;
		length=0;
	}
	public void add(Object object) {
		if(length < maxSize) { 
			theQueue[firstFree++] = object;
			length++;
			
			if(firstFree == maxSize) {
				firstFree = 0; 
			}
			 
			return;
		 }
		
		
		   throw new Error("queue full");
		
	}
	public Object remove() {
		if(length != 0 ) {
		int toReturn=first;
		first++;
		length--;
		if(first == maxSize) {
			first = 0;
		}
		return theQueue[toReturn];
		} 
		
		 throw new Error("queue empty");
		 
	}
	public Object peek() {
		if(length != 0 ) {
		  return theQueue[first];
			} 
			
			 throw new Error("queue empty");
	}
	public int getLength() {
		return this.length;
	}
	public int getFirstFree() {
		return firstFree;
	}
	
	
}
