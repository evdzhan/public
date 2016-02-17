package labdas_test;

/**
 * By Evdzhan Mustafa. Created on 17/02/16.
 */
public class LambdaTester_01 {


    public static void main(String args[]) {

        BinaryMathOperation addition = (int firstOperand, int secondOperand) -> firstOperand + secondOperand;
        BinaryMathOperation subtraction = (int firstOperand, int secondOperand) -> firstOperand - secondOperand;
        BinaryMathOperation multiplication = (int firstOperand, int secondOperand) -> firstOperand * secondOperand;
        BinaryMathOperation division = (int firstOperand, int secondOperand) -> firstOperand / secondOperand;
        BinaryMathOperation modulo = (int firstOperand, int secondOperand) -> firstOperand % secondOperand;

        final int TEN = 10;
        final int FIVE = 5;

        int result;
        result = addition.applyOperation(TEN, FIVE);
        System.out.printf("%d + %d = %d\n", TEN, FIVE, result);

        result = subtraction.applyOperation(TEN, FIVE);
        System.out.printf("%d - %d = %d\n", TEN, FIVE, result);

        result = multiplication.applyOperation(TEN, FIVE);
        System.out.printf("%d * %d = %d\n", TEN, FIVE, result);

        result = division.applyOperation(TEN, FIVE);
        System.out.printf("%d / %d = %d\n", TEN, FIVE, result);

        result = modulo.applyOperation(TEN, FIVE);
        System.out.printf("%d %% %d = %d \n", TEN, FIVE, result);


    }


    /**
     * Take two arguments, and produce single result. All integers.
     */
    @FunctionalInterface
            // this annotation is optional
    interface BinaryMathOperation {
        int applyOperation(int firstOperand, int secondOperand);
    }

}


