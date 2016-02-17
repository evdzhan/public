package labdas_test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * By Evdzhan Mustafa. Created on 17/02/16.
 */
public class LambdaTester_00 {

    public static void main(String args[]) {

        List<String> namesList1 = Arrays.asList("Eliot", "Dave", "Boris", "Anna", "Claudia");
        List<String> namesList2 = new ArrayList<>(namesList1);
        List<String> namesList3 = new ArrayList<>(namesList1);


        sortAnonymousInnerClass(namesList1);
        sortWithPureLambda(namesList2);
        sortWithMethodReferenceLambda(namesList3);

        assert namesList1.toString().equals(namesList2.toString());
        assert namesList2.toString().equals(namesList3.toString());

        System.out.println(namesList1);
        System.out.println(namesList2);
        System.out.println(namesList3);

    }

    private static void sortWithPureLambda(List<String> list) {
        //noinspection Convert2MethodRef
        Collections.sort(list, (String s1, String s2) -> s1.compareTo(s2));
    }

    private static void sortWithMethodReferenceLambda(List<String> list) {
        Collections.sort(list, String::compareTo);
    }

    private static void sortAnonymousInnerClass(List<String> list) {
        //noinspection Convert2Lambda,Anonymous2MethodRef
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
    }
}
