package dp.mit;

/**
 * @author dungptm2
 */
public class ArithmetricParenthesization {

    public int maxResult(int[] A, char[] p) {
        int n = A.length;
        int[][] min = new int[n+1][n+1];
        int[][] max = new int[n+1][n+1];
        for (int i =0; i<= n; i++) {
            for (int j =0; j <= n; j++) {
                min[i][j] = Integer.MAX_VALUE;
                max[i][j] = Integer.MIN_VALUE;
            }
        }

        for (int i = 0; i < n; i++) {
            min[i][i+1] = A[i];
            max[i][i+1] = A[i];
        }

        for (int i = n-1; i >= 0; i--) {
            for (int j = i + 2; j <= n; j++) {
                for (int k = i+1; k < j; k++) {
                    if (p[k] == '*') {
                        int opt1 = min[i][k] * min[k][j];
                        int opt2 = min[i][k] * max[k][j];
                        int opt3 = max[i][k] * min[k][j];
                        int opt4 = max[i][k] * max[k][j];
                        min[i][j] = Math.min(min[i][j], Math.min(opt1, Math.min(opt2, Math.min(opt3, opt4))));
                        max[i][j] = Math.max(max[i][j], Math.max(opt1, Math.max(opt2, Math.max(opt3, opt4))));
                    } else if (p[k] == '+') {
                        int opt1 = min[i][k] + min[k][j];
                        int opt2 = min[i][k] + max[k][j];
                        int opt3 = max[i][k] + min[k][j];
                        int opt4 = max[i][k] + max[k][j];
                        min[i][j] = Math.min(min[i][j], Math.min(opt1, Math.min(opt2, Math.min(opt3, opt4))));
                        max[i][j] = Math.max(max[i][j], Math.max(opt1, Math.max(opt2, Math.max(opt3, opt4))));
                    }
                }
            }
        }
        return max[0][n];
    }

    public static void main(String[] args) {
        ArithmetricParenthesization a = new ArithmetricParenthesization();
        int[] A = {7, -4, 3, -5, 2};
        char[] p = {'+', '+', '*', '+', '*'};
        System.out.println(a.maxResult(A, p));
        System.exit(0);
    }
}
