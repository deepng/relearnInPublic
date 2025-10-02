package com.nidee.remoteLearn.sort;

import java.util.Arrays;

public class MergeSort implements Sort {
    @Override
    public int[] resolveInt(int[] collection) {
        return mergeSort(collection, 0, collection.length - 1);
    }

    private int[] mergeSort(int[] collection, int start, int end) {
        if(start == end)
            return Arrays.copyOfRange(collection, start, end + 1);
        int mid = (end - start) / 2 + start;
        int[] a = mergeSort(collection, start, mid);
        int[] b = mergeSort(collection, mid+1, end);
        int i = 0, j = 0;
        int[] merged = new int[end - start + 1];
        int k = 0;
        while(i <= (mid - start) && j <= (end - mid - 1)) {
            if(a[i] <= b[j]) {
                merged[k++] = a[i++];
                i++;
            } else {
                merged[k++] = b[j++];
                j++;
            }
        }
        // Just in case 1 array is shorter than the other
        while(i <= (mid - start)) {
            merged[k++] = a[i++];
        }
        while(j <= (end - mid -1)) {
            merged[k++] = b[j++];
        }
        return merged;
    }
}
