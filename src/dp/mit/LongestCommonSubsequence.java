package dp.mit;

/**
 * @author dungptm2
 */
public class LongestCommonSubsequence {

    public int longestCommonSubsequence(String A, String B) {
        int[][] L = new int[A.length() + 1][B.length() + 1];
        for (int i = A.length(); i >= 0; i--) {
            L[i][B.length()] = 0;
        }
        for (int j = B.length(); j >= 0; j--) {
            L[A.length()][j] = 0;
        }

        for (int i = A.length() - 1; i >= 0; i--) {
            for (int j = B.length() - 1; j >= 0; j--) {
                if (A.charAt(i) == B.charAt(j)) {
                    L[i][j] = 1+ L[i + 1][j + 1];
                } else {
                    L[i][j] = Math.max(L[i + 1][j], L[i][j + 1]);
                }
            }
        }
        return L[0][0];
    }

    public static void main(String[] args) {
        String A = "HIEROGLYPHOLOGY";
        String B = "MICHAELANGELO";
        int longest = new LongestCommonSubsequence().longestCommonSubsequence(A, B);
        System.out.println("longest = " + longest);
        System.exit(0);
    }
}

