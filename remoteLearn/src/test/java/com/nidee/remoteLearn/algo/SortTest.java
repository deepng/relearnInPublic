package com.nidee.remoteLearn.algo;

import com.nidee.remoteLearn.algoProblems.SumProblems;
import com.nidee.remoteLearn.sort.*;
import org.springframework.util.Assert;
import org.testng.annotations.Test;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SortTest {

    int[] arrayToSort = {5, 6, 4, 2, 1, 3};
    int[] finalSortedArray = {1, 2, 3, 4, 5, 6};
    Logger logger = Logger.getLogger(SortTest.class.getName());

//`    public static Stream<Arguments> envParams() {
//        return Stream.of(
//                Arguments.of("quick"),
//                Arguments.of("merge"),
//                Arguments.of("bubble")
//        );
//    }
//
//    @ParameterizedTest
//    @MethodSource("envParams")
//    void testSortIntArray(String algo) {
//        Sort sort = null;
//        switch (algo) {
//            case "bubble":
////                 sort = new BubbleSort();
//                break;
//            case "merge":
//                // sort = new MergeSort();
//                break;
//            case "quick":
//                sort = new QuickSort();
//                break;
//            case "gpt-quick":
//            default:
//                sort = new GPTQuickSort();
//        }
//        if (sort == null) return;
//        Date date = new Date();
//        long startTime = date.getTime();
//        int[] sortedArray = sort.resolveInt(arrayToSort);
//        long endTime = date.getTime();
//        assertTrue(Arrays.equals(finalSortedArray,sortedArray));
//        logger.log(Level.INFO, "Time taken to sort the array: " + (endTime - startTime) + "ms");
//    }`

    @Test
    public void testSortLargeIntArray(){
        Date date = new Date();
        long startTime = date.getTime();
        Sort sort = new GPTQuickSort();
        int[] sortedArray = sort.resolveInt(arrayToSort);
        long endTime = date.getTime();
        assert(Arrays.equals(finalSortedArray,sortedArray));
        logger.log(Level.INFO, "Time taken to sort the array: " + (endTime - startTime) + "ms");
    }

    @Test
    public void testSelectionSort() {
        Date date = new Date();
        long startTime = date.getTime();
        Sort sort = new SelectionSort();
        int[] sortedArray = sort.resolveInt(arrayToSort);
        long endTime = date.getTime();
        assert(Arrays.equals(finalSortedArray,sortedArray));
        logger.log(Level.INFO, "Time taken to sort the array: " + (endTime - startTime) + "ms");
    }

    @Test
    public void testBubbleSort() {
        Date date = new Date();
        long startTime = date.getTime();
        Sort sort = new BubbleSort();
        int[] sortedArray = sort.resolveInt(arrayToSort);
        long endTime = date.getTime();
        Assert.isTrue(Arrays.equals(finalSortedArray,sortedArray),
                String.format("We expected %s but got %s", Arrays.toString(finalSortedArray),
                        Arrays.toString(sortedArray)));
        logger.log(Level.INFO, "Time taken to sort the array: " + (endTime - startTime) + "ms");
    }

    int target = 8;
    int[] arr = {7, 5, 4, 3, 1, 4, 8, 0, 2};
    int ans = 4;

    @Test
    public void test2Sums() {
        Date date = new Date();
        long startTime = date.getTime();
        SumProblems sumProblems = new SumProblems();
        Set<List<Integer>> pairsWhoGiveSums = sumProblems.getPairsWhoGiveSumsWithOutSorting(target, arr);
        assert(pairsWhoGiveSums.size() == ans);
        long endTime = date.getTime();
        logger.log(Level.INFO, "Time taken to sort the array: " + (endTime - startTime) + "ms");
    }


    @Test
    public void test2SumsWithSorting() {
        Date date = new Date();
        long startTime = date.getTime();
        SumProblems sumProblems = new SumProblems();
        Set<List<Integer>> pairsWhoGiveSums = sumProblems.getPairsWhoGiveSumsWithSorting(target, arr);
        assert(pairsWhoGiveSums.size() == ans);
        long endTime = date.getTime();
        logger.log(Level.INFO, "Time taken to sort the array: " + (endTime - startTime) + "ms");
    }

    @Test
    public void testInsertionSort() {
        Date date = new Date();
        long startTime = date.getTime();
         Sort sort = new InsertionSort();
         int[] sortedArray = sort.resolveInt(arrayToSort);
        long endTime = date.getTime();
         assert(Arrays.equals(finalSortedArray,sortedArray));
        logger.log(Level.INFO, "Time taken to sort the array: " + (endTime - startTime) + "ms");
    }

    @Test
    public void testMergeSort() {
        Date date = new Date();
        long startTime = date.getTime();
        Sort sort = new MergeSort();
        int[] sortedArray = sort.resolveInt(arrayToSort);
        long endTime = date.getTime();
        assert(Arrays.equals(finalSortedArray,sortedArray));
        logger.log(Level.INFO, "Time taken to sort the array: " + (endTime - startTime) + "ms");
    }

    @Test
    public void testMergeSortArray() {
        Date date = new Date();
        long startTime = date.getTime();
        MergeSort sort = new MergeSort();
        ArrayList<Integer> sortedArray = sort.merge_sort(new ArrayList<>(Collections.nCopies(arrayToSort.length, 0)));
        long endTime = date.getTime();
        assert(Arrays.equals(finalSortedArray,sortedArray.stream().mapToInt(i->i).toArray()));
        logger.log(Level.INFO, "Time taken to sort the array: " + (endTime - startTime) + "ms");
    }
}
