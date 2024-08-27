package dp.mit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dungptm2
 */
public class DifﬁngData {

    /**
     * swap: 0, delete and insert:1,2
     */

    public int minDiff(char[] A, char[] B) {
        int n = A.length;
        int[][] L = new int[n+1][n+1];
        for (int i = n; i >= 0; i--) {
            L[i][n] = n-i;
        }
        for (int j = n; j >= 0; j--) {
            L[n][j] = n-j;
        }

        for (int i = n-1; i >= 0; i--) {
            for (int j = n-1; j >= 0; j--) {
                if (A[i] == B[j]) {
                    L[i][j] = L[i+1][j+1];
                } else {
                    int swap = Integer.MAX_VALUE;
                    if (j+1 < n && i+1 < n && A[i] == B[j+1] && A[i+1] == B[j]) {
                        swap = L[i+2][j+2];
                    }
                    int ins = 1 + L[i+1][j];
                    int del = 1 + L[i][j+1];
                    L[i][j] = Math.min(Math.min(ins, del), swap);
                }
            }
        }
        return L[0][0];
    }

    public static void main(String[] args) {
        char[] A = {'9', 'a', 'b', 'c'};
        char[] B = {'a', '9', 'b', 'd'};
        System.out.println(new DifﬁngData().minDiff(A, B));
        System.exit(0);
    }
}
