package com.nidee.remoteLearn.algoProblems;

import com.nidee.remoteLearn.sort.Sort;

/**
 * Given an array of integers, move all zeros to the end
 * while maintaining the order of non-zero elements.
 *
 * Insertion sort?
 */
public class MoveAllZeros implements Sort {

    // Implementing sort just to get swap method

    public int[] moveAllZeros(int[] array) {
        if(array == null || array.length == 0) return array;
        int lastNonZeroFoundAt = 0;
        for(int i=0; i<array.length; i++) {
            if(array[i] != 0) {
                swap(array, lastNonZeroFoundAt, i);
                lastNonZeroFoundAt++;
            }
        }
        return array;
    }

    public int[] moveAllZerosUsing2Pointers(int[] array) {
        if(array == null || array.length == 0) return array;
        int left = 0, right = 0;
        while(right < array.length) {
            if(array[left] == 0 && array[right] != 0) {
                swap(array, left, right);
                left++;
            }
            while(array[right] == 0)
                right++;
            while(array[left] != 0)
                left++;
        }
        return array;
    }

    @Override
    public int[] resolveInt(int[] collection) {
        return new int[0];
    }
}
