package dp.mit;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author dungptm2
 */
public class LongestIncreasingSubsequence {

    public int lengthOfLIS(String A) {
        int n = A.length();
        int[] L = new int[n+1];
        L[n] = 0;
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                if (A.charAt(j) > A.charAt(i)) {
                    L[i] = Math.max(L[i], 1+ L[j]);
                }
            }
        }
        return Arrays.stream(L).max().getAsInt();
    }

    public static void main(String[] args) {
        String A = "CARBOHYDRATE";
        int lis = new LongestIncreasingSubsequence().lengthOfLIS(A);
        System.out.println("lis = " + lis);
        System.exit(0);
    }

}
