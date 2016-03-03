package evdzhan_mustafa.algorithms.tests;

import org.junit.Assert;
import org.junit.Test;

import evdzhan_mustafa.algorithms.MaxSubarraySum;

/**
 * By Evdzhan Mustafa. Created on 22/02/16.
 */
public class MaxSubarraySumTest {

  static int[][] data = {
          {-2, 11, -4, 13, -5, -2},
          {-2, 11, -4, 13, -5, -2, 100},
          {-2, 11, -12, 13, -5, -2, 100},
          {-3, 4, -1, 2, 1, -5, 4}
  };

  @Test
  public void test1() {
    Assert.assertEquals("1,3,20", MaxSubarraySum.maxSubarraySum(data[0]));
  }

  @Test
  public void test2() {
    Assert.assertEquals("1,6,113", MaxSubarraySum.maxSubarraySum(data[1]));
  }

  @Test
  public void test3() {
    Assert.assertEquals("3,6,106", MaxSubarraySum.maxSubarraySum(data[2]));
  }

  @Test
  public void test4() {
    Assert.assertEquals("1,4,6", MaxSubarraySum.maxSubarraySum(data[3]));
  }


}