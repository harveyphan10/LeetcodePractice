package dp.mit;

/**
 * @author dungptm2
 */
public class PrincessPlum {

    static class Path implements Comparable<Path> {
        @Override
        public int compareTo(Path o) {
            if (o.m > this.m) {
                return -1;
            } else if (o.m == this.m && o.p > this.p) {
                return -1;
            } else if (o.m == this.m && o.p == this.p) {
                return 0;
            }
            return 1;
        }

        int p;
        int m;

        public Path(int p, int m) {
            this.p = p;
            this.m = m;
        }

    }

    public Path maxNumberOptimalPath(char[][] F) {
        int n = F.length;
        Path[][] L = new Path[n + 1][n + 1];

        for (int j = 0; j < n; j++) {
            L[n][j] = new Path(0, 0);
        }
        for (int i = 0; i < n; i++) {
            L[i][n] = new Path(0, 0);
        }

        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (F[i][j] != 't') {
                    if (i < n - 1 && j < n-1) {
                        int compareTo = L[i][j + 1].compareTo(L[i + 1][j]);
                        if (compareTo < 0) {
                            L[i][j] = new Path(L[i + 1][j].p, F[i][j] == 'm' ? 1 + L[i + 1][j].m : L[i + 1][j].m);
                        } else if (compareTo == 0) {
                            L[i][j] = new Path(L[i + 1][j].p + 1, F[i][j] == 'm' ? 1 + L[i + 1][j].m : L[i + 1][j].m);
                        } else {
                            L[i][j] = new Path(L[i][j + 1].p, F[i][j] == 'm' ? 1 + L[i][j + 1].m : L[i][j + 1].m);
                        }
                    } else if (i == n-1){
                        L[i][j] = new Path(1, F[i][j] == 'm' ? 1 + L[i][j + 1].m : L[i][j + 1].m);
                    } else if (j == n-1){
                        L[i][j] = new Path(1, F[i][j] == 'm' ? 1 + L[i+1][j].m : L[i+1][j].m);
                    }
                } else {
                    L[i][j] = new Path(1, 0);
                }
            }
        }
        return L[0][0];
    }

    public static void main(String[] args) {
        PrincessPlum princessPlum = new PrincessPlum();
        char[][] F = {
                {'x', 'x', 't', 'x'},
                {'x', 't', 'x', 'x'},
                {'x', 't', 'm', 'm'},
                {'m', 'm', 'x', 'x'}};
        Path path = princessPlum.maxNumberOptimalPath(F);
        System.out.println(path.p + "|" + path.m);
        System.exit(0);
    }
}
