package handbook.basic;

import java.util.Arrays;

/**
 * @author dungptm2
 */
public class DynamicProgramming {
    public int climbStairs(int n) {
        if (n == 1) {
            return 1;
        }
        int[] A = new int[n];
        A[n - 1] = 1;
        A[n - 2] = 2;
        for (int i = n - 3; i >= 0; i--) {
            A[i] = A[i + 1] + A[i + 2];
        }
        return A[0];
    }

    public int coinChange(int[] coins, int amount) {
        int[] X = new int[amount + 1];
        Arrays.fill(X, amount + 1);
        X[0] = 0;
        for (int t = 1; t <= amount; t++) {
            for (int i = 0; i < coins.length; i++) {
                if (t >= coins[i]) {
                    X[t] = Math.min(1 + X[t - coins[i]], X[t]);
                }
            }
        }
        return X[amount] == amount + 1 ? -1 : X[amount];
    }

    public int rob(int[] nums) {
        int n = nums.length;
        int[] A = new int[n + 1];
        A[n] = 0;
        A[n - 1] = nums[n - 1];

        for (int i = n - 2; i >= 0; i--) {
            A[i] = Math.max(nums[i] + A[i + 2], A[i + 1]);
        }
        return A[0];
    }

    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] L = new int[n + 1];
        L[n] = 0;
        L[n - 1] = 1;

        for (int i = n - 2; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                if (nums[i] < nums[j]) {
                    L[i] = Math.max(L[i], 1 + L[j]);
                } else {
                    L[i] = Math.max(L[i], 1);
                }
            }
        }
        return Arrays.stream(L).max().getAsInt();
    }

    public boolean canPartition(int[] nums) {
        int n = nums.length;
        int sum = Arrays.stream(nums).sum();
        if (sum % 2 != 0) {
            return false;
        }
        sum = sum / 2;
        int[][] X = new int[n + 1][sum + 1];
        for (int i = 0; i <= n; i++) {
            X[i][0] = 1;
        }
        for (int t = 1; t <= sum; t++) {
            X[n][t] = 0;
        }

        for (int i = n - 1; i >= 0; i--) {
            for (int t = 1; t <= sum; t++) {
                if (t >= nums[i]) {
                    X[i][t] = Math.max(X[i + 1][t - nums[i]], X[i + 1][t]);
                } else {
                    X[i][t] = X[i + 1][t];
                }
            }
        }
        return X[0][sum] != 0;
    }

    public int longestCommonSubsequence(String text1, String text2) {
        int n = text1.length();
        int m = text2.length();
        int[][] L = new int[n + 1][m + 1];
        L[n][m] = 0;

        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                if (text1.charAt(i) == text2.charAt(j)) {
                    L[i][j] = L[i + 1][j + 1] + 1;
                } else {
                    L[i][j] = Math.max(L[i + 1][j], L[i][j + 1]);
                }
            }
        }
        return L[0][0];
    }

    public int rob2(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        int[] L1 = new int[n + 1];
        int[] L2 = new int[n + 1];
        L1[n] = 0;
        L1[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 1; i--) {
            L1[i] = Math.max(L1[i + 1], L1[i + 2] + nums[i]);
        }
        L2[n] = 0;
        L2[n - 1] = 0;
        for (int i = n - 2; i >= 0; i--) {
            L2[i] = Math.max(L2[i + 1], L2[i + 2] + nums[i]);
        }
        return Math.max(L1[1], L2[0]);
    }

    public int uniquePaths(int m, int n) {
        int[][] A = new int[m + 1][n + 1];
        A[m - 1][n] = 1;
        for (int i = 0; i < m; i++) {
            A[i][n - 1] = 1;
        }
        for (int i = 0; i < n; i++) {
            A[m - 1][i] = 1;
        }
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                A[i][j] = A[i + 1][j] + A[i][j + 1];
            }
        }
        return A[0][0];
    }

    public boolean canJump(int[] nums) {
        int n = nums.length;
        int[] A = new int[n + 1];
        A[n] = 0;
        A[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            for (int j = i + nums[i] > n ? n - i : nums[i]; j >= 0; j--) {
                A[i] = Math.max(A[i], A[i + j]);
                if (A[i] == 1) {
                    break;
                }
            }
        }
        return A[0] == 1;
    }

    public int numDecodings(String s) {
        if (s.charAt(0) == '0') {
            return 0;
        }
        int n = s.length();
        int[] A = new int[n + 1];
        A[n] = 1;
        A[n - 1] = s.charAt(n - 1) == '0' ? -1 : 1;

        for (int i = n - 2; i >= 0; i--) {
            if (s.charAt(i) == '0') {
                A[i] = -1;
            } else if (Integer.parseInt(s.substring(i, i + 2)) <= 26) {
                if (s.charAt(i + 1) == '0') {
                    A[i] = A[i + 2];
                } else if (A[i + 2] == -1) {
                    A[i] = A[i + 1];
                } else {
                    A[i] = 2 + A[i + 1];
                }
            } else {
                A[i] = A[i + 1];
            }
        }
        return A[0] == -1 ? 0 : A[0];
    }

    public static void main(String[] args) {
        DynamicProgramming dp = new DynamicProgramming();
        System.out.println(dp.numDecodings("1212"));
        System.exit(0);
    }
}
