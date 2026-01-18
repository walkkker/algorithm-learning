package dp;



public class LongestPalindromeSubseq {

    // 方法1： 暴力递归
    public int longestPalindromeSubseq1(String s) {
        if (s == null) {
            return 0;
        }
        char[] str = s.toCharArray();
        return process(0, str.length - 1, str);
    }

    public int process(int i, int j, char[] str) {
        if (i == j) {
            return 1;
        }
        if (i > j) {
            return 0;
        }

        if (str[i] == str[j]) {
            return 2 + process(i + 1, j - 1, str);
        } else {
            return Math.max(process(i + 1, j, str), process(i, j - 1, str));
        }
    }

    // 方法2： 二维动态规划
    public int longestPalindromeSubseq2(String s) {
        if (s == null) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[][] dp = new int[N][N];
        for (int i = 0; i < N; i++) {
            dp[i][i] = 1;
        }

        for (int i = N - 1; i >= 0; i--) {

            for (int j = i + 1; j < N; j++) {
                if (str[i] == str[j]) {
                    dp[i][j] = 2 + dp[i + 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[0][N - 1];
    }

    // 方法三： 一维数组 空间压缩 最优解
    public int longestPalindromeSubseq3(String s) {
        if (s == null) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[] dp = new int[N];
        dp[N - 1] = 1;

        for (int i = N - 2; i >= 0; i--) {
            dp[i] = 1;
            int tmp = 0;
            int ans;
            for (int j = i + 1; j < N; j++) {
                if (str[i] == str[j]) {
                    ans = 2 + tmp;
                } else {
                    ans = Math.max(dp[j], dp[j - 1]);
                }
                tmp = dp[j];
                dp[j] = ans;
            }
        }
        return dp[N - 1];
    }
}
