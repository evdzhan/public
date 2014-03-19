package uk.ac.aber.dcs.cs12420.blockmation;
 
import java.awt.*; 
import javax.swing.*; 
import javax.swing.filechooser.FileNameExtensionFilter;


import java.awt.event.*;
import java.io.File;

/**
* This class is responsible for the Director window. This is where we draw frames keep them , and save them to a file.
*/
public class DirectorFrame extends JFrame 
implements ActionListener{ 
	/** The place where we put our buttons */
    private JPanel buttonPanel; 
    
    /** The buttons that user uses  */
    private JButton size, keep, save,file; 
    
    /**  The text field to determine the dimensions of the frames*/
    private JTextField theSize;
    
    /** The place where we draw and change our frame  */
    private DirectorPanel myDirector;
    
    /** The file Chooser */
   private JFileChooser theChooser;
   
   /** The file for input */
   private File theFile;
   
   /** A boolean to keep track if a file is chosen yet or not */
    private boolean fileChosen;
    
    /** A boolean to keep track if we have never kept a frame  */
    private boolean neverBeenKept=true;

    /**
     * The constructor. Builds buttons ,  the text field for data entry  and the frame itself. 
     */
    public DirectorFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Window of the Director");
        buttonPanel=new JPanel();
        size = new JButton("Size");
        theSize = new JTextField("");
        keep = new JButton("Keep");
        save = new JButton("Save");
        file = new JButton("Set the file");
       
        
        buttonPanel.setLayout(new GridLayout(1,5));
        buttonPanel.add(theSize); 
        
        buttonPanel.add(size); 
        size.addActionListener(this);
        
        buttonPanel.add(keep);
        keep.addActionListener(this);
        
        buttonPanel.add(save); 
        save.addActionListener(this);
        
        buttonPanel.add(file);
        file.addActionListener(this);
       
      
        setLayout(new BorderLayout());
        this.setLocation(100,220);
        add(buttonPanel, BorderLayout.NORTH);
                  setSize(500,400);     
                  
                          setVisible(true);
    }

    /**
     * The method that is responsible for buttons .  Each button calls the wished action to be performed.
     * The object is it's own listener.
     */
    public void actionPerformed(ActionEvent e){
    	
    	if (e.getSource()  ==  size) {
    	this.createNewGrid();
    	
    	  }
    	else if(e.getSource() == keep) {
    		this.keep();
    	}
    	else if(e.getSource() == save) {
    		this.save();
    	}
    	else if(e.getSource() == file) {
    		this.setTheFile();
    	}
    	
    	}
    	/**
    	 *  Keep method.
    	 *  Can't be processed if no frame is created yet.
    	 */
    public void keep() {
    	
    	boolean isOk=true;
		if(myDirector == null) {
			isOk=false;
			JOptionPane.showMessageDialog(new JFrame(), "Nothing to be kept yet..", "Warning!",
					JOptionPane.INFORMATION_MESSAGE);
		}
		if(isOk) {
		myDirector.keep();
		neverBeenKept=false;
		}
    }
    
    /**
     *  Save method.
     *  Has lots of error catching checks.
     *  1. Can't press Save button if we don't have created frame yet.
     *  2. Can't press Save button if we created frame , but have not kept it yet.
     *  3. Can't save to a file, if  save button is clicked , but then Cancel is clicked.
     */
    public void save() {
    	boolean ok=true;
		if(myDirector== null) {
			ok=false;
				JOptionPane.showMessageDialog(new JFrame(), "Nothing to be saved yet...Enter size in the field and then press size button !", "Warning!",
						JOptionPane.INFORMATION_MESSAGE);	
				return;
		}
		if(neverBeenKept) {
			ok=false;
			JOptionPane.showMessageDialog(new JFrame(), "Can't save ! Press keep , for at least one frame...", "Warning!",
					JOptionPane.INFORMATION_MESSAGE);	
			return;
			
		}
		
		if(ok==true) {
			if(this.theFile == null) {
				 this.setTheFile();             
			}
			if(fileChosen) {
				myDirector.save(theFile);
				}
		     else {
					JOptionPane.showMessageDialog(new JFrame(), "You didin't pick a file. File not saved!", "No file chosen.",
							JOptionPane.CANCEL_OPTION);	
				}
			}
			
		
		}
    	 /**
    	  *  Method that sets the file the user currently saves his frames on.
    	  */
    
    	public void setTheFile() {
    		
    		 theChooser = new JFileChooser();
    	
    	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
    	        "Text files only", "txt");
    	    theChooser.setFileFilter(filter);
    	    int returnVal = theChooser.showSaveDialog(this);
    	    if(returnVal == JFileChooser.APPROVE_OPTION) {
    	    	theFile = theChooser.getSelectedFile();
    	    	fileChosen=true;
    	    }
    	    else {
    	    	fileChosen=false;
    	    }
    	  
    	  
    }
    
    
    
   /**
    * The method called when the Button Size is clicked.
    * If the value in the JTextField( theSize ) is integer between  1 and 30 , 
    * we create a new Director Panel where all the fun starts.
    * 
    */

	public void createNewGrid() {
    	int val=0;
    	boolean ok=false;
    	if(myDirector != null) {
    		this.remove(myDirector);
    		neverBeenKept=true;
    		
    		
    	}
  	  String userdata=theSize.getText(); 
  	  
	  try{
		  val = Integer.parseInt(userdata);
		 ok=true;
	  }
      catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(new JFrame(), "Must be a number w/o spaces ! (Values between 1-30 only)", "Error !",
						JOptionPane.ERROR_MESSAGE);
		return;	
      }
	  
	  if(val > 30) {
		  ok=false;
		  JOptionPane.showMessageDialog(new JFrame(), "Too large !", "Error !",
					JOptionPane.ERROR_MESSAGE);
	  }
	  else if(val < 1 ){
		  ok=false;
		  JOptionPane.showMessageDialog(new JFrame(), "Too small  !", "Error !",
					JOptionPane.ERROR_MESSAGE);
		  
	  }
	 if(ok) {
	  myDirector = new DirectorPanel(val,val);
		
      add(myDirector, BorderLayout.CENTER);
      setVisible(true);  
	 }
  }
	    }