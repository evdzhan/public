package uk.ac.aber.dcs.cs12420.blockmation;
import java.awt.*;

import javax.swing.*;


import java.awt.event.*;
/**
 * The window of the Display. Here we have only 3 buttons, and a white space below them where we run the animation.
 * @author Evdzhan Nedzhib Mustafa
 *
 */
public class DisplayFrame extends JFrame implements ActionListener {
  /** Declaring the JButtons */
	private JButton load,run,stop;
	/** The panel with where we add buttons */
	private JPanel buttons;
	/** A DisplayPanel object to represent our virtual monitor , here we draw */
    private DisplayPanel theAnimation;
    
   

	/**
	 * Constructor. The usual stuff for a JFrame , we set title ,  add buttons and set their action listener to this object.
	 */
	public DisplayFrame() {
		  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setTitle("Frame of the Viewer");
	        buttons=new JPanel();
	        load=new JButton("Load");
	        load.addActionListener(this);
	        run=new JButton("Run");
	        run.addActionListener(this);
	        stop=new JButton("Stop");
	        stop.addActionListener(this);
	        buttons.setLayout(new GridLayout(1,4));
	        buttons.add(load);
	        buttons.add(run);
	        buttons.add(stop);
	        
	        setLayout(new BorderLayout());
	        add(buttons, BorderLayout.NORTH);
	        setSize(500,400);  
	        this.setLocation(700,220);
	        theAnimation=new DisplayPanel();
	        add(theAnimation,BorderLayout.CENTER);
	        setVisible(true);
	        
	}

	/**
	 * The action listener mehtod of this object.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == load) {
			theAnimation.load();
		}
		else if(e.getSource() == run) {
		Thread asd= new Thread(theAnimation);
		asd.start();
		}
		else if(e.getSource() == stop) {
			theAnimation.stop();
			
		}
		
	}

}
