package evdzhan_mustafa.generics;

/**
 * By Evdzhan Mustafa. Created on 22/02/16.
 */
public class Person {


  protected String name = "";
  int age = 0;

  public Person() {
  }

  public Person(int age, String name) {
    this.age = age;
    this.name = name;
  }

  public void personSayHi() {
    System.out.println("Hi i am a person.");
  }


}
