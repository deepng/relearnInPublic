package com.nidee.remoteLearn.sort;

import java.util.ArrayList;

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


    static ArrayList<Integer> selection_sort(ArrayList<Integer> arr) {
        // Write your code here.
        int pivot = (arr.size() - 1)/2;
        sort(arr, 0, pivot);
        sort(arr, pivot+1, arr.size());
        return arr;
    }

    static void sort(ArrayList<Integer> arr, int i ,int j) {
        int pivot = (j-i)/2;
        while(i < j) {
            while(arr.get(i) < arr.get(pivot))
                i++;
            while(arr.get(j) > arr.get(pivot))
                j--;

            if(i<j)
                swapSort(arr, i, j);

        }
    }

    static void swapSort(ArrayList<Integer> arr, int i, int j) {
        int temp = arr.get(i);
        arr.add(i, arr.get(j));
        arr.add(j, temp);
    }



}
