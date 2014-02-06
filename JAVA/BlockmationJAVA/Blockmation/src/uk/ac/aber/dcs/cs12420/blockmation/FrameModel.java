package uk.ac.aber.dcs.cs12420.blockmation;
import java.io.File;
/**
 * Class holding the data representation of single frame.
 * It holds a two dimensional Array of Chars.
 * 
 * @author Evdzhan Nedzhib Mustafa
 *
 */
public class FrameModel {

	/** The  data representation of our project */ 
	private char grid[][];
	
	
	/**
	 *  The constructor .We create the grid according to the desired dimensions .
	 *  We also create the object holding our ArrayList of  2D char arrays.
	 * @param rows  We pass the rows integer to set the rows.
	 * @param columns  We pass the columns integer to set the columns.
	 */
	public FrameModel(int rows,int columns) {
		if((rows < 1) || (columns < 1)) throw new IllegalArgumentException ("Rows and/or columns must be possitive ");	
		grid = new char[rows][columns];	
		this.clear();
			
	}	
	/**
	 * The method sets the char[][] object in this object, to new one that is given by the parameter.
	 * 
	 * @param theNewGrid This is the new array we pass into this method and set it to be the current one we work on.
	 */
	public void setGrid(char[][] theNewGrid) {
		this.grid=theNewGrid;
	}	
	/**
	 *  Invoking this method , we create replica of the current Array of chars and return it.
	 */
	public char[][] copyAndGive() 
	{
			char[][] frame=new char[grid.length][grid.length];
		for(int i=0;i<grid.length;i++)  {
			for(int j=0;j<grid[0].length;j++) {
			frame[i][j]=grid[i][j];
			}
		}
		return frame;	
	}	
	/**
	 * A method used by the paint method in the panel class. It gives a char determined by the integers passed in.
	 * @param row the desired row location
	 * @param column the desired column location
	 * @return we return here the char we desire , according to the location of rows and columns integers.
	 */
	public char checkChar(int row,int column) 
	{		 
	            return grid[row][column];
	}
	/**
	 * Test method . Not used in the logic of the project.
	 * It does copy job from one 2D array of char to another.
	 * @param x The 2D array where we intend to copy to.
	 * @param y The 2D array from where we intent to copy from.
	 */
	public void copy(char[][] x,char[][] y) {
		
		for(int row=0;row<x.length ; row++) {
			for(int column=0;column<x[0].length;column++) {
				x[row][column]=y[row][column];
			}
		}
	}
	/**
	 * Method used upon click on the squares (Director panel). 
	 * It changes the char in particular location in the 2D array.
	 * @param row the row location 
	 * @param column the column location 
	 * @param b the char to be set
	 */
	public void setChar(int row, int column, char theChar) {       
        grid[row][column] = theChar;
    }	
	/**
	 * Method that resets every char to "b" . Used in the constructor.
	 */
	 public void clear() {
         int row, column;
        
         for (row=0; row < grid.length; row++)
             for (column=0; column < grid[0].length; column++)
                 grid[row][column] = 'b';
      }
	 
	
	
}
	 
