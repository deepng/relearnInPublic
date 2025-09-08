package com.nidee.remoteLearn.algoProblems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Example:
 * `Selection sort`:
 * Find the smallest number and put it in the latest index. it will run through the array atleast n^2 times.
 * it will select the lowest number and assign it to the current index
 */

public class SumProblems {

    /**
     * This is a brute force solution with O(n^2) time complexity
     * It will find all the pairs that give the target sum
     * @param target
     * @param arr
     * @return
     */
    public Set<List<Integer>> getPairsWhoGiveSumsWithOutSorting(int target, int[] arr) {
        Set<List<Integer>> out = new HashSet<>();
        for(int i=0; i<arr.length-1; i++) {
            for(int j=i+1; j<=arr.length-1; j++) {
                if(arr[i] + arr[j] == target) {
                    List<Integer> pair = new ArrayList<>();
                    pair.add(arr[i]);
                    pair.add(arr[j]);
                    out.add(pair);
                    break;
                }
            }
        }
        return out;
    }

    /**
     * This is an optimized solution with O(n log n) + O(n) time complexity
     * It will find all the pairs that give the target sum
     * First it will sort the array and then use two pointers to find the pairs
     * @param target
     * @param arr
     * @return
     */
    public Set<List<Integer>> getPairsWhoGiveSumsWithSorting(int target, int[] arr) {
        Set<List<Integer>> out = new HashSet<>();
        // Sort the array first
        java.util.Arrays.sort(arr); // O(n log n)
        // Use two pointers to find the pairs
        int left = 0;
        int right = arr.length - 1;
        while(left < right) {
            int sum = arr[left] + arr[right];
            if(sum == target) {
                List<Integer> pair = new ArrayList<>();
                pair.add(arr[left]);
                pair.add(arr[right]);
                out.add(pair);
                left++;
                right--;
            } else if(sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return out;
    }

    /**
     * 3 sum problem
     * Given an array of integers, find all unique triplets in the array which sum to a given target.
     * Note: The solution set must not contain duplicate triplets.
     */
    public Set<List<Integer>> getTripletsWhoGiveSumsWithSorting(int target, int[] arr) {
        Set<List<Integer>> out = new HashSet<>();
        // Sort the array first
        java.util.Arrays.sort(arr); // O(n log n)
        // Use two pointers to find the pairs
        for(int i=0; i<arr.length-2; i++) { // i can go till length - 2
            if(i > 0 && arr[i] == arr[i-1]) continue; // Skip duplicates
            int left = i + 1;
            int right = arr.length - 1;
            while(left < right) {
                if(left > i + 1 && arr[left] == arr[left - 1]) {
                    left++;
                    continue; // Skip duplicates
                }
                if(right < arr.length - 1 && arr[right] == arr[right + 1]) {
                    right--;
                    continue; // Skip duplicates
                }
                int sum = arr[i] + arr[left] + arr[right];
                if(sum == target) {
                    List<Integer> triplet = new ArrayList<>();
                    triplet.add(arr[i]);
                    triplet.add(arr[left]);
                    triplet.add(arr[right]);
                    out.add(triplet);
                    left++;
                    right--;
                } else if(sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return out;
    }
}


