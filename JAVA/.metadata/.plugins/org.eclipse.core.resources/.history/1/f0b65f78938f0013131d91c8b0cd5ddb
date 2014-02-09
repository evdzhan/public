package abstractDataTypes;

public class Person implements Comparable<Person>{

	private int age;
	private String name;
	private int salary;
	public Person(int age,String name,int salary){
		this.age=age; this.name=name; this.salary = salary;
	}
	@Override
	public int compareTo(Person otherPerson) {
		boolean ageIsSame = otherPerson.age == this.age ;
		boolean nameIsSame = otherPerson.name.equals(this.name);
		boolean salaryIsSame = otherPerson.salary == this.salary;
		
		if(ageIsSame  && nameIsSame && salaryIsSame) {
			return 0;
			
		} else if(ageIsSame && nameIsSame) {
			
			return otherPerson.salary > this.salary ? -1 : 1 ;
			
		} else if(ageIsSame){
			
		    return otherPerson.name.compareTo(this.name);
		} else {
			
			return otherPerson.age > this.age ? -1 : 1;
			
		}
		
		
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	

}
