package cs22510_2014;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
 
 

public class Model {
	
	 
	 private final static String GGA = "GGA";
	 private final static String GSA = "GSA";
	 private final static String GSV = "GSV";
	 private final static String RMC = "RMC";
	 private boolean satelitesAreOK;
	 
	 
	    
	 private BufferedReader br;
	 private String currentLine;
	 private List<String> currentData;
	 public Model() {
		
		  satelitesAreOK = false;
		  currentData    = new ArrayList<String>();
		   
		try {
			
			 br = new BufferedReader(new FileReader("/home/evdjoint/gps_1.dat"));
			 
			 printSomething();
		} catch (IOException e) {
			 e.printStackTrace();
		}
		  
	 }
	 

	 public void printSomething()  {
		 ArrayList<String> extensionsArray = new ArrayList<String>();
		    Set<String> theset = new HashSet<String>(extensionsArray);
		    System.out.println(theset.size());
		  
	 }
	
	   
	
	  
		    

}
