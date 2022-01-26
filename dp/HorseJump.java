package dp;

public class HorseJump {

    public static int ways(int x, int y, int step) {
        return process(0, 0, step, x, y);
    }

    public static int process(int x, int y, int rest, int targetX, int targetY) {
        // 边界条件 注意搞清楚. 并且转成动态规划时，特别注意，如果递归中存在边界条件，那么dp中一定在计算时，注意坐标的分类讨论
        if (x < 0 || x >= 9 || y < 0 || y >= 10) {
            return 0;
        }
        if (rest == 0) {
            return x == targetX && y == targetY ? 1 : 0;
        }

        int ans = 0;
        ans += process(x - 2, y + 1, rest - 1, targetX, targetY);
        ans += process(x - 1, y + 2, rest - 1, targetX, targetY);
        ans += process(x + 1, y + 2, rest - 1, targetX, targetY);
        ans += process(x + 2, y + 1, rest - 1, targetX, targetY);
        ans += process(x + 2, y - 1, rest - 1, targetX, targetY);
        ans += process(x + 1, y - 2, rest - 1, targetX, targetY);
        ans += process(x - 1, y - 2, rest - 1, targetX, targetY);
        ans += process(x - 2, y - 1, rest - 1, targetX, targetY);
        return ans;
    }

    public static int waysDp(int targetX, int targetY, int step) {
        int[][][] dp = new int[9][10][step + 1];
        dp[targetX][targetY][0] = 1;
        for (int rest = 1; rest <= step; rest++) {
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 10; y++) {
                    dp[x][y][rest] = 0;
                    dp[x][y][rest] += pick(x - 2, y + 1, rest - 1, dp);
                    dp[x][y][rest] += pick(x - 1, y + 2, rest - 1, dp);
                    dp[x][y][rest] += pick(x + 1, y + 2, rest - 1, dp);
                    dp[x][y][rest] += pick(x + 2, y + 1, rest - 1, dp);
                    dp[x][y][rest] += pick(x + 2, y - 1, rest - 1, dp);
                    dp[x][y][rest] += pick(x + 1, y - 2, rest - 1, dp);
                    dp[x][y][rest] += pick(x - 1, y - 2, rest - 1, dp);
                    dp[x][y][rest] += pick(x - 2, y - 1, rest - 1, dp);
                }
            }
        }

        return dp[0][0][step];
    }


    // 为了处理越界问题， 多写一个小函数就可以解决
    public static int pick(int x, int y, int rest, int[][][] dp) {
        if (x < 0 || x >= 9 || y < 0 || y >= 10) {
            return 0;
        }
        return dp[x][y][rest];
    }


    public static void main(String[] args) {
        int x = 7;
        int y = 7;
        int step = 10;
        System.out.println(ways(x, y, step));
        System.out.println(waysDp(x, y, step));

        System.out.println(3 / Math.pow(2, 2));
        System.out.println(Float.MAX_VALUE);
    }
}
