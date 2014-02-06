package uk.ac.aber.dcs.cs12420.blockmation;
 
import java.awt.*; 

import javax.swing.*;


import java.awt.event.*;
import java.io.File;
/**
 * This class is responsible for the white field below the buttons in the DirectorFrame window.
 * Here we build blocks of squares and change their colors.
 * 
 */
public class DirectorPanel extends JPanel implements MouseListener {
	/** The frame model we are using  to represent the current frame */
	private FrameModel frameModel;
	
	/** The Holder to keep our frames */
	private FrameHolder frameHolder;
	
	/** The rows our frame will have */
	private int rows;
	
	/** The columns our frame will have */
	private int columns;
	/** The status in the panel */
	private String theStatus=" left - green | right - red | scroller - reset  ";
	
	
	/**
	 *  Constructor .  We  create a frameModel object and give it  integers and set them as the dimensions.
	 *  We also create a FrameHolder object to store our frames.
	 *  @param theRows the Number of rows in our block of squares.
	 *  @param theColumns the number of columns in our block of squares.
	 *  
	 */
	public DirectorPanel(int theRows,int theColumns) {
		rows=theRows;
		columns=theColumns;
		setBackground(Color.white);
	 	frameModel = new FrameModel(rows,columns);
	 	frameHolder = new FrameHolder();
	 	this.addMouseListener(this);
	 	this.repaint();		
	}	
	/**
	 *  The method that paints , it calls draw() which is where the actual painting happens.
	 */
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
       draw(g);   
        
    }	
	/**
	 * The Actual painting .
	 * @param g
	 */
	
	public void draw(Graphics g) {
		 g.setColor(Color.BLACK);
	       g.drawString(theStatus,50, 20); 
         int i,j,starti=100,startj=100;
        
        for (i=0; i<rows; i++)
            for (j=0; j<columns; j++)
                if (frameModel.checkChar(i,j) == ('b'))
                {
                	g.setColor(Color.BLACK);
                    g.fillRect(startj+j*9,starti+i*9,8,8);
                }
        
                else if(frameModel.checkChar(i, j)== ('g')) {
                	g.setColor(Color.GREEN);
                    g.fillRect(startj+j*9,starti+i*9,8,8);
                }
        
               else if(frameModel.checkChar(i,j) == ('r')) {
                    	g.setColor(Color.RED);
                        g.fillRect(startj+j*9,starti+i*9,8,8);
               }
    
       
    }  
	/**
	 *  The mouse listener method. 
	 *  This method detects where our mouse is . In the event it's over a square and a click happens , 
	 *  we change the square color (left , right or middle mouse button ).
	 *  We also repaint after each click .
	 */
	 public void mouseClicked(MouseEvent e) {
	        int i,j,starti=100,startj=100;
	        int x=e.getX(); int y=e.getY();
	        for (i=0; i<rows; i++)
	            for (j=0; j<columns; j++)
	                if ( y > starti+i*9 && y < starti+i*9+8 &&
	                x > startj+j*9 && x < startj+j*9+8) {
	                	
	                    
	                   if(SwingUtilities.isLeftMouseButton(e))
	                       {
	                	   frameModel.setChar(i, j,'g' );
	                	   this.repaint();
	                	   }
	                   else if(SwingUtilities.isRightMouseButton(e)) {
	                	   frameModel.setChar(i,j, 'r');
	                	   this.repaint();
	                   }
	                   else if(SwingUtilities.isMiddleMouseButton(e)) {
	                	   frameModel.setChar(i,j, 'b');
	                	   this.repaint();
	                   }
	                   
	                    
	                   
	                   
	                    }
	                else {
	                
	                }
	       
	        
	    } 
	 
	 /**
	  *  This method adds  the current frame we work on currently and adds it in the array list in the frame holder .
	  *  The keep() method in the FrameModel creates replica of the 2D char array, and returns  it.
	  */
	 public void keep() {
		 
		this.frameHolder.add(frameModel.copyAndGive());
		
	 }
	 
	 /**
	  * This method invokes the save method in  the FrameHolder.
	  * It saves the ArrayList to a text file.
	  * @param file The file we save on.
	  */
	 public void save(File file) {
	
		 frameHolder.save(file);
		 
	 }
	 

	
	
public void mouseEntered(MouseEvent arg0) {
		
		
	}


	public void mouseExited(MouseEvent arg0) {
		
		
	}

	public void mousePressed(MouseEvent arg0) {
		
		
	}

	public void mouseReleased(MouseEvent arg0) {
		
	}


	
	

}
