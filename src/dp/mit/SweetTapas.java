package dp.mit;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author dungptm2
 */
public class SweetTapas {

    public int maxVolume(Plate[] ip, int k, int s) {
        Plate[] p = Stream.of(ip, ip).flatMap(Stream::of).toArray(Plate[]::new);
        int n = p.length;
        int[][] A = new int[k + 1][s + 1];
        A[0][0] = 0;

        for (int i = 1; i <= k; i++) {
            for (int j = 0; j <= s; j++) {
                for (int l = 0; l < n; l++) {
                    if (i - p[l].c >= 0 && j - p[l].s >= 0) {
                        A[i][j] = Math.max(A[i][j], p[l].v + A[i - p[l].c][j - p[l].s]);
                    }
                }
            }
        }
        return A[k][s];
    }

    public static void main(String[] args) {
        Plate[] p = {
                new Plate(1, 2, 1),
                new Plate(2, 2, 0),
                new Plate(1, 1, 1),
                new Plate(1, 1, 0)};

        System.out.println(new SweetTapas().maxVolume(p, 4, 2));

        System.exit(0);
    }

    public static class Plate {
        int v;
        int c;
        int s;

        public Plate(int v, int c, int s) {
            this.v = v;
            this.c = c;
            this.s = s;
        }
    }
}
