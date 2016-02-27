package evdzhan_mustafa.adt.tests;


import org.junit.Before;
import org.junit.Test;

import evdzhan_mustafa.adt.Queue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * By Evdzhan Mustafa. Created on 18/02/16.
 */
public class QueueTest {

  Queue<Integer> queue;

  @Before
  public void setUp() throws Exception {
    queue = new Queue<>(5);
  }

  @Test
  public void testPush() throws Exception {
    for (int i = 0; i < 5; ++i) {
      queue.push(i);
    }
    try {
      queue.push(5);
      fail("Didn't throw 'Queue full.'");
    } catch (Exception expected) {
      assertEquals("Queue full.", expected.getMessage());
    }

  }

  @Test
  public void testPopNoPushInBetween() throws Exception {
    for (int i = 0; i < 5; ++i) {
      queue.push(i);
    }
    for (int i = 0; i < 5; ++i) {
      int popValue = queue.pop();
      assertEquals(i, popValue);
    }

    try {
      queue.pop();
      fail("Didn't throw 'Queue empty.'");
    } catch (Exception expected) {
      assertEquals("Queue empty.", expected.getMessage());
    }
  }

  @Test
  public void testPopWithPushInBetween() throws Exception {
    queue.push(0);
    queue.push(1);
    queue.push(2);
    assertEquals(0, (int) queue.pop());
    queue.push(3);
    assertEquals(1, (int) queue.pop());
    assertEquals(2, (int) queue.pop());
    queue.push(4);
    queue.push(5);
    queue.push(6);
    assertEquals(3, (int) queue.pop());
    assertEquals(4, (int) queue.pop());
    assertEquals(5, (int) queue.pop());
    assertEquals(6, (int) queue.pop());


  }

}