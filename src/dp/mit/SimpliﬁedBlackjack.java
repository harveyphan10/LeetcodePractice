package dp.mit;

/**
 * @author dungptm2
 */
public class SimpliﬁedBlackjack {

    public int maxRoundWin(int[] c) {
        int n = c.length;
        int[] H = new int[n + 1];
        H[n] = 0;

        for (int i = n - 1; i >= 0; i--) {
            if (i + 5 > n) {
                H[i] = 0;
                continue;
            }
            int scoreDealer = c[i] + c[i + 1];
            int scorePlayer1 = c[i + 2] + c[i + 3];
            int scorePlayer2 = c[i + 2] + c[i + 3] + c[i + 4];

            H[i] = Math.max(((scorePlayer1 <= 21 && scorePlayer1 > scoreDealer) ? 1 : 0) + H[i + 4],
                    ((scorePlayer2 <= 21 && scorePlayer2 > scoreDealer) ? 1 : 0) + H[i + 5]);
        }
        return H[0];
    }

    public static void main(String[] args) {
        int[] c = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        int ret = new SimpliﬁedBlackjack().maxRoundWin(c);
        System.out.println("ret = " + ret);
        System.exit(0);
    }
}
