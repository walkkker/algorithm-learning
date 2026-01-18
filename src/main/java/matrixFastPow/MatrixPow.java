package matrixFastPow;

/**
 * 固定公式，请务必记牢！
 */
public class MatrixPow {

    // 矩阵相乘函数
    // 返回值： 两个矩阵相乘得到的 【二维矩阵】
    // 参数： 二维矩阵A， 二维矩阵B
    public int[][] multiMatrix(int[][] A, int[][] B) {
        int[][] ans = new int[A.length][B[0].length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B[0].length; j++) {
                for (int k = 0; k < B.length; k++) {
                    // 【错误点】 这里经常写错，务必记牢。 是要使用 【+=】  +  【*】
                    ans[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return ans;
    }

    public int[][] matrixPow(int[][] mat, int pow) {
        int[][] ans = new int[mat.length][mat[0].length];
        // 将矩阵设置成 【单位矩阵】
        for (int i = 0; i < ans.length; i++) {
            ans[i][i] = 1;
        }
        int[][] tmp = mat;
        for (; pow > 0; pow >>= 1) {
            if ((pow & 1) == 1) {
                ans = multiMatrix(ans, tmp);
            }
            tmp = multiMatrix(tmp, tmp);
        }
        return ans;
    }

}
