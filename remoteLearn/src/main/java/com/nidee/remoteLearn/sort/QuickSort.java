package com.nidee.remoteLearn.sort;

import java.util.Collection;
import java.util.List;

public class QuickSort implements Sort {

    @Override
    public Collection<?> resolve(Collection<?> collection) {
        for(Object obj : collection) {
            if(obj instanceof int[]) {
                int[] array = (int[]) obj;
                quickSort(array, 0, array.length - 1);
                return List.of(array);
            }
        }
        return List.of(collection);

    }

    private void quickSort(int[] array, int l, int h) {
        if(l >= 0 && l < h)  {
            int pivot = partition(array, l, h);
            quickSort(array, l, pivot - 1);
            quickSort(array, pivot + 1, h);
        }
    }

    private int partition(int[] array, int l, int h) {
        if(l >= h || l < 0) return -1;
        int pivot = array[h];
        int i = l-1;
        int j = h+1;
        while (i < j) {
            i++;
            j--;
            if(array[i] < pivot || array[j] > pivot) {
                continue;
            }
            if(i < j)
                swap(array, i, j);
        }
        pivot = i;
        return pivot;
    }

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
