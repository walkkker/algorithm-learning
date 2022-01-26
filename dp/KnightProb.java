package dp;

public class KnightProb {

    public static double knightProbability(int n, int k, int row, int column) {
        int[][][] dp = new int[n][n][k + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j][0] = 1;
            }
        }

        for (int rest = 1; rest <= k; rest++) {
            for (int x = 0; x < n; x++) {
                for (int y = 0; y < n; y++) {
                    int ans = 0;
                    ans += pick(x + 1, y+2, rest - 1, dp, n);
                    ans += pick(x + 2, y+1, rest - 1, dp, n);
                    ans += pick(x - 1, y-2, rest - 1, dp, n);
                    ans += pick(x - 2, y-1, rest - 1, dp, n);
                    ans += pick(x + 1, y-2, rest - 1, dp, n);
                    ans += pick(x + 2, y-1, rest - 1, dp, n);
                    ans += pick(x - 1, y+2, rest - 1, dp, n);
                    ans += pick(x - 2, y+1, rest - 1, dp, n);
                    dp[x][y][rest] = ans;
                }
            }
        }

        return (double) dp[row][column][k] / Math.pow(8, k);
    }

    public static int pick(int x, int y, int rest, int[][][] dp, int n) {
        if (x < 0 || x >= n || y < 0 || y >= n) {
            return 0;
        }
        return dp[x][y][rest];
    }

    public static void main(String[] args) {
        System.out.println(knightProbability(8,30,6,4));
    }
}
