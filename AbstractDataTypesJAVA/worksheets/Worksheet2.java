package worksheets;
import java.io.*;
public class Worksheet2 {

	
	public static void main(String[] args) throws IOException {
		
		BufferedReader input =  new BufferedReader(new FileReader("file2.txt"));
		for(int x=0;x<20;x++) {
			String a=input.readLine();
			String strings[]=a.split("(\\s|\\p{Punct})+");
		if(x<strings.length) 
		{
				System.out.println(strings[x]);
	    }
	    else 
	    {
		System.out.println(strings[strings.length-1]);		
	    }
		}
		input.close();
		}
	}
