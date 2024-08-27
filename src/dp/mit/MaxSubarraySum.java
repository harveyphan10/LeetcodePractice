package dp.mit;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author dungptm2
 */
public class MaxSubarraySum {

    public int maxContiguousSum(int[] A) {
        int n = A.length;
        int[] L = new int[n+1];
        L[n-1] = A[n-1];

        for (int i = n-2; i >= 0; i--) {
            L[i] = Math.max(A[i], A[i] + L[i+1]);
        }
        return Arrays.stream(L).max().getAsInt();
    }

    public static void main(String[] args) {
        System.out.println(new MaxSubarraySum().maxContiguousSum(new int[]{-9, 1, -5, 4, 3, -6, 7, 8,-2}));
        System.exit(0);
    }
}
