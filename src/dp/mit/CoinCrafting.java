package dp.mit;

/**
 * @author dungptm2
 */
public class CoinCrafting {

    public int maxRevenue(int[] p, int [] m) {
        int n = p.length;
        int[][] X = new int[n+1][n+1];
        X[n][n] = 0;
        for (int i = 0; i <= n; i++) {
            X[i][0] = 0;
        }

        for (int i = n-1; i >= 0; i--) {
            for (int j = n; j >= 0; j--) {
                if (j-m[i] >= 0) {
                    X[i][j] = Math.max(p[i] + X[i+1][j-m[i]], X[i+1][j]);
                } else {
                    X[i][j] = X[i+1][j];
                }
            }

        }

        return X[0][n];
    }

    public static void main(String[] args) {
        CoinCrafting cc = new CoinCrafting();
        int[] p = {4,5,9,1};
        int[] m = {1,2,2,3};
        System.out.println(cc.maxRevenue(p, m));
        System.exit(0);
    }
}
