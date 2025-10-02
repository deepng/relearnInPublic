package com.nidee.remoteLearn.algoProblems;

import java.util.List;

public class Fibonnacci {

    public int fibRecursion(List<Integer> ans, int n) {
        if(ans.get(n) != null) {
            return ans.get(n);
        }
        if (n <= 1) {
            ans.set(n, n);
            return n;
        }
        ans.set(n-1, fibRecursion(ans,n-1));
        ans.set(n-2, fibRecursion(ans,n-2));
        ans.set(n, ans.get(n-1) + ans.get(n-2));
        return ans.get(n);
    }

    public int fibIterative(int n) {
        if (n <= 1) {
            return n;
        }
        int a = 0;
        int b = 1;
        int c = 0;
        for(int i=2; i<=n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return c;
    }

}
