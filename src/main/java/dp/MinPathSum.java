package dp;

public class MinPathSum {

    // 暴力递归
    public int minPathSum1(int[][] grid) {
        return process(0, 0, grid);
    }

    public int process(int x, int y, int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        if (x < 0 || x >= rows || y < 0 || y >= cols) {
            return Integer.MAX_VALUE;
        }

        if (x == rows - 1 && y == cols - 1) {
            return grid[x][y];
        }

        int p1 = process(x, y + 1, grid);
        int p2 = process(x + 1, y, grid);
        return grid[x][y] + Math.min(p1, p2);
    }

    // 快速转换
    public int minPathSum2(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] dp = new int[rows][cols];
        dp[rows - 1][cols - 1] = grid[rows - 1][cols - 1];
        for (int y = cols - 2; y >= 0; y--) {
            dp[rows - 1][y] = grid[rows - 1][y] + dp[rows - 1][y + 1];
        }

        for (int x = rows - 2; x >= 0; x--) {
            dp[x][cols - 1] = grid[x][cols - 1] + dp[x + 1][cols - 1];
        }

        for (int x = rows - 2; x >= 0; x--) {
            for (int y = cols - 2; y >= 0; y--) {
                dp[x][y] = grid[x][y] + Math.min(dp[x][y + 1], dp[x + 1][y]);
            }
        }
        return dp[0][0];
    }

    // 空间压缩
    public int minPathSum3(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[] dp = new int[cols];
        dp[cols - 1] = grid[rows - 1][cols - 1];
        for (int y = cols - 2; y >= 0; y--) {
            dp[y] = grid[rows - 1][y] + dp[y + 1];
        }

        for (int x = rows - 2; x >= 0; x--) {
            dp[cols - 1] += grid[x][cols - 1];
            for (int y = cols - 2; y >= 0; y--) {
                dp[y] = grid[x][y] + Math.min(dp[y], dp[y + 1]);
            }
        }
        return dp[0];
    }
}
