package abtract_data_types;

import java.util.Scanner;


/**
 * This class finds the greatest common divisor among two numbers. It uses the Euclid Algorithm. ->
 * http://en.wikipedia.org/wiki/Euclidean_algorithm
 *
 * @author Evdzhan Mustafa enm3@aber.ac.uk
 */
public class Euclid {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        System.out.println("Enter first number : ");

        long a = scan.nextLong();

        System.out.println("Enter second number : ");

        long b = scan.nextLong();

        while (b != 0) {
            long rem = a % b;

            a = b;

            b = rem;

        }

        System.out.println("The greatest common divisor of those numbers is : " + a);
        scan.close();
    }

}
