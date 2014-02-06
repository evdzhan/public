package worksheets;

import java.io.*;

public class Worksheet1 {


	public static void main(String[] args) throws IOException {
	
	
		BufferedReader input =  new BufferedReader(new FileReader("file.txt"));
			 
		for(int i=0;i<10;i++) {
			String a=input.readLine();
			System.out.println(a);
		}
		
		input.close();
	}
    
}
