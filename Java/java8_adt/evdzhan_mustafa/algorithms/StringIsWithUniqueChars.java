package evdzhan_mustafa.algorithms;

/**
 * By Evdzhan Mustafa. Created on 23/02/16.
 */
public class StringIsWithUniqueChars {

  public static void main(String[] args) {
    String b = "caxbc";
    System.out.println(isWithUniqueChars(b));
  }

  private static boolean isWithUniqueChars(String b) {
    for(int i = 0 ; i < b.length() ; ++i) {
      for(int j = i + 1 ; j < b.length(); ++j) {
        if(b.charAt(i) == b.charAt(j)) {
          return false;
        }
      }
    }

    return true;
  }
}
