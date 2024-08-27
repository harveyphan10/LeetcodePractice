package dp.mit;

import java.util.stream.IntStream;

/**
 * @author dungptm2
 */
public class EffectiveCampaigning {

    public int maxTotalDelegate(int[] d, int[] z) {
        int n = d.length;
        int[] A = new int[n + 1];

        A[n] = 0;
        A[n-1] = d[n-1];
        A[n-2] = Math.max(d[n-2], z[n-1] + A[n-1]);

        for (int i = n-3; i >= 0; i--) {
            A[i] = Math.max(z[i] + A[i+1], d[i] + A[i+3] + z[i+1] + z[i+2]);
        }

        return A[0];
    }

    public static void main(String[] args) {
        EffectiveCampaigning effectiveCampaigning = new EffectiveCampaigning();
        int[] d = {5, 2, 4, 6, 3};
        int[] z = {4, 1, 3, 0, 2};
        int maxTotalDelegate = effectiveCampaigning.maxTotalDelegate(d, z);
        System.out.println(maxTotalDelegate);
        int D = IntStream.of(d).sum();
        System.out.println("Can win: " + (maxTotalDelegate >= D/2 +1));
        System.exit(0);
    }
}
