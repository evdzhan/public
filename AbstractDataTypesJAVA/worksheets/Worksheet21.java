package mustafa.evdzhan.worksheets;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Worksheet21 {

	
	public static void main(String[] args) throws IOException {
		BufferedReader input =  new BufferedReader(new FileReader("file2.txt"));
	 
		
	ArrayList<String> sentences = new ArrayList<String>();
	
	String line = "";
	while(sentences.size() != 10){
	line +=" "+ input.readLine();
	String sentence[]= line.split("[.]");	
	if(sentence.length == 1) {
		line=sentence[0];
	} else {
		sentences.add(sentence[0]);
		line="";
		for(int i=1;i<sentence.length;i++) {
		line+=sentence[i];
		}
	}
	}
	for(int i=0;i<sentences.size();i++) {
	System.out.println(i + sentences.get(i));
	}
	
	
		
		
		
		

		input.close();
	}
}


