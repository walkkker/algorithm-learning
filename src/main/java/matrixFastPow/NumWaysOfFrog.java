package matrixFastPow;

public class NumWaysOfFrog {
    public int climbStairs(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        int[][] base = {{1, 1},
                {1, 0}};
        int[][] res = matrixPow(base, n - 2);
        return 2 * res[0][0] + res[1][0];
    }

    public int[][] multiMatrix(int[][] A, int[][] B) {
        int[][] ans = new int[A.length][B[0].length];
        for (int i = 0; i < ans.length; i++) {
            for (int j = 0; j < ans[0].length; j++) {
                for (int k = 0; k < A[0].length; k++) {
                    ans[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return ans;
    }

    public int[][] matrixPow(int[][] m, int pow) {
        int[][] ans = new int[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            ans[i][i] = 1;
        }
        int[][] tmp = m;
        for (; pow > 0; pow >>= 1) {
            // 【错误点】 这里注意要加上括号，不然的话: 如果这样写 (pow & 1 == 1) 会被翻译成 (pow & (1 == 1))最终导致返回int类型，出错
            if ((pow & 1) == 1) {
                ans = multiMatrix(ans, tmp);
            }
            tmp = multiMatrix(tmp, tmp);
        }
        return ans;
    }
}
