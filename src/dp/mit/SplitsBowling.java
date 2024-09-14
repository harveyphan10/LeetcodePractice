package dp.mit;

/**
 * @author dungptm2
 */
public class SplitsBowling {

    public int maxScore(int[] v) {
        int n = v.length;
        int[][][] A = new int[n][n][2];
        int[] knock = new int[2];
        knock[0] = 0;
        knock[1] = 1;
        for(int i = n-1; i >= 0; i--) {
            for (int j = i+1; j < n; j++) {
                for (int c : knock) {
                    A[i][j][c] = Integer.MIN_VALUE;
                    if (c == 0) {
                        A[i][j][c] = Math.max(A[i][j][c], A[i+1][j][c]);

                    } else if (i+1 < j) {
                        for (int k = i+1; k <= j-1; k++) {
                            A[i][j][c] = Math.max(A[i][j][c], v[i] * v[k] + A[i+1][k-1][1] + A[k+1][j][c]);
                        }
                    } else if (i < j) {
                        A[i][j][c] = v[i] + A[i+1][j][c];
                    }
                }
            }
        }
        return A[0][n][0];
    }

    public static void main(String[] args) {
        SplitsBowling sol = new SplitsBowling();
        System.out.println(sol.maxScore(new int[]{1,-2,-3,4,-5,6,-7}));
        System.exit(0);
    }

}
