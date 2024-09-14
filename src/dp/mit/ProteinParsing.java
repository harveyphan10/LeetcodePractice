package dp.mit;

import java.util.HashSet;
import java.util.Set;

/**
 * @author dungptm2
 */
public class ProteinParsing {

    public int maxDivision(String S, Set<String> P, int k) {
        int s = S.length();
        int p = P.size();
        int[] A = new int[s + 1];
        A[s] = 0;

        for (int i = s - 1; i >= 0; i--) {
            for (int j = 1; j <= k; j++) {
                if (i + j <= s) {
                    int accumulate = P.contains(S.substring(i, i + j)) ? 1 : 0;
                    A[i] = Math.max(A[i], accumulate + A[i + j]);
                }
            }
        }
        return A[0];
    }

    public static void main(String[] args) {
        ProteinParsing pp = new ProteinParsing();
        Set<String> P = new HashSet<String>();
        P.add("A");
        P.add("AC");
        P.add("AG");
        P.add("CGT");
        P.add("TTT");

        System.out.println(pp.maxDivision("ACGTTTAC", P, 4));
        System.exit(0);
    }
}
