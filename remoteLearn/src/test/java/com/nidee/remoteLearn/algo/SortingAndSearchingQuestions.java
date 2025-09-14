package com.nidee.remoteLearn.algo;

import org.testng.Assert;
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


}
