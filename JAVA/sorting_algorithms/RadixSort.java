/*
 * Created on Jun 28, 2005
 *
 */
package sorting2014;


import linearStructures.*;
/**
 * @author rcs
 *
 */
public class RadixSort implements Sorter {

	private static final int RADIX=100;
	private int DIGITS;
	public void sort(Comparable[] items, int cutoff) 
	{
		radixSort(items);
	}
	
	private void radixSort(Object[] items)
	{
		
		int maxItems=items.length;
		String str;
		int maxLength=0;
		for (int i= 0; i<maxItems; i++)
		{
			str=items[i].toString();
			maxLength=maxLength<str.length()?str.length():maxLength;
		}
		DIGITS=maxLength;
		char index;
		Queue[] queues = new Queue[RADIX];
		for (int d=0; d<RADIX; d++)
		{
			queues[d]=new Queue(maxItems);
		}
		for (int d=DIGITS, factor=1;d >= 0; factor *= RADIX, d--)
		{
			for (int j=0; j< items.length; j++)
			{
				str=items[j].toString();
				
				if (d<str.length())
				{
					index =str.charAt(d);
				} else
				{
					index =0;
				}
				queues[index].add(items[j]);	
			}
			for (int j=0, k=0; j< RADIX; j++)
			{
				while (!queues[j].isEmpty())
				{
					items[k++]=(queues[j].remove());
				}
			}
		}
	}

}
