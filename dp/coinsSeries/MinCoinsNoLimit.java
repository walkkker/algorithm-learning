package dp.coinsSeries;

public class MinCoinsNoLimit {

    // 记得 满足题目要求中的 当coins无法拼接出amount时，返回 -1 这个条件。
    public int coinChange(int[] coins, int amount) {
        if (coins == null) {
            return 0;
        }
        int ans = process(0, amount, coins);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public static int process(int index, int rest, int[] arr) {
        if (index == arr.length) {
            return rest == 0 ? 0 : Integer.MAX_VALUE;
        }
        int minCoins = Integer.MAX_VALUE;
        for (int i = 0; i * arr[index] <= rest; i++) {
            int tmp = process(index + 1, rest - i * arr[index], arr);
            if (tmp != Integer.MAX_VALUE) {
                minCoins = Math.min(minCoins, i + tmp);
            }
        }
        return minCoins;
    }


    public int coinChangePro(int[] coins, int amount) {
        if (coins == null) {
            return 0;
        }
        int N = coins.length;
        int[] dp = new int[amount + 1];
        for (int j = 1; j <= amount; j++) {
            dp[j] = Integer.MAX_VALUE;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= amount; rest++) {
                if (rest - coins[index] >= 0 && dp[rest - coins[index]] != Integer.MAX_VALUE) {
                    dp[rest] = Math.min(dp[rest - coins[index]] + 1, dp[rest]);
                }
            }
        }
        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }




}
