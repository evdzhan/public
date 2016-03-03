package evdzhan_mustafa.generics;

/**
 * By Evdzhan Mustafa. Created on 22/02/16.
 */
public class Student extends Person {


  private int studentID = 123;

  public Student(int age, String name, int studentID) {
    super(age, name);
    this.studentID = studentID;
  }

  public Student() {
  }

  public void studentSayHi() {
    System.out.println("Hi i am a student.");
  }

}
