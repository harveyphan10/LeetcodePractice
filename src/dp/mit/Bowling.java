package dp.mit;

import java.util.Arrays;

/**
 * @author dungptm2
 */
public class Bowling {

    public int maxScore(int[] v) {
        int[] B = new int[v.length+1];
        B[v.length] = 0;

        for (int i = v.length - 1; i >= 0; i--) {
            if (i+1 <= v.length-1) {
                B[i] = Math.max(Math.max(B[i+1], v[i] + B[i+1]), v[i]*v[i+1] + B[i+2]);
            } else {
                B[i] = Math.max(B[i+1], v[i] + B[i+1]);
            }
        }
        return B[0];
    }

    public static void main(String[] args) {
        int[] c = {1, 1, 9, 9, 2, -5, -5};
        int ret = new Bowling().maxScore(c);
        System.out.println("ret = " + ret);
        System.exit(0);
    }

}
