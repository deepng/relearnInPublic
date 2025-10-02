package com.nidee.remoteLearn.sort;

import java.util.List;

/**
 * Decrease and conquer algorithm
 * This can be recursive or iterative
 * Recursive approach is used here
 * Analogy: Think of a manager having a bunch of issues to resolve. The manager picks the last task and hands over the rest to a subordinate.
 * The subordinate picks the last task from his list and hands over the rest to another subordinate and so on.
 * When the subordinate has only one task, he resolves it and hands it over to the manager.
 * The manager then resolves his task and places it in the correct position in the sorted list received from the subordinate.
 * The manager then hands over the sorted list to his superior.
 * This continues until the top most manager resolves his task and places it in the correct position in the sorted list received from his subordinate.
 * The top most manager then has the complete sorted list.
 * Time complexity:
 * Best case: O(n) - when the array is already sorted
 * Average case: O(n^2)
 * Worst case: O(n^2) - when the array is sorted in reverse order
 * Space complexity: O(1)
 * Stable sort
 * Insertion sort is a simple sorting algorithm that builds the final sorted array one item at a time.
 * It is much less efficient on large lists than more advanced algorithms such as quicksort, heapsort, or merge sort.
 * However, insertion sort provides several advantages:
 * 1. Simple implementation
 * 2. Efficient for small data sets
 * 3. More efficient in practice than most other simple quadratic (i.e., O(n^2)) algorithms such as selection sort or bubble sort
 * 4. Adaptive, i.e., efficient for data sets that are already substantially sorted: the time complexity is O(n + d), where d is the number of inversions
 * 5. Stable; i.e., does not change the relative order of elements with equal keys
 * 6. In-place; i.e., only requires a constant amount O(1) of additional memory space
 * 7. Online; i.e., can sort a list as it receives it
 */
public class InsertionSort implements Sort {
    @Override
    public int[] resolveInt(int[] collection) {
        return insertionSort(collection, 0, collection.length - 1);
    }

    private int[] insertionSort(int[] collection, int start, int end) {
        if (start > end ) return collection;
        insertionSort(collection, start, end-1);
        int key = collection[end];
        int j = end - 1;
        while (j >= 0 && collection[j] > key) {
            collection[j + 1] = collection[j];
            j = j - 1;
        }
        collection[j + 1] = key;
        return collection;
    }
}
