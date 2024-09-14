package dp.mit;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author dungptm2
 */
public class ShortingStocks {
    public void maxShortingValue(int C, int n, int k, int[][] P) {
        int[] maxA = new int[C];

        for (int c = 0; c < C; c++) {
            int[][] A = new int[n + 1][k + 1];
            A[n - 1][k - 1] = 1;
            for (int i = n - 1; i >= 0; i--) {
                for (int j = k - 1; j >= 0; j--) {
                    for (int ii = i * k + j + 1; ii < (i+1)*k + k ; ii++) {
                        if (ii < P[c].length && P[c][i * k + j] > P[c][ii]) {
                            A[i][j] = Math.max(A[i][j], 1 + A[ii / k][ii % k]);
                        } else {
                            A[i][j] = Math.max(A[i][j], 1);
                        }
                    }
                }
            }
            int maxC = Arrays.stream(A)
                    .flatMapToInt(Arrays::stream)
                    .max().getAsInt();
            maxA[c] = maxC;
        }

        System.out.println("max len: " + Arrays.stream(maxA).max().getAsInt());
    }

    public static void main(String[] args) {
        int[][] P = {{1, 8, 9, 2, 4, 3},
                {9, 12, 11, 10, 3, 5}};
        ShortingStocks s = new ShortingStocks();
        s.maxShortingValue(2, 3, 2, P);
        System.exit(0);
    }
}
