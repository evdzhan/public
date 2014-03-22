package sorting_algorithms;

/**
 * Merge Sort.
 * @author Evdzhan Mustafa enm3@aber.ac.uk
 *
 */
public class MergeSort implements Sorter {
	
	private Comparable[] primaryArr; // the main array where the data will be sorted
	private Comparable[] secondaryArr; // linear extra memory  

	@SuppressWarnings("unchecked")
	public void sort(Comparable[] items, int cutoff) {

		this.primaryArr = items;

		secondaryArr = new Comparable[items.length];

		mergesort(0, items.length - 1);

	}

	/**
	 * Merge sort - works by continually splitting the array in halves. Split in
	 * halves until arrays of size 1 is left. This is achieved by recursive
	 * calls. After both arrays are of size split , they are sorted and then
	 * rearranged into, the primary array.
	 * 
	 * @param first  the index of the first element in the (sub)array
	 * @param last  the index of the last element in the (sub)array
	 */
	private void mergesort(int first, int last) {

		if (first < last) { // i.e. split only if the array size is > 1

			int mid = first + ((last - first) / 2); // find the index of the
														// middle element

			mergesort(first, mid); // recursively split the left sub array

			mergesort(mid + 1, last); // recursively split the right sub array 

			merge(first, mid, last); // on returning merge the arrays in sorted manner

		}

	}

	/**
	 * Merges two sub arrays, by putting them into the primaryArr in sorted
	 * manner. That is :
	 * 1) compare the elements, indexed as next, from both sub arrays 
	 * 2) take the lower one and place it into the primary array
	 * 4) increment the next index by 1, from the array that the value was picked 
	 * 3) repeat 
	 * 
	 * Repeat this until both sub arrays are placed onto the primarryArr.
	 * 
	 * @param first   index of the first element
	 * @param mid    index of the middle element
	 * @param last   index of the last element
	 */
	private void merge(int first, int mid, int last) {

		for (int i = first; i <= last; i++) { // copy the current sub arrays
			secondaryArr[i] = primaryArr[i];
		}

		int arr_one_next = first; // the next element index of the first sub array
		int arr_two_next = mid + 1;  // the next element index of the second sub array
		
		int primary_arr_next = first; // index of the next element in primary array 

		while (arr_one_next <= mid && arr_two_next <= last) {
			
			/* compare to determine which element to take first	*/		
			if (secondaryArr[arr_one_next].compareTo(secondaryArr[arr_two_next]) <= 0) { 
				
				/* the first sub array's next was lower, place it in the primary array*/
				primaryArr[primary_arr_next] = secondaryArr[arr_one_next]; 					
				arr_one_next++;
				
			} else {
				
				/* the second sub array's next was lower, place it in the primary array*/
				primaryArr[primary_arr_next] = secondaryArr[arr_two_next];
				arr_two_next++;

			}
			
			primary_arr_next++; // increment the index, 
			//so we place the next element in the next place

		}
		
		/* if any elements in arr_one sub array are not placed into the main array, 
		 * place them. Note that this cannot happen for arr_two. */
		while (arr_one_next <= mid) { 
			primaryArr[primary_arr_next] = secondaryArr[arr_one_next];
			arr_one_next++;
			primary_arr_next++;
		}

	}

}