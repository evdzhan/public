import java.util.Arrays;
import abstractDataTypes.Person;
 public class ComparableTest {

	 
	public static void main(String[] args) {
		 Person a = new Person(27,"John",1500);
		  Person b = new Person(26,"John",1500);
		  Person c = new Person(35,"Ivan",1350);
		  Person d = new Person(17,"Barbara",1200);
		  Person e = new Person(55,"Tom",1800);
		  Person f = new Person(55,"Tom",1650);
	  
	 Person[] array = {	 a,b,c,d,e,f };
	 Arrays.sort(array);
	 for( Person  x : array ){
		 System.out.println("Name is " +x.getName() +", Age is "+ x.getAge()+", Salary is " + x.getSalary());
	 }
	   
	}
}