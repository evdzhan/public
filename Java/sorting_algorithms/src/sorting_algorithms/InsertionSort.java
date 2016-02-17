package sorting_algorithms;

/**
 * Insertion sort.  
 * @author Evdzhan Mustafa enm3@aber.ac.uk
 */
@SuppressWarnings("rawtypes")
public class InsertionSort implements Sorter {

	private Comparable[] items ;
	@Override
	public void sort(Comparable[] items, int cutoff) {
		
		this.items = items ;
		this.insertionsort();

	}

	/**
	 * Selection Sort The algorithm loops through the array of items, after
	 * iteration number N, the first N+1 elements of the array are in sorted
	 * order. In iteration number N, the N+1 element is inserted to the correct
	 * position in the already sorted N elements, thus the sorted elements
	 * become N+1. This causes N - P elements to be shifted to make space for
	 * the new value, where P is the index of the newly inserted(sorted) item,
	 * and N is the number of iteration.
	 * 
	 * @param items the array to be sorted
	 */

	@SuppressWarnings("unchecked")
	private void insertionsort() {

		for (int i = 1; i < items.length; ++i) {

			Comparable temp = items[i]; // the current element to compare

			int j; // integer to index the shifted value

			for (j = i - 1; j >= 0; --j) {

				if (items[j].compareTo(temp) < 0) {
					break; // the current position is correct - stop shifting
				}
				items[j + 1] = items[j]; // continually shift the previous value to current position
			}
			items[j + 1] = temp; // insert the value to it's correct position
		}
	}
	 
}
