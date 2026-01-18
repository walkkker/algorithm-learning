package divideCircle;

public class RotateMatrix {

    public static void rotateMatrix(int[][] matrix) {
        // 固定 【左上角】 和 【右上角】 然后不断往中间压缩
        int a = 0;
        int b = 0;
        // 【错误点】 没有将 他们 -1。 (c, d) 对应 右下角点的坐标位置
        int c = matrix.length - 1;
        int d = matrix[0].length - 1;
        while (a <= c && b <= d) {
            rotateSingleLayer(matrix, a++, b++, c--, d--);
        }
    }

    // 旋转单圈
    public static void rotateSingleLayer(int[][] matrix, int a, int b, int c, int d) {
        int numOfGroups = d - b;
        for (int i = 0; i < numOfGroups; i++) {
            int tmp = matrix[a][b + i];
            matrix[a][b + i] = matrix[c - i][b];
            matrix[c - i][b] = matrix[c][d - i];
            matrix[c][d - i] = matrix[a + i][d];
            matrix[a + i][d] = tmp;
        }
    }


    public static void main(String[] args) {
        int[][] matrix = {{1,2,3,4},
            {5,6,7,8},
            {9,10,11,12},
            {13,14,15,16}};
        printMatrix(matrix);
        rotateMatrix(matrix);
        System.out.println();
        printMatrix(matrix);
    }

    public static void printMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
