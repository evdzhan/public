package evdzhan_mustafa.generics;

/**
 * By Evdzhan Mustafa. Created on 22/02/16.
 */
public class Main {

  public static void main(String[] args) {
//    testComparator();



  }

  private static void testComparator() {
    Person p1 = new Person(17, "abc");
    Person p2 = new Person(17, "abc");
    PersonComparator pc = new PersonComparator();
    System.out.println(pc.compare(p1, p2));
    Student s1 = new Student(17, "abc", 123);
    System.out.println(pc.compare(p1, s1));
  }
}
