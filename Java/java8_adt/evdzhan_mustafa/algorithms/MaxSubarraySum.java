package evdzhan_mustafa.algorithms;

/**
 * By Evdzhan Mustafa. Created on 22/02/16.
 */
public class MaxSubarraySum {

  /**
   * This method finds a sub array with the maximum sum. The return format is Begin index , End
   * index , The sum of the elements in the array.
   *
   * @link https://en.wikipedia.org/wiki/Maximum_subarray_problem
   */
  public static String maxSubarraySum(int[] a) {

    int beginIndex = 0;
    int endIndex = 0;
    int maximumSum = 0;
    int currentSum = 0;
    for (int i = 0; i < a.length; ++i) {

      currentSum += a[i];

      if (currentSum > maximumSum) {
        maximumSum = currentSum;
        endIndex = i;
      } else if (currentSum < 0) {
        currentSum = 0;
        beginIndex = i + 1;
      }

    }

    @SuppressWarnings("StringBufferReplaceableByString")
    StringBuilder sb = new StringBuilder();
    sb.append(beginIndex).append(',');
    sb.append(endIndex).append(',');
    sb.append(maximumSum);
    return sb.toString();

  }


}
