
package sorting2014;
/**
 *  
 * Created on Nov 28, 2003
 * @author rcs
 *  
 */
public class BubbleSort implements Sorter {

	 
	@SuppressWarnings("unchecked")
	public void sort(Comparable[] items, int cutoff) {
		Comparable temp;
		for (int i = 0; i < items.length - 1; i++) {
			for (int j = 0; j < items.length - 1 - i; j++) {
				if (items[j].compareTo(items[j + 1]) > 0) {
				 	temp = items[j + 1];
				 	items[j + 1] = items[j];
				 	items[j] = temp;
				}
			}
		}
	}
}
