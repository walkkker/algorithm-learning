package backtracking;

import java.util.List;
import java.util.ArrayList;

public class NQueens1 {

    public class Solution1 {
        List<List<String>> res = new ArrayList<>();

        public List<List<String>> solveNQueens(int n) {
            char[][] cb = new char[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    cb[i][j] = '.';
                }
            }
            process(0, n, cb);
            return res;
        }

        public void process(int row, int n, char[][] cb) {
            // base case, 成功到达最后一行，表明前面排列成功了
            if (row == n) {
                res.add(Array2List(cb));
                return;
            }

            // 分解子问题（递归） + 回溯
            // 子问题为，找到在 row行上的每一个 有效的位置，然后递归剩余部分。
            for (int col = 0; col < n; col++) {
                if (isValid(row, col, cb, n)) {
                    cb[row][col] = 'Q';
                    process(row + 1, n, cb);
                    cb[row][col] = '.';
                }
            }
        }

        // 将 满足条件的 char[][] cb 变成 List<String>， 作为一种答案
        public List<String> Array2List(char[][] cb) {
            List<String> ans = new ArrayList<>();
            for (char[] c : cb) {
                ans.add(String.valueOf(c));
            }
            return ans;
        }

        // (row, col) 是当前待测试的点，       （r,c）在char[][] cb上面进行检测 validity
        public boolean isValid(int row, int col, char[][] cb, int n) {
            // 45度， 90度 ， 135度
            // 只需要检测上半区， 去检测 这三个角度的直线上，是否有冲突
            // 检测冲突的方式就是 遍历直线上每一个位置，如果有Q，则冲突，返回false
            // 只有当 检测的三条直线上的 坐标点 都没有Q，那么说明 (row, col)不会冲突，从而可以返回 true
            // 45
            for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
                if (cb[i][j] == 'Q') {
                    return false;
                }
            }

            for (int i = row - 1; i >= 0; i--) {
                if (cb[i][col] == 'Q') {
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

    public class Solution2{
        List<List<String>> ans = new ArrayList<>();
        public List<List<String>> solveNQueens(int n) {
            int[] selected = new int[n];
            process(0, n, selected);
            return ans;
        }

        public void process(int row, int n, int[] selected) {
            if (row == n) {
                ans.add(Array2List(selected));
                return;
            }

            for(int col = 0; col < n; col++) {
                if (isValid(row, col, selected)) {
                    selected[row] = col;
                    process(row + 1, n, selected);
                }
            }
        }

        public List<String> Array2List(int[] selected) {
            List<String> res = new ArrayList<>();
            int n = selected.length;
            char[] c = new char[n];
            for (int i = 0; i < n; i++) {
                c[i] = '.';
            }
            for (int row = 0; row < n; row++) {
                int col = selected[row];
                c[col] = 'Q';
                res.add(String.valueOf(c));
                c[col] = '.';
            }
            return res;
        }

        public boolean isValid(int row, int col, int[] selected) {
            int n = selected.length;
            for (int i = 0; i < row; i++) {
                if (col == selected[i] || Math.abs(i - row) == Math.abs(selected[i] - col)) {
                    return false;
                }
            }
            return true;
        }
    }

}
