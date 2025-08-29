package com.nidee.remoteLearn.algo;

import com.nidee.remoteLearn.sort.QuickSort;
import com.nidee.remoteLearn.sort.Sort;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
            default:
                sort = new QuickSort();
        }
        Date date = new Date();
        long startTime = date.getTime();
        Collection<?> sortedArray = sort.resolve(Collections.singleton(arrayToSort));
        long endTime = date.getTime();
        assertEquals(Collections.singleton(finalSortedArray), Collections.singleton(sortedArray));
        logger.log(Level.INFO, "Time taken to sort the array: " + (endTime - startTime) + "ms");
    }
}
