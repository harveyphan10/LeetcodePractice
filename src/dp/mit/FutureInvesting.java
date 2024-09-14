package dp.mit;

/**
 * @author dungptm2
 */
public class FutureInvesting {
    public int maxLottery(int[] c, int adj) {
        int n = c.length;
        int[][] A = new int[3][n + 1];

        for (int i = 0; i <= n; i++) {
            A[0][i] = 0;
        }

        for (int i = 1; i <= 2; i++) {
            for (int j = n - 1; j >= 0; j--) {
                if (j + adj - (2 - i) <= n) {
                    A[i][j] = Math.max(A[i][j + 1], c[j] + A[i - 1][j + 1] + A[i][j + adj - (2 - i)]);
                } else {
                    A[i][j] = Math.max(A[i][j + 1], c[j] + A[i - 1][j + 1]);
                }
            }
        }
        return A[2][0];
    }

    public static void main(String[] args) {
        System.out.println(new FutureInvesting().maxLottery(new int[]{18, 10, 8, 19, 1, 15, 7, 2}, 7));
        System.exit(0);
    }
}
