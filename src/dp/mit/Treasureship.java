package dp.mit;

/**
 * @author dungptm2
 */
public class Treasureship {

    public int maxScore(int[][] g) {
        int n = g[0].length;
        int[][] A = new int[2][n + 1];

        A[1][0] = 0;
        A[0][0] = Math.max(A[0][0], g[0][0] + g[1][0]);

        for (int j = 1; j < n; j++) {
            if (j >= 2) {
                A[1][j] = Math.max(A[1][j], Math.max(A[1][j - 1], g[1][j] + g[1][j - 1] + A[1][j - 2]));
                int v1 = g[0][j] + g[0][j - 1] + Math.max(A[0][j - 2], A[1][j]);
                int v2 = g[0][j] + g[1][j] + A[0][j - 1];
                A[0][j] = Math.max(A[0][j], Math.max(Math.max(v1, v2), Math.max(A[0][j - 1], A[1][j])));
            } else {
                A[1][j] = Math.max(A[1][j], Math.max(A[1][j - 1], g[1][j] + g[1][j - 1]));
                int v1 = g[0][j] + g[0][j - 1];
                int v2 = g[0][j] + g[1][j] + A[0][j - 1];
                A[0][j] = Math.max(A[0][j], Math.max(Math.max(v1, v2), Math.max(A[0][j - 1], A[1][j])));
            }
        }

        return A[0][n - 1];
    }

    public static void main(String[] args) {
        Treasureship treasureship = new Treasureship();
        int[][] g = {
                {1, -3, 7, 1, 5},
                {-2, 5, 8, 1, 2}};

        System.out.println(treasureship.maxScore(g));
        System.exit(0);
    }
}
