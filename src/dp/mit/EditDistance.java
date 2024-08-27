package dp.mit;

/**
 * @author dungptm2
 */
public class EditDistance {

    public int minEdit(String A, String B) {
        int nA = A.length();
        int nB = B.length();
        int[][] L = new int[nA + 1][nB+ 1];

        for (int i = nA; i >= 0; i--) {
            L[i][nB] = nA-i;
        }
        for (int j = nB; j >= 0; j--) {
            L[nA][j] = nB-j;
        }

        for (int i = nA-1; i >= 0; i--) {
            for (int j = nB-1; j >= 0; j--) {
                if (A.charAt(i) == B.charAt(j)) {
                    L[i][j] = L[i+1][j+1];
                } else {
                    int rep = 1+ L[i+1][j+1];
                    int del = 1 + L[i+1][j];
                    int ins = 1 + L[i][j+1];
                    L[i][j] = Math.min(Math.min(rep, del), ins);
                }
            }
        }
        return L[0][0];
    }

    public static void main(String[] args) {
        EditDistance editDistance = new EditDistance();
        System.out.println(editDistance.minEdit("12304", "123"));
        System.exit(0);
    }
}
