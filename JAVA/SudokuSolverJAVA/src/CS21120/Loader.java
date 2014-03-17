package CS21120;

import java.io.*;
import java.util.Scanner;

/**
 * @author Evdzhan Nedzhib Mustafa This class loads a file. Provides methods to
 *         check whether the file is good. Also can return a 2 D int array with
 *         the values taken from the file
 */
public class Loader {
	private int sudoku[][];

	public Loader() {
		sudoku = new int[9][9];

	}

	/**
	 * Checks if the file is correct.
	 * 
	 * @return is the file good, or not
	 */
	public boolean fileOK() {
		int count = 0;
		for (int i = 0; i < 9; i++) {
			for (int o = 0; o < 9; o++) {
				if (this.sudoku[i][o] >= 0 && this.sudoku[i][o] < 10) {
					count++;
				}
			}
		}
		if (count == 81) {
			return true;
		}
		return false;
	}

	/**
	 * Gives the 2D array of ints
	 * 
	 * @return the returned two-dimensional array of ints
	 */
	public int[][] giveSudoku() {
		int[][] unstable = new int[9][9];
		for (int i = 0; i < 9; i++) {
			for (int o = 0; o < 9; o++) {
				unstable[i][o] = this.sudoku[i][o];
			}
		}

		return unstable;
	}

	/**
	 * The loading method. Changes every space in the file to 0. Puts all
	 * numbers to a 2D int array.
	 * 
	 * @param file
	 *            the file to be loaded
	 */
	public void Load(File file) {
		try {
			Scanner infile = new Scanner(new InputStreamReader(
					new FileInputStream(file)));

			for (int w = 0; w < 9; w++) {
				String a = infile.nextLine();
				a = a.replaceAll(" ", "0");
				for (int h = 0; h < 9; h++) {

					sudoku[w][h] = Character.getNumericValue(a.charAt(h));

				}
			}
			infile.close();
		} catch (Exception e) {

		}
	}
}
