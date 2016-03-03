package sudoku_solver;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * @author Evdzhan Nedzhib Mustafa GUI class for the sudoku solver.
 */
public class SudokuFrame extends JFrame implements ActionListener {
	/**
	 * Buttons for load, solve and exit actions.
	 */
	private JButton load, solve, exit;
	/**
	 * Jpanel to hold the buttons.
	 */
	private JPanel buttons;
	/**
	 * The panel where we draw the solved sudoku.
	 */
	private SudokuPanel display;

	/**
	 * Constructs the window , buttons etc.
	 */
	public SudokuFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Sudoku - CS21120 Assignment(2013-2014) ");
		buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 2));
		load = new JButton("Load");
		load.addActionListener(this);
		solve = new JButton("Solve");
		solve.addActionListener(this);
		exit = new JButton("Exit");
		exit.addActionListener(this);
		buttons.add(load);
		buttons.add(solve);
		buttons.add(exit);
		display = new SudokuPanel();

		setLayout(new BorderLayout());
		add(buttons, BorderLayout.NORTH);

		add(display, BorderLayout.CENTER);
		setSize(360, 290);
		this.setLocation(500, 220);
		setVisible(true);

	}

	/**
	 * Action handler method.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == load) {
			display.load();
		} else if (e.getSource() == solve) {
			display.solve();
		} else if (e.getSource() == exit) {
			System.exit(0);
		}
	}
}
