package uk.ac.aber.dcs.cs12420.blockmation.tests;
import uk.ac.aber.dcs.cs12420.blockmation.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestHolder {
	FrameHolder fh=new FrameHolder();
	@Before
	public void setUp() {
		
	}
	
	
	@Test
	public void testAdd() {
		char[][] someArray = new char[3][3];
		fh.add(someArray);
		assertArrayEquals(fh.giveChosenArray(0), someArray);
	}
	
	@Test
	public void testGetDimensionMethod() {
		char[][] asd = new char[9][9];
	
		 fh.add(asd);
		assertEquals(fh.getRowsColumns(),9);
		
	}
	@Test 
	public void testArraySize() {
		
			char[][] asd = new char[1][1];
			char[][] asd1 = new char[1][1];
			
			fh.add(asd1) ; fh.add(asd); 
			
		assertEquals(fh.giveArray().size(), 2);
	}
	@Test 
	public void testGiveChosenArray() {
		char[][] asd = new char[1][1];
		char[][] asd1 = new char[1][1];
		char[][] asd2 = new char[1][1];
		char[][] asd3 = new char[1][1];
		
		fh.add(asd) ; fh.add(asd1);  fh.add(asd2);  fh.add(asd3); 
		assertArrayEquals(asd, fh.giveChosenArray(0));
		assertArrayEquals(asd1, fh.giveChosenArray(1));
		assertArrayEquals(asd2, fh.giveChosenArray(2));
		assertArrayEquals(asd3, fh.giveChosenArray(3));	
	}

}
