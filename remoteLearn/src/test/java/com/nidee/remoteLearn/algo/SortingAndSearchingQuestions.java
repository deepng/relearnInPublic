package com.nidee.remoteLearn.algo;

import com.nidee.remoteLearn.algoProblems.Fibonnacci;
import com.nidee.remoteLearn.algoProblems.MoveAllZeros;
import com.nidee.remoteLearn.algoProblems.PalindromicStrings;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SortingAndSearchingQuestions {
    List<Integer> l1 = Arrays.asList(1, 3, 5, 6, 13, 31, 37, 53, 55, 61);
    List<Integer> l2 = Arrays.asList(1, 2, 3, 7, 15, 33, 37, 53, 57, 63);
    List<Integer> l1AndL2 = Arrays.asList(1, 3, 37, 53);

    @Test(description = "Given two list of sorted & unique integers, give the common items in them." +
            "This is how AND queries are implemented")
    public void getTheCommonItemsInList() {
        List<Integer> l3 = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (i<l1.size() && j < l2.size()) {
            if(Objects.equals(l1.get(i), l2.get(j))) {
                l3.add(l1.get(i));
                i++;
                j++;
            } else if(l1.get(i) < l2.get(j)) {
                i++;
            } else {
                j++;
            }
        }
        Assert.assertEquals(l3, l1AndL2, String.format("We were expecting %s, but got %s. They are not same ", l1AndL2, l3));

    }

    @Test
    public void testFibonnacciRecursion() {
        int n = 10;
        List<Integer> ans = new ArrayList<>(n+1);
        for(int i=0; i<=n; i++) {
            ans.add(null);
        }
        Fibonnacci fibonnacci = new Fibonnacci();
        int result = fibonnacci.fibRecursion(ans, n);
        Assert.assertEquals(result, 55, String.format("We were expecting %s, but got %s. They are not same ", 55, result));
    }

    @Test
    public void testFibonnacciIteration() {
        int n = 10;
        List<Integer> ans = new ArrayList<>(n+1);
        for(int i=0; i<=n; i++) {
            ans.add(null);
        }
        Fibonnacci fibonnacci = new Fibonnacci();
        int result = fibonnacci.fibRecursion(ans, n);
        Assert.assertEquals(result, 55, String.format("We were expecting %s, but got %s. They are not same ", 55, result));
    }

    @Test(dataProvider = "testMoveNonZeroData")
    public void testMoveNonZero(int[] arr, int[] expected) {
//        int[] arr = {0,1,0,3,12};
//        int[] expected = {1,3,12,0,0};
        MoveAllZeros moveAllZeros = new MoveAllZeros();
        arr = moveAllZeros.moveAllZeros(arr);
        Assert.assertEquals(arr, expected, String.format("We were expecting %s, but got %s. They are not same ", Arrays.toString(expected), Arrays.toString(arr)));
    }

    @DataProvider(name = "testMoveNonZeroData")
    public Object[][] testMoveNonZeroData() {
        return new Object[][] {
                {new int[]{0,1,0,3,12}, new int[]{1,3,12,0,0}},
                {new int[]{0,0,1}, new int[]{1,0,0}},
                {new int[]{1,2,3}, new int[]{1,2,3}},
                {new int[]{0,0,0}, new int[]{0,0,0}},
                {new int[]{}, new int[]{}},
                {new int[]{0}, new int[]{0}},
                {new int[]{1}, new int[]{1}},
                {new int[]{0,1}, new int[]{1,0}},
                {new int[]{1,0}, new int[]{1,0}},
        };
    }

    @Test(dataProvider = "testPalindromicDecompositionData")
    public void testPalindromicDecomposition(String s, ArrayList<String> expected) {
        PalindromicStrings palindromicStrings = new PalindromicStrings();
        ArrayList<String> result = palindromicStrings.generate_palindromic_decompositions(s);
        Assert.assertEqualsNoOrder(result.toArray(), expected.toArray(),
                String.format("We were expecting %s, but got %s. They are not same ", expected, result));
    }


    @DataProvider(name = "testPalindromicDecompositionData")
    public Object[][] testPalindromicDecompositionData() {
        return new Object[][] {
                {"abracadabra", new ArrayList<>(Arrays.asList("a", "b", "r", "a", "c", "a", "d", "a", "b", "r", "a", "ada", "aca"))},
                {"abba", new ArrayList<>(Arrays.asList("a", "b", "b", "a", "bb", "abba"))},
                {"racecar", new ArrayList<>(Arrays.asList("r", "a", "c", "e", "c", "a", "r", "cec", "aceca", "racecar"))},
                {"noon", new ArrayList<>(Arrays.asList("n", "o", "o", "n", "oo", "noon"))},
                {"abc", new ArrayList<>(Arrays.asList("a", "b", "c"))},
                {"a", new ArrayList<>(Arrays.asList("a"))},
                {"", new ArrayList<>()}
        };
    }

}
