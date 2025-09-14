package com.nidee.remoteLearn.sort;

import java.util.ArrayList;

/**
 * Moves from left to right. Selecting the smallest element and placing it in the
 * leftmost index. Since we select and replace we call this selection sort.
 * This is following decrease and conquer strategy and takes O(n^2) time
 */
public class SelectionSort implements Sort {
    @Override
    public int[] resolveInt(int[] collection) {
        return selectionSort(collection);
    }

    private int[] selectionSort(int[] arr) {
        for(int i=0; i<arr.length - 1; i++) {
            int min = i;
            for(int j=i+1; j<=arr.length - 1; j++) { // Get the smallest number to put in the index i;
                if(arr[j] < arr[min])
                    min = j;
            }
            swap(arr, i, min);
        }
        return arr;
    }

    static void swapSort(ArrayList<Integer> arr, int i, int j) {
        int temp = arr.get(i);
        arr.add(i, arr.get(j));
        arr.add(j, temp);
    }



}
