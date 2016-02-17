package blockmation;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * The panel responsible for viewing the animation upon run clicking is detected.
 * @author Evdzhan Nedzhib Mustafa
 *
 */
public class DisplayPanel extends JPanel implements Runnable{
/** The Row Dimension of our frames */
	private int rows;
	
	/** The Column Dimension of our Frames */
	private int columns;
	
	/** A frame holder object . We use it to load our frames , and we get them from here when we repaint. */
	private FrameHolder theFrames;
	
	/** The current frame being displayed. We replace this after each repaint. */
	private FrameModel gridmodel;
	
	/** To know if the thread needs to be going or to stop*/
	private boolean keepGoing;
	
	/** A boolean to watch if we have file loaded or not */ 
	private boolean fileLoaded=false;
	
	 /** The file to be processed to animation */
	private File theFile;
	
	/** The status showing the current file being played */
	private String theStatus="";
	
	/**
	 *  Normal constructor , we only set it's color to white . Nothing else to declare or create.
	 */
	public DisplayPanel() {
		setBackground(Color.white);
		
		
	}
	
	
	/**
	 *  The thing we get after each repaint. We actually call our other method draw () .
	 */
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
       draw(g);      
        
    }
	
	
	
	/**
	 *  The actual painting. We go through the currend 2D array and we paint.
	 *  
	 */
	public void draw(Graphics g) {
		
		g.setColor(Color.BLACK);
	       g.drawString(theStatus,175, 20);   
        int i,j,starti=100,startj=100;
       
     
       for (i=0; i<rows; i++)
           for (j=0; j<columns; j++)
               if (gridmodel.checkChar(i,j) == ('b'))
               {
               	g.setColor(Color.BLACK);
                   g.fillRect(startj+j*9,starti+i*9,8,8);
               }
       
               else if(gridmodel.checkChar(i, j)== ('g')) {
               	g.setColor(Color.GREEN);
                   g.fillRect(startj+j*9,starti+i*9,8,8);
               }
       
              else if(gridmodel.checkChar(i,j) == ('r')) {
                   	g.setColor(Color.RED);
                       g.fillRect(startj+j*9,starti+i*9,8,8);
              }
              else if(gridmodel.checkChar(i, j) == ('l')) {
            	  g.setColor(Color.GRAY);
            	  g.fillRect(startj+j*9,starti+i*9,8,8);
              }
	
}
	/**
	 *  Here we set the keepGoing Boolean to false. We do so, to stop  the animation.
	 */
	public void stop() {
		keepGoing=false;
		
		theStatus="Stopped.";
		
		this.repaint();
	}
	/**
	 * The run method. We check if we have a file first. We  then set the keepGoing to true , and then while it's true we take every 2D array 
	 * from the ArrayList and set it as our FrameModel , we then repaint and wait 0,5 seconds.
	 *  In case we finish all 2D arrays , we set the keepGoing to false
	 * and the animations stops.
	 */
	public void run() {
		
		if((theFile == null) || (gridmodel == null)) {
			JOptionPane.showMessageDialog(new JFrame(), "Nothing to run yet !", "Error !",
					JOptionPane.WARNING_MESSAGE);
			
			return;
		}
		
		try {
			int i=0;
			keepGoing=true;
			 theStatus="Playing: " +theFile.getName();
			   this.repaint();
          while(keepGoing) {
				if(i < theFrames.giveArray().size()) {
			 gridmodel.setGrid(theFrames.giveChosenArray(i));
			 Thread.sleep(500);
			 this.repaint();
			
			 i= i+1;
				}
				else {		
					i=0;
					keepGoing=false;
					theStatus="Finshed playing.";
		            this.repaint();
							
				}		
}
		}
		catch(InterruptedException e) {
		}
		}	
	/**
	 * This method creates new FrameHolder object, and calls it's method load.
	 * The load takes the file specified by the user, and creates ArrayList, with elements char[][] .
	 * It has very complicated error checking. 
	 */
	public void load() {
		keepGoing=false;
	
		this.setTheFile();
		
		if(fileLoaded) {
		theFrames = new FrameHolder();
		
		if(theFrames.load(theFile))  {
			
		
		rows=theFrames.getRowsColumns();
		columns=theFrames.getRowsColumns();
		gridmodel = new FrameModel(rows,columns);
		theStatus="Loaded: " +theFile.getName();
		this.repaint();
		}	
		else {
			theFile=null;
			gridmodel.clear();
			this.repaint();
		}
		}
		
		
		
		
	}
	/** This method sets the File field to a file that user selects through JFileChooser */
	public void setTheFile() {
		JFileChooser   theChooser= new JFileChooser();
		FileNameExtensionFilter filter =new FileNameExtensionFilter(".txt Files","txt");
		theChooser.setFileFilter(filter);
		int returnedVal= theChooser.showOpenDialog(this);
		if(returnedVal == theChooser.APPROVE_OPTION) {
			this.theFile=theChooser.getSelectedFile();
		   fileLoaded=true;
		   
		 
		}
		else {
			fileLoaded=false;
		}
			}
}