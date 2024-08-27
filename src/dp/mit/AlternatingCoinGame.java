package dp.mit;

/**
 * @author dungptm2
 */
public class AlternatingCoinGame {

    public int maxMyCoins(int[] v) {
        int n = v.length;
        int[][] X = new int[n+1][n+1];
        int[][] Y = new int[n+1][n+1];
        for (int i = 0; i < n; i++) {
            X[i][i] = v[i];
        }
        for (int i = 0; i < n; i++) {
            Y[i][i] = 0;
        }
        for (int i = n-1; i >= 0; i--) {
            for (int j = i+1; j < n; j++) {
                X[i][j] = Math.max(v[i] + Y[i+1][j], v[j] + Y[i][j-1]);
                Y[i][j] = Math.min(X[i+1][j], X[i][j-1]);
            }
        }
        return X[0][n-1];
    }

    public static void main(String[] args) {
        AlternatingCoinGame game = new AlternatingCoinGame();
        System.out.println(game.maxMyCoins(new int[]{5, 10, 100, 25}));
        System.exit(0);
    }
}
