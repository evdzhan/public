package evdzhan_mustafa.adt.tests;


import org.junit.Before;
import org.junit.Test;

import evdzhan_mustafa.adt.Stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * By Evdzhan Mustafa. Created on 18/02/16.
 */
public class StackTest {

  Stack<Integer> stack;

  @Before
  public void setUp() throws Exception {
    stack = new Stack<>(5);
  }

  @Test
  public void testPush() throws Exception {
    for (int i = 0; i < 5; ++i) {
      stack.push(i);
    }
    try {
      stack.push(5);
      fail("Didn't throw 'Stack full.'");
    } catch (Exception expected) {
      assertEquals("Stack full.", expected.getMessage());
    }

  }

  @Test
  public void testPop() throws Exception {
    for (int i = 0; i < 5; ++i) {
      stack.push(i);
    }
    for (int i = 4; i >= 0; --i) {
      int popValue = stack.pop();
      assertEquals(i, popValue);
    }

    try {
      stack.pop();
      fail("Didn't throw 'Stack empty.'");
    } catch (Exception expected) {
      assertEquals("Stack empty.", expected.getMessage());
    }
  }

  @Test
  public void testConstructor() throws Exception {
    try {
      stack = new Stack<>(-1);
      fail("Didn't throw 'invalid size' exception.");
    } catch (Exception expected) {
      assertEquals("Invalid initial size.", expected.getMessage());
    }
  }


}