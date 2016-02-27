package evdzhan_mustafa.generics;

import java.util.ArrayList;
import java.util.List;

/**
 * By Evdzhan Mustafa. Created on 22/02/16.
 */
@SuppressWarnings("unused")
public class PersonStudentInspector {
  static List<Person> list = new ArrayList<>(5);

  static {

    list.add(new Person());
    list.add(new Student());
  }

  static public void consumer(List<? extends Person> coll) {
    // coll.add(new Student()); -- illegal !
    // coll.add(new Person()); -- illegal !

    coll.forEach(Person::personSayHi);
  }

  static public void producer(List<? super Person> coll) {
    coll.add(new Person());
    coll.add(new Student());
  }


  public static void main(String[] args) {
    consumer(list);

    producer(list);

    consumer(list);
  }
}
