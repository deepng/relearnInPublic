package com.nidee.remoteLearn.algoProblems;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Of A String
 * Find all palindromic decompositions of a given string s.
 *
 * A palindromic decomposition of string is a decomposition of the string into substrings,
 * such that all those substrings are valid palindromes.
 *
 *
 * example: "s": "abracadabra"
 * or
 * "abba"
 */
public class PalindromicStrings {

    ArrayList<String> output = new ArrayList();

    public  ArrayList<String> generate_palindromic_decompositions(String s) {
        // Write your code here
        palindromic(s, 0, s.length() - 1);
        return output;
    }

    public  List<String> palindromic(String s, int start, int end) {
        if(start <= end) {
            for(int i=start; i<=end; i++) {
                String sub = s.substring(start, i+1);
                if(isPalindrome(sub)) {
                    output.add(sub);
                }
            }
            palindromic(s, start+1, end);
        }
        return output;
    }

    public boolean isPalindrome(String s) {
        int i=0;
        int j = s.length()-1;
        while (i < j) {
            if(s.charAt(i) != s.charAt(j))
                return false;
            else {
                i++;
                j--;
            }
        }
        return true;
    }

}
