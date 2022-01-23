package dp;

/**
 * 有 n 个物品和一个大小为 m 的背包. 给定数组 A 表示每个物品的大小和数组 V 表示每个物品的价值.
 *
 * 问最多能装入背包的总价值是多大?
 */
public class Knapsack {

    // 暴力递归
    public int backPackII1(int m, int[] A, int[] V) {
        // write your code here
        return process(0, m, A, V);
    }

    // 从第i个货物开始，重量不超过rest情况下返回的最大价值
    public int process(int i, int rest, int[] A, int[] V) {
        if (rest == 0) {
            return 0;
        }
        if (i == A.length) {
            return 0;
        }
        // 对于当前货物而言，两种选择取最大值 - 要/不要
        // 要
        int p1 = 0;
        if (rest - A[i] >= 0) {
            p1 = V[i] + process(i + 1, rest - A[i], A, V);
        }
        // 不要
        int p2 = process(i + 1, rest, A, V);
        return Math.max(p1, p2);
    }


    // 暴力递归改动态规划
    public int backPackII2(int m, int[] A, int[] V) {
        // write your code here
        int N = A.length;
        int[][] dp = new int[N + 1][m + 1];
        for (int rest = 1; rest <= m; rest++) {
            for (int i = N - 1; i >= 0; i--) {
                int p1 = 0;
                if (rest - A[i] >= 0) {
                    p1 = V[i] + dp[i + 1][rest - A[i]];
                }
                // 不要
                int p2 = dp[i + 1][rest];
                dp[i][rest] = Math.max(p1, p2);
            }
        }
        return dp[0][m];
    }


}
