package dp.mit;

import java.util.*;

/**
 * @author dungptm2
 */
public class SunnyStudies {


    public void maxHappiness(int[] t) {
        int n = t.length;
        int[] H = new int[n + 1];
        H[n] = 0;
        List<List<Integer>> plays = new ArrayList<>(n+1);
        for (int i = 0; i <= n; i++) {
            plays.add(new ArrayList<>());
        }

        for (int i = n - 1; i >= 0; i--) {
            int v0 = H[i + 1];
            int v1 = Integer.MIN_VALUE + 8;
            int v2 = Integer.MIN_VALUE + 8;
            if (i + 3 <= n) {
                v1 = t[i] + H[i+2];
                v2 = t[i] + t[i+1] + H[i+3];
            } else if (i + 2 <= n) {
                v1 = t[i] + H[i+2];
            }
            H[i] = Math.max(Math.max(v0, v1), v2);

            if (H[i] == v2) {
                ArrayList<Integer> curPlays = new ArrayList<>(plays.get(i + 3));
                curPlays.add(i);
                curPlays.add(i+1);
                plays.set(i, curPlays);
            } else if (H[i] == v1) {
                ArrayList<Integer> curPlays = new ArrayList<>(plays.get(i + 2));
                curPlays.add(i);
                plays.set(i, curPlays);
            } else {
                plays.set(i, new ArrayList<>(plays.get(i + 1)));
            }
        }
        System.out.printf("max happiness: %d\n", H[0]);
        System.out.println("play days: " + plays.getFirst());
    }

    public static void main(String[] args) {
        int[] t = {1, 1, 9, 9, 2, -5, -5};
        new SunnyStudies().maxHappiness(t);
        System.exit(0);
    }
}
