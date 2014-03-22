package uk.ac.aber.dcs.cs12420.blockmation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 * The class that holds  ArrayList of FrameModel objects.
 * The load and save happens here. 
 */
public class FrameHolder {
	/** We keep object of type ArrayList here. It holds 2D array of chars */
private ArrayList<char[][]> theFrames;

	
/**
 * Constructor. We create the declared ArrayList of 2D arrays.
 */
	public FrameHolder() {
		theFrames= new ArrayList<char[][]>();
	}
	/**
	 * Method used by Director classes.After keep button is clicked, the current frame is added.
	 * @param frameAdded this is the current frame added
	 */
	public void add(char[][] frameAdded) {
		this.theFrames.add(frameAdded);
		
	}
	/**
	 * Method used by the Display classes. It's used to give the chosen 2D array determined by the int given.
	 * @param index The index int determines which frame is required.
	 * @return the returned object is 2D array , which is defined by the int index.
	 */
	public char[][] giveChosenArray(int index) {
		return theFrames.get(index);
	}
	/**
	 *  We give the ArrayList inside this object upon this method is invoked.
	 *  A method used for testing.
	 * @return the returned ArrayList
	 */
	public ArrayList<char[][]> giveArray() {
		return theFrames;
		
	}
	/**
	 * This method is used for testing purposes during the development of the assignment.
	 * We print out here the content of the ArrayList here.
	 */
	public void printContent() {
		System.out.println(theFrames.size());
		for(char[][] a:theFrames) {
			for(int i=0; i<a.length ;i++) {
				System.out.println();
				for(int j=0; j<a[0].length; j++) {
					System.out.print(a[i][j]);
				}
			}
		}
		
	}
	/**
	 * Method used to check the dimensions of the 2D arrays inside the ArrayList.
	 * @return returned dimension
	 */
	public int getRowsColumns() {
	
			return theFrames.get(0).length;
		
		}
	
	/**
	 * Load method. Used when Display classes need it. It basically takes input from a file and 
	 * turns it into  ArrayList of 2D arrays of char.
	 * @param file The file to load from .
	 */
	public boolean load(File file) {
		try{
					Scanner infile =new Scanner(new InputStreamReader
					(new FileInputStream(file)));
					int numFrames=infile.nextInt();
					infile.nextLine();
					int dimension=infile.nextInt();
					infile.nextLine();
				
					for(int i=0; i <numFrames ; i++) {
						char[][] tehFrames= new char[dimension][dimension];
						for(int j=0; j < dimension ;j++) {
							char[] frame = infile.nextLine().toCharArray();
							for(int u=0;u<frame.length;u++) {
								tehFrames[j][u]=frame[u];
							}
						}
						theFrames.add(tehFrames);
					}
					infile.close();
					return true;

		}
		catch (Exception  e) { 
			JOptionPane.showMessageDialog(new JFrame(), "This file is bad. Try different one.", "Bad file Error !",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	
	}
	
	/**
	 * Save method. It turns the ArrayList of arrays of chars into a file that we pass into parameter.
	 * @param file The file we wish to save to
	 */
	public void save(File file) {
		  try{
				PrintWriter outfile = new PrintWriter (new
				OutputStreamWriter (new FileOutputStream (file)));
				outfile.println(theFrames.size());
				outfile.println(getRowsColumns());
				for(char[][] a:theFrames) {
	
				for(int i = 0; i < a.length; i++) {
				
					for(int j = 0; j < a[0].length; j++) { 
						outfile.print(a[i][j]);
			}
					
					outfile.println();
				}
			
		 }
				outfile.close();
		 }
		 catch (FileNotFoundException fnf){
				System.out.println("File not found!");
				}
	}
	

}
