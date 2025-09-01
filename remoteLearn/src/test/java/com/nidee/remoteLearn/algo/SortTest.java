package com.nidee.remoteLearn.algo;

import com.nidee.remoteLearn.sort.QuickSort;
import com.nidee.remoteLearn.sort.Sort;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SortTest {

    int[] arrayToSort = {5, 3, 8, 6, 2};
    int[] finalSortedArray = {2, 3, 5, 6, 8};
    Logger logger = Logger.getLogger(SortTest.class.getName());

    public static Stream<Arguments> envParams() {
        return Stream.of(
                Arguments.of("quick"),
                Arguments.of("merge"),
                Arguments.of("bubble")
        );
    }

    @ParameterizedTest
    @MethodSource("envParams")
    void testSortIntArray(String algo) {
        Sort sort = null;
        switch (algo) {
            case "bubble":
//                 sort = new BubbleSort();
                break;
            case "merge":
                // sort = new MergeSort();
                break;
            case "quick":
            default:
                sort = new QuickSort();
        }
        if (sort == null) return;
        Date date = new Date();
        long startTime = date.getTime();
        int[] sortedArray = sort.resolveInt(arrayToSort);
        long endTime = date.getTime();
        assertTrue(Arrays.equals(finalSortedArray,sortedArray));
        logger.log(Level.INFO, "Time taken to sort the array: " + (endTime - startTime) + "ms");
    }
}
