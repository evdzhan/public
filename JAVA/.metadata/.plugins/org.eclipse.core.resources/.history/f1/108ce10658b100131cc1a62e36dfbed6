package sorting2014;

@SuppressWarnings("rawtypes")
/**
 *  Based on Quick Sort - Median Pivot. 
 *  Reverse sorts the  array items.
 * @author Evdzhan Mustafa enm3@aber.ac.uk
 *
 */
public class ReverseSorter implements Sorter {
private Comparable[] items;
	@Override
	public void sort(Comparable[] items, int cutoff) {
		this.items = items ;
		quicksort(0, items.length - 1);
	}

	 
	private void quicksort(int first, int last) {

		if (last <= first) {
			return; // if only one item in the array - it is sorted
		}

		int pivot = findMedian(first, last); // let the pivot be the
													// median of the three
													// values

		pivot = setPivot(first, last, pivot); // moves the pivot to it's
														// correct position

		if (first < pivot)
			quicksort(first, pivot - 1); // recursively sort the sub
												// array
												// left from the pivot
		if (last > pivot)
			quicksort(pivot + 1, last); // recursively sort the sub array
												// right from the pivot

	}

 
	private int setPivot(int left, int right, int pivotIndex) {

		swap(pivotIndex, left); // move the pivot out of the way

		int swap = left + 1; // set the initial swap position

		for (int i = swap; i <= right; i++) { // loop through swap until
												// right(inclusive)

			if (items[left].compareTo(items[i]) < 0) { // compare the current
														// item with the pivot

				swap(swap, i); // swap the current item with the swap
										// position
				swap++; // move the swap position to the right
			}

		}
		swap(left, swap - 1); // put the pivot to it's right place
		return swap - 1; // swap - 1 is the pivot's new index

	}

 
	private void swap(int indexFirst, int indexSecond) {

		Comparable temp = items[indexFirst]; // store the first in temporary
												// variable

		items[indexFirst] = items[indexSecond]; // change the first to the
												// second's value

		items[indexSecond] = temp; // change the second to the first's value

	}

	 
	private int findMedian(int first, int last) {

		int pivot;
		int mid = first + (last - first) / 2;

		if (items[first].compareTo(items[mid]) > 0) {

			if (items[first].compareTo(items[last]) < 0)
				pivot = first;
			
			else if (items[mid].compareTo(items[last]) > 0)
				pivot = mid;
			
			else	pivot = last;
			
			
		} else {
			
			if (items[mid].compareTo(items[last]) < 0)
				pivot = mid;
			
			else if (items[first].compareTo(items[last]) > 0)
				pivot = first;
			
			else	pivot = last;

		}
		return pivot;
	}

}
