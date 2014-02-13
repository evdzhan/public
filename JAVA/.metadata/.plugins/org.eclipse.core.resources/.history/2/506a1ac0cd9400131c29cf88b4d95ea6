import java.util.Scanner;

import abstractDataTypes.BoundedQueue;
import abstractDataTypes.BoundedStack;

 




 

public class Palindrome {

	/**
	 * 09/02/2014
	 * This class shows the usage of Stack and Queue 
	 * in order to determine whether a string is a palindrome
	 * @author Evdzhan Mustafa 
	 * @param args	
	 */
	public static void main(String[] args) {
		
		
		
		String stringToTest ;
		
		Scanner scanny = new Scanner(System.in);
		
		System.out.println("Enter the string to check : ");
		
		stringToTest=scanny.next();
		
		BoundedStack bs = new BoundedStack(stringToTest.length());
		
		BoundedQueue bq = new BoundedQueue(stringToTest.length());
		
		for(int i=0;i<stringToTest.length();i++) {
			
		bs.push(stringToTest.charAt(i));
		
		bq.add(stringToTest.charAt(i));
		
		}
		StringBuffer sb = new StringBuffer();
		
		int size = bs.sizeOf();
		
		for(int i = 0 ; i < size ; i ++ ) {
			   
			sb.append((char)bs.pop());
		}
		
	    String first = sb.toString();
	    
	    sb.setLength(0);
	    
	    size = bq.getLength();
	    
	    for(int i = 0 ; i < size ; i ++ ) {
			   
			sb.append((char)bq.remove());
		}
		 String second=sb.toString();
		 
		 if(first.compareToIgnoreCase(second) == 0) {
			 
			 System.out.println("The string is palindrome !");
			 
		 } else {
			 
			 System.out.println("The String is NOT palindrome.");
		 }
	    
   
 scanny.close();
	 
		

	}

}
