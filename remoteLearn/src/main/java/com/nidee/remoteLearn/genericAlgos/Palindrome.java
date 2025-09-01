package com.nidee.remoteLearn.genericAlgos;

import org.springframework.stereotype.Component;

/**
 * check if a given string is a palindrome
 */
@Component
public class Palindrome {
    public static void main(String[] args) {
//        Properties properties = System.getProperties();
//        String testString = properties.getProperty("palindrome.test.string");
        String testString = "pranarp";
        if(testString == null || testString.isEmpty()) {
            System.out.println("Please set the properties =  palindrome.test.string");
        } else {
           if(!isPalindrome(testString)) {
               System.out.println(String.format("The string %s is not a palindrome", testString));
           } else {
               System.out.println(String.format("The string %s is a palindrome", testString));
           }
        }
    }

    private static boolean isPalindrome(String testString) {
        int i = 0;
        int j = testString.length() - 1;
        while (i < j) {
            if(testString.charAt(i) != testString.charAt(j)) {
                return false;
            }
            i++;
            j--;
            if(i == j || i > j) {
                return true;
            }
        }

        return false;
    }
}
