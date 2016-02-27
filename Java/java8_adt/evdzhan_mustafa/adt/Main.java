package evdzhan_mustafa.adt;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * By Evdzhan Mustafa. Created on 22/02/16.
 */
public class Main {
  static BigInteger one = new BigInteger("1");

  public static void main(String[] args) {

    Scanner scanny = new Scanner(System.in);
    String input = scanny.next();
    System.out.println(getFactorial(new BigInteger(input)));
  }

  static BigInteger getFactorial(BigInteger bi) {
    if (bi.compareTo(one) == 0) {
      return one;
    } else {
      return getFactorial(bi.subtract(one)).multiply(bi);
    }
  }
}
