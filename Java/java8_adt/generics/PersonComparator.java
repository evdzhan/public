package evdzhan_mustafa.generics;

import java.util.Comparator;

/**
 * By Evdzhan Mustafa. Created on 22/02/16.
 */
public class PersonComparator<T extends Person> implements Comparator<T> {


  @Override
  public int compare(T o1, T o2) {
    int ageDiff = o1.age - o2.age;
    int nameDiff = o1.name.compareTo(o2.name);
    if (nameDiff == 0) {
      return ageDiff;
    } else {
      return nameDiff;
    }
  }
}
