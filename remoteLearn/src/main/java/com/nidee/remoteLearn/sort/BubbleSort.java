package com.nidee.remoteLearn.sort;

/**
 * We move from right to left comparing the element with the previous element.
 * And swapping if the previous element is grater than the current.
 * This way we bubble up the smallest element to the left most index.
 * This takes O(n^2) time. And is less efficient with multiple swaps.
 */
public class BubbleSort implements Sort {

    @Override
    public int[] resolveInt(int[] collection) {
        return bubbleSort(collection);
    }

    private int[] bubbleSort(int[] arr) {
        for(int i=arr.length-1; i>0; i--) {
            boolean swaps = false;
            for(int j=arr.length-1; j>0; j--) {
                if(arr[j] < arr[j-1]) {
                    swaps = true;
                    swap(arr, j, j - 1);
                }
            }
            if(!swaps) // if there are no swaps we break since the arr is sorted
                break;
        }
        return arr;
    }
}
