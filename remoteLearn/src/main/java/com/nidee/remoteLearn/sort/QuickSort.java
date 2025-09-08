package com.nidee.remoteLearn.sort;

public class QuickSort implements Sort {

    @Override
    public int[] resolveInt(int[] array) {
        quickSort(array, 0, array.length - 1);
        return array;

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
        int pivot = array[(h-l)/2 + 1];
        int i = l;
        int j = h;
        while (i < j) {
            while (array[i] < pivot) {
                i++;
            }
            while (array[j] > pivot) {
                j--;
            }
            if(i < j && array[i] != array[j]) {
                swap(array, i, j);
            }
        }
        pivot = j;
        return pivot;
    }


}
