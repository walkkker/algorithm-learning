package backtracking;

public class NQueens2 {

    public class Solution1{
        // 随想录介绍 回溯方法，暴力但是直观，并且在 二维数组上 做判断
        public int totalNQueens(int n) {
            char[][] cb = new char[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    cb[i][j] = '.';
                }
            }
            // todo
            return process(0, cb);
        }

        public int process(int row, char[][] cb) {
            int n = cb.length;
            if (row == n) {
                return 1;
            }

            int ans = 0;
            for (int col = 0; col < n; col++) {
                if (isValid(row, col, cb)) {
                    cb[row][col] = 'Q';
                    ans += process(row + 1, cb);
                    cb[row][col] = '.';
                }
            }
            return ans;
        }

        public boolean isValid(int row, int col, char[][] cb) {
            int n = cb.length;
            for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
                if (cb[i][j] == 'Q') {
                    return false;
                }
            }

            for (int i = row - 1, j = col; i >= 0; i--) {
                if (cb[i][j] == 'Q') {
                    return false;
                }
            }

            for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
                if (cb[i][j] == 'Q') {
                    return false;
                }
            }

            return true;
        }
    }

    public class Solution2 {
        public int totalNQueens(int n) {
            int[] selected = new int[n];
            return process(0, selected);
        }
        public int process(int row, int[] selected) {
            int n = selected.length;
            // 如果成功到达末尾位置，说明0-n-1，全部成功布局，此时返回1，表示有一个成功的布局
            if (row == n) {
                return 1;
            }

            int ans = 0;
            // 否则的话，我们就要去尝试在 第row行上的， 每一列是否可以放 皇后
            for (int col = 0; col < n; col++) {
                if (isValid(row, col, selected)) {
                    selected[row] = col;
                    ans += process(row + 1, selected);
                }
            }
            return ans;
        }
        public boolean isValid(int row, int col, int[] selected) {
            int n = selected.length;
            // !!!! 注意，只遍历 row行上面的部分，因为我是从上往下，要放第row行的值；千万不要搞错了
            for (int i = 0; i < row; i++) {
                int j = selected[i];   // 已选中点 的 坐标为 (i, j)；
                // 针对每个已选中点，判断 其是否与 当前点在同一列 或者 对角线的位置
                if (j == col || Math.abs(row - i) == Math.abs(col - j)) {
                    return false;
                }
            }
            // 遍历所有已选节点后，没有冲突点，此时返回true，表示 (row,col) 有效
            return true;
        }
    }
}
