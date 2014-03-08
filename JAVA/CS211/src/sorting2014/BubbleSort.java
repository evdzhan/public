/*
 * Created on Nov 28, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package sorting2014;


public class BubbleSort implements Sorter {

	/* (non-Javadoc)
	 * @see sorting.Sorter#sort(java.lang.Comparable[])
	 */
	@SuppressWarnings("unchecked")
	public void sort(Comparable[] items, int cutoff) {
		Comparable temp;
		for (int i=0; i<items.length-1; i++)
		{
			for (int j=0; j<items.length-1-i; j++)
			{
				if (items[j].compareTo(items[j+1])>0)
				{
					temp=items[j+1];
					items[j+1]=items[j];
					items[j]=temp;
				}
			}
		}
	}
}
