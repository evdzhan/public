import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class MAIN {

	public static void main(String[] args) throws Exception {
		 
		
		BufferedReader br1 = new BufferedReader(new FileReader("java.txt"));
		BufferedReader br2 = new BufferedReader(new FileReader("c.txt"));
		BufferedReader br3 = new BufferedReader(new FileReader("c++.txt"));
		 int x = 0 ;
		for(int i = 0 ; i < 458 ; ++i) {
	    String [] a = 		br1.readLine().split(",");
	    String [] b = 		br2.readLine().split(",");	
	    String [] c = 		br3.readLine().split(",");
	    double lat1 = Double.parseDouble(a[0]);
	    double lat2 = Double.parseDouble(b[0]);
	    double lat3 = Double.parseDouble(c[0]);
	    
	    if( lat1 == lat2 && lat2 == lat3 ) {
	    	x++;
	    	
	    }
	   
	    double lng1 = Double.parseDouble(a[1]);
	    double lng2 = Double.parseDouble(b[1]);
	    double lng3 = Double.parseDouble(c[1]);
	    
	    
	     
	    if(lng1 == lng2 && lng2 == lng3 ) {
	    	x++;
	    	
	    }
	  
			
		}
		  if(x == 458 * 2) System.out.println("true");
		
		
		
		
		

	}

}
