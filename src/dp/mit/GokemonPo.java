package dp.mit;

import java.util.Arrays;

/**
 * @author dungptm2
 */
public class GokemonPo {

    public int minCost(int[] c, int[][] s) {
        int n = s.length - 1;
        int[] A = new int[n + 2];
        A[0] = 0;
        A[1] = 0;

        for (int i = 2; i <= n; i++) {
            int min = c[i] + A[i - 1];
            for (int j = i - 1; j >= 1; j--) {
                int loopMin = s[j][i] + A[j];
                for (int k = i - 1; k > j; k--) {
                    loopMin += c[k];
                }
                min = Math.min(min, loopMin);
            }
            A[i] = min;
        }

        return A[n];
    }

    public static void main(String[] args) {
        GokemonPo gp = new GokemonPo();
        int[] c = {0, 2, 5, 2, 3};
        int[][] s = {
                {0, 0, 0, 0, 0},
                {0, 0, 1, 2, 1},
                {0, 0, 0, 7, 2},
                {0, 0, 0, 0, 6},
                {0, 0, 0, 0, 0}
        };
        System.out.println(gp.minCost(c, s));
        System.exit(0);
    }
}
