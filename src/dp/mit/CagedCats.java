package dp.mit;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.IntStream;

/**
 * @author dungptm2
 */
public class CagedCats {

    public int minDiscomfort(Tiger[] tiger, Cage[] cage) {
        int n = tiger.length;
        int n2 = (int) Math.pow(n, 2);
        int[][] A = new int[n+1][n2+1];

        Arrays.sort(tiger, (o1, o2) -> {
            return o2.a - o1.a;
        });
        Arrays.sort(cage, (o1, o2) -> {
            return o1.d - o2.d;
        });

        for (int i = n-1; i >= 0; i--) {
            for (int j = 0 ; j < (n2-(n-i)+1); j++) {
                int discomfort = tiger[i].s > cage[j].c ? tiger[i].s - cage[j].c : 0;
                A[i][j] = Math.min(A[i][j], discomfort);

                int min = i == n-1 ? 0 : Arrays.stream(A[i+1], j+1, n2-(n-(i+1))+1).min().getAsInt();
                A[i][j] = tiger[i].s > cage[j].c ? tiger[i].s - cage[j].c + min : min;
            }
        }
        return Arrays.stream(A[0], 0, n2-n).min().getAsInt();
    }

    static class Tiger {
        int a;
        int s;

        public Tiger(int a, int s) {
            this.a = a;
            this.s = s;
        }
    }

    static class Cage {
        int c;
        int d;

        public Cage(int c, int d) {
            this.c = c;
            this.d = d;
        }
    }

    public static void main(String[] args) {
        Tiger[] tiger = {new Tiger(1, 13), new Tiger(3, 4), new Tiger(2, 15)};
        Cage[] cage = {new Cage(2, 4), new Cage(1, 2), new Cage(5, 1),
                new Cage(3, 5), new Cage(4, 6), new Cage(6, 7),
                new Cage(7, 18), new Cage(8, 3), new Cage(9, 9)};
        CagedCats cagedCats = new CagedCats();
        System.out.println(cagedCats.minDiscomfort(tiger, cage));
        System.exit(0);
    }
}
