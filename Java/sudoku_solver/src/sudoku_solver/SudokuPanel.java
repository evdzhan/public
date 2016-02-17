package sudoku_solver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SudokuPanel extends JPanel {
	/**
	 * Object of type Algs. Here we solve the sudoku.
	 */
	private Algs alg;
	/**
	 * Boolean to keep track whether the file is loaded or not.
	 */
	private boolean fileLoaded = false;
	/**
	 * Boolean to keep track whether the sudoku is solved or not.
	 */
	private boolean solved = false;
	/**
	 * Object to handle the loading part, and getting the data from a text file.
	 */
	private Loader loader;

	/**
	 * Constructs the panel where we will draw the solution.
	 */
	public SudokuPanel() {
		setBackground(Color.black);
		this.setPreferredSize(new Dimension(390, 390));
		setVisible(true);

	}

	/**
	 * Checks if the sudoku is solved .
	 * 
	 * @return is the sudoku solved
	 */
	public boolean isSolved() {
		return alg.isSolved();
	}

	/**
	 * Painting stuff.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	/**
	 * Prompt the user for file to choose. Makes use of setTheFile() method. If
	 * a file is successfully loaded it is displayed on the screen.
	 */
	public void load() {
		this.setTheFile();
		if (fileLoaded) {
			alg = new Algs(loader.giveSudoku());
			this.repaint();
		}
	}

	/**
	 * Painting stuff.
	 * 
	 * @param g
	 */
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.drawLine(160, 65, 160, 205);
		g.drawLine(205, 65, 205, 205);
		g.drawLine(112, 65, 112, 205);
		g.drawLine(250, 65, 250, 205);
		g.drawLine(112, 112, 250, 112);
		g.drawLine(112, 158, 250, 158);
		g.drawLine(112, 205, 250, 205);
		g.drawLine(112, 65, 250, 65);

		if (fileLoaded) {
			g.setColor(Color.white);
			int a = 120;
			int b = 80;
			for (int i = 0; i < 9; i++) {
				for (int o = 0; o < 9; o++) {
					StringBuffer sb = new StringBuffer();
					sb.append(alg.unstableSudoku[o][i]);
					g.drawString(sb.toString(), a + i * 15, b + o * 15);
				}
			}
		}
		if (solved) {
			int a = 120;
			int b = 80;
			for (int i = 0; i < 9; i++) {
				for (int o = 0; o < 9; o++) {
					StringBuffer sb = new StringBuffer();
					sb.append(alg.unstableSudoku[o][i]);
					g.drawString(sb.toString(), a + i * 15, b + o * 15);
				}
			}
		}
	}

	/**
	 * Solves the sudoku and paints it on the panel .
	 */
	public void solve() {
		if (!fileLoaded) {
			JOptionPane
					.showMessageDialog(
							new JFrame(),
							"No file loaded, please press the \"Load\" button to choose one.",
							"What ... REALLY ?  !",
							JOptionPane.INFORMATION_MESSAGE);
		} else {
			alg.first();
			while (!alg.isSolved()) {
				alg.candidateCounter();
				alg.nakedPairs();
				alg.hiddenRow();

			}
			this.repaint();
		}

	}

	/**
	 * Takes care of the loading a file.
	 */
	public void setTheFile() {
		JFileChooser theChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				".sud Files", "sud");
		FileNameExtensionFilter filter2 = new FileNameExtensionFilter(
				".txt Files", "txt");
		theChooser.setFileFilter(filter2);
		theChooser.setFileFilter(filter);
		int returnedVal = theChooser.showOpenDialog(this);
		if (returnedVal == theChooser.APPROVE_OPTION) {
			File file = theChooser.getSelectedFile();
			loader = new Loader();
			loader.Load(file);
			if (!loader.fileOK()) {
				JOptionPane.showMessageDialog(new JFrame(),
						"Bad file, please select correct file.",
						"You shall not SOLVE what is not sudoku ... !",
						JOptionPane.WARNING_MESSAGE);
				fileLoaded = false;
			} else {
				fileLoaded = true;
			}
		} else {
			fileLoaded = false;

		}
	}
}