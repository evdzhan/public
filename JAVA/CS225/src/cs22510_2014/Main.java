package cs22510_2014;

public class Main {

	public static void main(String[] args) {

		// double decimaldegrees = (data - (100 * degrees)) / 60.0;
		// System.out.println(minutes);

		String file1 = "/home/evdjoint/gps_1.dat";
		String file2 = "/home/evdjoint/gps_2.dat";

		Controller controller = new Controller(file1, file2);
		 
		
		
		// for(Location x : controller.locations) {
		// System.out.println(x);
		// }
		/*
		 * System.out.println(a); controller.stream2.read();
		 * System.out.println(controller.stream1.satelitesAreOK);
		 * System.out.println(controller.stream2.satelitesAreOK);
		 */}

}
