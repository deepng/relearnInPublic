package com.nidee.remoteLearn.genericAlgos;

/**
 * Add a factorial algorithm here
 */
public class Factorial {
    public static void main(String[] args) {
        int number = 5;
        System.out.println(String.format("The factorial of %d is %d", number, factorial(number)));
    }

    private static int factorial(int number) {
        if(number < 0) return -1;
        if(number == 0) return 1;
        return number * factorial(number - 1);
    }
}
