package sudoku_solver;

import java.util.HashMap;
import java.util.Map;

/**
 * This class takes an 2-dimensional int array. The array is read as an sudoku
 * and is solved by various algorithms.
 * 
 * @author Evdzhan Nedzhib Mustafa
 * 
 */
public class Algs {
	/**
	 * The map holds a string representation of each unknown value from the int
	 * array. The key(string) is the address of that unknown value, e.g. the
	 * element in the first row , first column is "00".
	 */
	private Map<String, String> the_sudoku;
	/**
	 * The 2D int array. Every known value is a digit (1 to 9) . Unknown values
	 * are represented by 0.
	 */
	public int[][] unstableSudoku;

	/**
	 * Tells whether the sudoku is solved or not.
	 * 
	 * @return is the sudoku solved
	 */
	public boolean isSolved() {
		int count = 0;
		for (int i = 0; i < 9; i++) {
			for (int o = 0; o < 9; o++) {
				if (this.unstableSudoku[i][o] > 0
						&& this.unstableSudoku[i][o] < 10) {
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
	 * Method invoked after the sudoku is solved. The solved sudoku is returned
	 * as 2D int array.
	 */
	public int[][] giveSudoku() {
		int[][] newone = new int[9][9];
		for (int i = 0; i < 9; i++) {
			for (int o = 0; o < 9; o++) {
				newone[i][o] = unstableSudoku[i][o];
			}
		}

		return newone;
	}

	/**
	 * The default constructor.
	 * 
	 * @param sudoku
	 *            the sudoku in unsolved form.
	 */
	public Algs(int[][] sudoku) {
		the_sudoku = new HashMap<String, String>();

		this.unstableSudoku = sudoku;
		for (int i = 0; i < 9; i++) {
			for (int o = 0; o < 9; o++) {
				if (unstableSudoku[i][o] == 0) {
					the_sudoku.put(Integer.toString(i) + Integer.toString(o),
							"123456789");
				}
			}
		}
	}

	/**
	 * Method invoked when eliminating a candidate from a row.
	 * 
	 * @param theRow
	 *            From which row is the candidate eliminated.
	 * @param eliminated
	 *            The eliminated value from the given row.
	 */
	public void eliminateR(int theRow, String eliminated) {
		for (int i = 0; i < 9; i++) {
			StringBuilder sb = new StringBuilder();
			sb.append(theRow);
			sb.append(i);
			String key = sb.toString();
			if (the_sudoku.containsKey(key)) {
				if (the_sudoku.get(key).contains(eliminated)) {
					String x = the_sudoku.get(key).replace(eliminated, "");
					the_sudoku.put(key, x);
					this.lockSingleCandidate();
				}
			}
		}
	}

	/**
	 * Method invoked when eliminating a candidate from a column.
	 * 
	 * @param theRow
	 *            From which column is the candidate eliminated.
	 * @param eliminated
	 *            The eliminated value from the given column.
	 */
	public void eliminateC(int theColumn, String eliminated) {
		for (int i = 0; i < 9; i++) {
			StringBuilder sb = new StringBuilder();
			sb.append(i);
			sb.append(theColumn);
			String key = sb.toString();
			if (the_sudoku.containsKey(key)) {
				if (the_sudoku.get(key).contains(eliminated)) {
					String z = the_sudoku.get(key).replace(eliminated, "");
					the_sudoku.put(key, z);
					this.lockSingleCandidate();
				}
			}
		}
	}

	/**
	 * Method invoked when eliminating a candidate from a local 3x3 square.
	 * 
	 * @param theRow
	 *            row to determine which 3x3 square the eliminated shold be
	 *            removed from
	 * @param theColumn
	 *            column to determine which 3x3 square the eliminated shold be
	 *            removed from
	 * @param eliminated
	 *            The eliminated value from the given local 3x3 square.
	 */
	public void eliminateS(int theRow, int theColumn, String eliminated) {
		int x = (theRow / 3) * 3;
		int y = (theColumn / 3) * 3;
		for (int i = x; i < x + 3; i++) {
			for (int o = y; o < y + 3; o++) {
				StringBuilder sb = new StringBuilder();
				sb.append(i);
				sb.append(o);
				String key = sb.toString();
				if (the_sudoku.containsKey(key)) {
					if (the_sudoku.get(key).contains(eliminated)) {
						String z = the_sudoku.get(key).replace(eliminated, "");
						the_sudoku.put(key, z);
						this.lockSingleCandidate();
					}
				}
			}
		}
	}

	/**
	 * Checks for naked singles. If it finds any, it puts the value in the int
	 * array and removes it from the from the other candidates list in the
	 * corresponding row,column or local 3x3 square.
	 */
	public void lockSingleCandidate() {
		for (int i = 0; i < 9; i++) {
			for (int o = 0; o < 9; o++) {
				StringBuilder sb = new StringBuilder();
				sb.append(i);
				sb.append(o);
				String key = sb.toString();
				if (the_sudoku.containsKey(key)) {
					if (the_sudoku.get(key).length() == 1) {
						String a = the_sudoku.get(key);
						unstableSudoku[i][o] = Integer.parseInt(the_sudoku
								.get(key));
						the_sudoku.remove(key);

						this.eliminateC(o, a);
						this.eliminateR(i, a);
						this.eliminateS(i, o, a);
					}
				}
			}
		}
	}

	/**
	 * The first method invoked after loading the sudoku. Also invoked after
	 * figuring out a new value from the sudoku. It clears out the known values
	 * , from the corresponding row,column or local 3x3 square list of
	 * candidates.
	 */

	public void first() {
		for (int i = 0; i < 9; i++) {
			for (int o = 0; o < 9; o++) {
				if (this.unstableSudoku[i][o] != 0) {
					String eliminated = Integer
							.toString(this.unstableSudoku[i][o]);
					this.eliminateC(o, eliminated);
					this.eliminateR(i, eliminated);
					this.eliminateS(i, o, eliminated);
					this.lockSingleCandidate();
				}
			}
		}
	}

	/**
	 * Start looking for hidden Singles or naked Pairs.
	 */
	public void candidateCounter() {
		for (int i = 1; i < 10; i++) {
			String counted = Integer.toString(i);
			for (int row = 0; row < 9; row++) {
				for (int column = 0; column < 9; column++) {
					this.countRow(row, counted);
					this.countColumn(column, counted);
					this.countSquare(row, column, counted);
				}
			}
		}

	}

	/**
	 * Looks for Hidden Singles or Naked pairs in the row. If Hidden Single
	 * found , put the value in the 2D int array. Remove the value from the
	 * corresponding row, column or local 3x3 square list of candidates. Start
	 * looking for naked singles again.
	 * 
	 * If Naked pair found,remove the pair from the candidates list of the row.
	 * 
	 * @param row
	 *            the row currently being checked
	 * @param counted
	 *            the current value currently being checked
	 */
	public void countRow(int row, String counted) {

		int count = 0;
		IntStack is = new IntStack();
		for (int o = 0; o < 9; o++) {
			StringBuilder sb = new StringBuilder();
			sb.append(row);
			sb.append(o);
			String key = sb.toString();
			if (the_sudoku.containsKey(key)) {
				if (the_sudoku.get(key).contains(counted)) {
					count++;
					is.push(o);
				}
			}
		}

		if (count == 1) {
			StringBuilder sb = new StringBuilder();
			sb.append(row);
			sb.append(is.peek());
			String key = sb.toString();
			the_sudoku.remove(key);
			this.unstableSudoku[row][is.peek()] = Integer.parseInt(counted);
			this.first();
		}
		if (count == 2) {
			int column1 = is.pop(), column2 = is.pop();
			int a = (column1 / 3) * 3, b = (column2 / 3) * 3, c = (row / 3) * 3;
			if (a == b) {
				for (int i = c; i < c + 3; i++) {
					for (int o = a; o < a + 3; o++) {
						if (i != row) {
							StringBuilder sb = new StringBuilder();
							sb.append(i);
							sb.append(o);
							String key = sb.toString();
							if (the_sudoku.containsKey(key)) {
								if (the_sudoku.get(key).contains(counted)) {
									String replacedOne = the_sudoku.get(key)
											.replaceAll(counted, "");
									the_sudoku.put(key, replacedOne);
									this.lockSingleCandidate();
								}
							}
						}
					}
				}
			}
		}

	}

	/**
	 * Looks for Hidden Singles or Naked pairs in the column. If Hidden Single
	 * found , put the value in the 2D int array. Remove the value from the
	 * corresponding row, column or local 3x3 square list of candidates. Start
	 * looking for naked singles again.
	 * 
	 * If Naked pair found,remove the pair from the candidates list of the
	 * column.
	 * 
	 * @param column
	 *            the row currently being checked
	 * @param counted
	 *            the current value currently being checked
	 */
	public void countColumn(int column, String counted) {
		int count = 0;
		IntStack is = new IntStack();
		for (int o = 0; o < 9; o++) {
			StringBuilder sb = new StringBuilder();
			sb.append(o);
			sb.append(column);
			String key = sb.toString();
			if (the_sudoku.containsKey(key)) {
				if (the_sudoku.get(key).contains(counted)) {
					count++;
					is.push(o);
				}
			}
		}
		if (count == 1) {
			StringBuilder sb = new StringBuilder();
			sb.append(is.peek());
			sb.append(column);
			String key = sb.toString();
			the_sudoku.remove(key);
			this.unstableSudoku[is.peek()][column] = Integer.parseInt(counted);
			this.first();
		}
		if (count == 2) {
			int row1 = is.pop(), row2 = is.pop();
			int a = (row1 / 3) * 3, b = (row2 / 3) * 3, c = (column / 3) * 3;
			if (a == b) {
				for (int i = a; i < a + 3; i++) {
					for (int o = c; o < c + 3; o++) {
						if (o != column) {
							StringBuilder sb = new StringBuilder();
							sb.append(i);
							sb.append(o);
							String key = sb.toString();
							if (the_sudoku.containsKey(key)) {
								if (the_sudoku.get(key).contains(counted)) {
									String replacedOne = the_sudoku.get(key)
											.replaceAll(counted, "");
									the_sudoku.put(key, replacedOne);
									this.lockSingleCandidate();
								}
							}
						}
					}
				}
			}
		}

	}

	/**
	 * Looks for Hidden Singles or Naked pairs in the local 3x3 square specified
	 * by the row and column passed in. If Hidden Single found , put the value
	 * in the 2D int array. Remove the value from the corresponding row, column
	 * or local 3x3 square list of candidates. Start looking for naked singles
	 * again.
	 * 
	 * If Naked pair found,remove the pair from the candidates list of the 3x3
	 * square candidates.
	 * 
	 * @param row
	 *            the row to specify the 3x3 square
	 * @param column
	 *            the column to specify the 3x3 square
	 * @param counted
	 *            the current value currently being checked
	 */
	public void countSquare(int row, int column, String counted) {
		int count = 0;
		IntStack is1 = new IntStack();
		IntStack is2 = new IntStack();
		int x = (row / 3) * 3, y = (column / 3) * 3;
		for (int i = x; i < x + 3; i++) {
			for (int o = y; o < y + 3; o++) {
				StringBuilder sb = new StringBuilder();
				sb.append(i);
				sb.append(o);
				String key = sb.toString();
				if (the_sudoku.containsKey(key)) {
					if (the_sudoku.get(key).contains(counted)) {
						count++;
						is1.push(i);
						is2.push(o);
					}
				}
			}
		}
		if (count == 1) {
			StringBuilder sb = new StringBuilder();
			sb.append(is1.peek());
			sb.append(is2.peek());
			String key = sb.toString();
			the_sudoku.remove(key);
			this.unstableSudoku[is1.peek()][is2.peek()] = Integer
					.parseInt(counted);
			this.first();
		}
		if (count == 2) {
			int row1 = is1.pop(), row2 = is1.pop();
			int column1 = is2.pop(), column2 = is2.pop();
			if (row1 == row2) {
				for (int i = 0; i < 9; i++) {
					if (column1 != i && column2 != i) {
						StringBuilder sb = new StringBuilder();
						sb.append(row1);
						sb.append(i);
						String key = sb.toString();
						if (the_sudoku.containsKey(key)) {
							if (the_sudoku.get(key).contains(counted)) {
								String replacedOne = the_sudoku.get(key)
										.replaceAll(counted, "");
								the_sudoku.put(key, replacedOne);
								this.lockSingleCandidate();
							}
						}
					}
				}
			} else if (column1 == column2) {
				for (int i = 0; i < 9; i++) {
					if (row1 != i && row2 != i) {
						StringBuilder sb = new StringBuilder();
						sb.append(i);
						sb.append(column1);
						String key = sb.toString();
						if (the_sudoku.containsKey(key)) {
							if (the_sudoku.get(key).contains(counted)) {
								String replacedOne = the_sudoku.get(key)
										.replaceAll(counted, "");
								the_sudoku.put(key, replacedOne);
								this.lockSingleCandidate();
							}
						}
					}
				}
			}
		}

	}

	/**
	 * Another method to look for naked pairs. While being able to find naked
	 * pairs in the candidate counter method , some of them are still
	 * undiscovered. This method takes care of those.
	 * 
	 */
	public void nakedPairs() {
		for (int i = 0; i < 9; i++) {
			for (int o = 0; o < 9; o++) {
				StringBuilder sb = new StringBuilder();
				sb.append(i);
				sb.append(o);
				String key = sb.toString();
				if (the_sudoku.containsKey(key)) {
					if (the_sudoku.get(key).length() == 2) {
						this.nakedPairRow(i, o, key);
						this.nakedPairColumn(i, o, key);
					}
				}
			}
		}
	}

	/**
	 * Continuation of the nakedPairs() method. Looks for naked pairs in a
	 * column.
	 */
	public void nakedPairColumn(int row, int column, String daKey) {
		for (int i = 0; i < 9; i++) {
			if (i != row) {
				StringBuilder sb = new StringBuilder();
				sb.append(i);
				sb.append(column);
				String key = sb.toString();
				if (the_sudoku.containsKey(key)) {
					if (the_sudoku.get(key).length() == 2) {
						if (the_sudoku.get(key).equals(the_sudoku.get(daKey))) {
							String pair1 = the_sudoku.get(key).substring(0, 1);
							String pair2 = the_sudoku.get(key).substring(1, 2);
							for (int m = 0; m < 9; m++) {
								if (m != i && m != row) {
									StringBuilder sb1 = new StringBuilder();
									sb1.append(m);
									sb1.append(column);
									String key1 = sb1.toString();
									if (the_sudoku.containsKey(key1)) {
										if (the_sudoku.get(key1)
												.contains(pair1)) {
											String replacedOne = the_sudoku
													.get(key1).replace(pair1,
															"");
											the_sudoku.put(key1, replacedOne);
											this.lockSingleCandidate();
										}
										if (the_sudoku.containsKey(key1)) {
											if (the_sudoku.get(key1).contains(
													pair2)) {
												String replacedOne = the_sudoku
														.get(key1).replace(
																pair2, "");
												the_sudoku.put(key1,
														replacedOne);
												this.lockSingleCandidate();
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

	}

	/**
	 * Continuation of the nakedPairs() method. Looks for naked pairs in a row.
	 */
	public void nakedPairRow(int row, int column, String daKey) {
		for (int i = 0; i < 9; i++) {
			if (i != column) {
				StringBuilder sb = new StringBuilder();
				sb.append(row);
				sb.append(i);
				String key = sb.toString();
				if (the_sudoku.containsKey(key)) {
					if (the_sudoku.get(key).length() == 2) {
						if (the_sudoku.get(key).equals(the_sudoku.get(daKey))) {
							String pair1 = the_sudoku.get(key).substring(0, 1);
							String pair2 = the_sudoku.get(key).substring(1, 2);
							for (int m = 0; m < 9; m++) {
								if (m != i && m != column) {
									StringBuilder sb1 = new StringBuilder();
									sb1.append(row);
									sb1.append(m);
									String key1 = sb1.toString();
									if (the_sudoku.containsKey(key1)) {
										if (the_sudoku.get(key1)
												.contains(pair1)) {
											String replacedOne = the_sudoku
													.get(key1).replace(pair1,
															"");
											the_sudoku.put(key1, replacedOne);
											this.lockSingleCandidate();
										}
										if (the_sudoku.containsKey(key1)) {
											if (the_sudoku.get(key1).contains(
													pair2)) {
												String replacedOne = the_sudoku
														.get(key1).replace(
																pair2, "");
												the_sudoku.put(key1,
														replacedOne);
												this.lockSingleCandidate();
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * This method Seeks Hidden Pairs.
	 */
	public void hiddenRow() {
		for (int i = 1; i < 10; i++) {
			String counted = Integer.toString(i);
			for (int row = 0; row < 9; row++) {
				this.hidden(row, counted);

			}
		}

	}

	/**
	 * Continuation of the hiddenRow() method.
	 */
	public void hidden(int row, String counted) {
		int count = 0;
		IntStack is = new IntStack();

		for (int i = 0; i < 9; i++) {
			StringBuilder sb = new StringBuilder();
			sb.append(row);
			sb.append(i);
			String key = sb.toString();
			if (the_sudoku.containsKey(key)) {
				if (the_sudoku.get(key).contains(counted)) {
					count++;
					is.push(i);
				}
			}
		}
		if (count == 2) {
			this.checkRow(row, counted, is.pop(), is.pop());
		}

	}

	/**
	 * Continuation of hiddenRow() and hidden() methods.
	 */
	public void checkRow(int row, String counted, int a, int b) {
		for (int i = 1; i < 10; i++) {
			int count = 0;
			if (i != Integer.parseInt(counted)) {
				String counted2 = Integer.toString(i);
				IntStack is = new IntStack();
				for (int o = 0; o < 9; o++) {
					StringBuilder sb = new StringBuilder();
					sb.append(row);
					sb.append(o);
					String key = sb.toString();
					if (the_sudoku.containsKey(key)) {
						if (the_sudoku.get(key).contains(counted2)) {
							count++;
							is.push(o);
						}
					}
				}
				if (count == 2 && a == is.pop() && b == is.pop()) {
					StringBuilder sb1 = new StringBuilder();
					sb1.append(row);
					sb1.append(a);
					StringBuilder sb2 = new StringBuilder();
					sb2.append(row);
					sb2.append(b);
					StringBuilder sb3 = new StringBuilder();
					sb3.append(counted2);
					sb3.append(counted);
					the_sudoku.put(sb1.toString(), sb3.toString());
					the_sudoku.put(sb2.toString(), sb3.toString());
					this.candidateCounter();
				}
			}
		}

	}

	/**
	 * Prints the values of the 2Dimensional int array.
	 */
	public void print() {
		for (int i = 0; i < 9; i++) {
			System.out.println();
			for (int o = 0; o < 9; o++) {
				System.out.print(this.unstableSudoku[i][o]);
			}
		}
	}

}
