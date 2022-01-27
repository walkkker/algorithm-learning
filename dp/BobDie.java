package dp;

public class BobDie {

    public static double BobDie1(int N, int M, int row, int col, int k) {
        if (N < 0 || M < 0 || row < 0 || row >= N || col < 0 || col >= M || k < 0) {
            return -1;
        }
        long liveTimes = process(row, col, k, N, M);
        return (double) liveTimes / Math.pow(4, k);
    }

    // Bob走路存活的次数
    public static long process(int x, int y, int rest, int N, int M) {
        if (rest == 0) {
            return x >= 0 && x < N && y >= 0 && y < M ? 1 : 0;
        }
//        越界条件写错了, 不应该是 &&， 而应该是 ||
//        if (x < 0 && x >= N && y < 0 && y >= M) {
//            return 0;
//        }
        if (x < 0 || x >= N || y < 0 || y >= M) {
            return 0;
        }

        long ans = 0;
        ans += process(x - 1, y, rest - 1, N, M);
        ans += process(x + 1, y , rest - 1, N, M);
        ans += process(x, y - 1, rest - 1, N, M);
        ans += process(x, y + 1, rest - 1, N, M);
        return ans;
    }

    public static double BobDieDp(int N, int M, int row, int col, int k) {
        if (N < 0 || M < 0 || row < 0 || row >= N || col < 0 || col >= M || k < 0) {
            return -1;
        }
        int[][][] dp = new int[N][M][k + 1];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                dp[i][j][0] = 1;
            }
        }

        for (int rest = 1; rest <= k; rest++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    dp[i][j][rest] += pick(i - 1, j, rest - 1, N, M, dp);
                    dp[i][j][rest] += pick(i + 1, j, rest - 1, N, M, dp);
                    dp[i][j][rest] += pick(i, j - 1, rest - 1, N, M, dp);
                    dp[i][j][rest] += pick(i, j + 1, rest - 1, N, M, dp);
                }
            }
        }
        return dp[row][col][k] / Math.pow(4, k);
    }

    // 用以检查 对应的参数 是否可以从 dp表中 取得目标值。如果超过了dp表的范围，那么返回0 （递归函数中限定）.
    public static int pick(int x, int y, int rest, int N, int M, int[][][] dp) {
        if (x < 0 || x >= N || y < 0 || y >= M) {
            return 0;
        }
        return dp[x][y][rest];
    }





    public static void main(String[] args) {
        System.out.println(BobDie1(50, 50, 6, 6, 10));
        System.out.println(BobDieDp(50, 50, 6, 6, 10));
    }




}
