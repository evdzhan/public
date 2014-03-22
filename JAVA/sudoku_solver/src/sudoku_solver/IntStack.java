package sudoku_solver;

public class IntStack {

	int[] theStack;
	int firstFree;
	private static final int DEFAULT_MAXIMUM = 10;

	public IntStack() {
		this(DEFAULT_MAXIMUM);
	}

	public IntStack(int theSize) {
		this.theStack = new int[theSize];
		firstFree = 0;

	}

	public void push(int added) {
		if (theStack.length == firstFree) {
			throw new Error("Full stack");
		}
		theStack[firstFree] = added;

		firstFree++;

	}

	public boolean isEmpty() {
		if (firstFree == 0) {
			return true;
		}
		return false;
	}

	public int pop() {
		if (this.isEmpty()) {
			throw new Error("Empty Stack");
		}
		firstFree--;
		return theStack[firstFree];
	}

	public int peek() {
		if (this.isEmpty()) {
			throw new Error("Empty Stack");
		}
		return theStack[firstFree - 1];
	}

	public int depth() {
		if (this.isEmpty()) {
			throw new Error("Empty Stack");
		}
		return firstFree;
	}

}
