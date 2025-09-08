package com.nidee.remoteLearn.sort;

public interface Sort {

    public int[] resolveInt(int[] collection);

    public default void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}
