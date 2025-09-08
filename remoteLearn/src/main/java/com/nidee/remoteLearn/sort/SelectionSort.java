package com.nidee.remoteLearn.sort;

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
}
