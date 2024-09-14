package dp.mit;

/**
 * @author dungptm2
 */
public class CareerFairOptimization {

    public int maxCoolness(Booth[] bs, int k, int h, int b) {
        int[][] X = new int[k + 1][b + 1];

        for (int j = 0; j <= b; j++) {
            X[0][j] = 0;
        }

        for (int i = 1; i <= k; i++) {
            for (int j = 1; j <= b; j++) {
                if (i - h >= 0) {
                    X[i][j] = Math.max(X[i][j], X[i - h][b]);
                }
                for (Booth booth : bs) {
                    if (i - 1 - booth.t >= 0 && j - booth.w >= 0) {
                        X[i][j] = Math.max(X[i][j], booth.c + X[i - 1 - booth.t][j - booth.w]);
                    }
                }
            }
        }
        return X[k][b];
    }

    public static void main(String[] args) {
        CareerFairOptimization cfp = new CareerFairOptimization();
        Booth[] b = {new Booth(1, 2, 1),
                new Booth(2, 3, 2),
                new Booth(1, 4, 2),
                new Booth(-4, 5, 1)};
        System.out.println(cfp.maxCoolness(b, 10, 3, 3));

        System.exit(0);
    }

    static class Booth {
        int c;
        int w;
        int t;

        public Booth(int c, int w, int t) {
            this.c = c;
            this.w = w;
            this.t = t;
        }
    }
}
