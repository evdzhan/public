package evdzhan_mustafa.algorithms;

/**
 * By Evdzhan Mustafa. Created on 26/02/16.
 */
public class Euclid {

  public static void main(String[] args) {

    int a = 221;
    int b = 51;

    int greatestCommonDivisor = euclid(a, b);
    System.out.println(greatestCommonDivisor);
  }

  private static int euclid(int a, int b) {

    int remainder = b;
    int dividend = a ;
    int divisor = b;

    while (remainder != 0) {
      remainder = dividend % divisor;
      dividend = divisor;
      divisor = remainder;
    }
    return dividend;
  }

}
