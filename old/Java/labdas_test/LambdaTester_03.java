package labdas_test;

import java.util.function.Function;

/**
 * By Evdzhan Mustafa. Created on 17/02/16.
 */
public class LambdaTester_03 {

    public static void main(String args[]) {


        double totalRepayable = loanCalculator(1000, 10, LoanTypes.FLAT_10_percent);
        double totalRepayable2 = loanCalculator(1000, 10, LoanTypes.FLAT_25_percent);
        double totalRepayable3 = loanCalculator(1000, 5, LoanTypes.CRAZY_ONE);
        double totalRepayable4 = loanCalculator(1000, 10, LoanTypes.CRAZY_ONE);
        double totalRepayable5 = loanCalculator(1000, 15, LoanTypes.CRAZY_ONE);


        System.out.println(totalRepayable);
        System.out.println(totalRepayable2);
        System.out.println(totalRepayable3);
        System.out.println(totalRepayable4);
        System.out.println(totalRepayable5);
    }

    static double loanCalculator(int initialLoan, int years, Function<Integer, Double> interestRate) {
        return initialLoan + initialLoan * interestRate.apply(years);
    }

    private enum LoanTypes implements Function<Integer, Double> {

        FLAT_10_percent((years) -> years * 0.01),
        FLAT_25_percent((years) -> years * 0.025),
        CRAZY_ONE((Integer years) -> {
            if (years > 10) {
                return years * 0.025 * 2;
            } else {
                return years * 0.030 * 3;
            }
        });


        Function<Integer, Double> func;

        LoanTypes(Function<Integer, Double> func) {
            this.func = func;
        }

        @Override
        public Double apply(Integer integer) {
            return func.apply(integer);
        }


    }


}
