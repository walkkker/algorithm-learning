package divideCircle;

import java.util.ArrayList;
import java.util.List;

public class ZigZagMatrix {

    public static void nextA(int rows, int cols, int[] a) {
        if (a[0] != rows - 1) {
            a[0]++;
        } else {
            a[1]++;
        }
    }

    public static void nextB(int rows, int cols, int[] b) {
        if (b[1] != cols - 1) {
            b[1]++;
        } else {
            b[0]++;
        }
    }

    public static List<Integer> zigzag(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] a = {0, 0};
        int[] b = {0, 0};
        boolean isPositive = true;
        List<Integer> ans = new ArrayList<>();
        while (a[0] >= b[0] && a[1] <= b[1]) {
            printLine(matrix, a, b, isPositive, ans);
            nextA(rows, cols, a);
            nextB(rows, cols, b);
            isPositive = !isPositive;
        }
        return ans;
    }

    public static void printLine(int[][] matrix, int[] a, int[] b, boolean isPositive, List<Integer> ans) {
        int[] start = isPositive ? a : b;
        int[] end = start == a ? b : a;
        int row = start[0];
        int col = start[1];
        // 【错误点】 边界没有搞清楚 ==》 当 row == end[0] 时， 依旧要做输出的
        while ((isPositive && row != end[0] - 1) || (!isPositive && row != end[0] + 1)) {
            ans.add(matrix[row][col]);
            row = isPositive ? row - 1 : row + 1;
            col = isPositive ? col + 1 : col - 1;
        }
    }

    public static void main(String[] args) {
        int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } };
        List<Integer> list = zigzag(matrix);
        for (int num : list) {
            System.out.print(num + " ");
        }
        System.out.println();
        System.out.println();

        int[][] matrix1 = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } };
        List<Integer> ans1 = printMatrixZigZag(matrix1);
        for (int num : ans1) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    /* ------------------------------------------------------------------------------ */

    public static List<Integer> printMatrixZigZag(int[][] matrix) {
        // 左下角 使用 DR, DC
        // 右上角 使用 TR, TC
        // 初始值 都设为 (0, 0) 节点
        int TR = 0;
        int TC = 0;
        int DR = 0;
        int DC = 0;
        int endRow = matrix.length - 1;
        int endCol = matrix[0].length - 1;
        boolean fromUp = false;
        List<Integer> ans = new ArrayList<>();
        while (TR != endRow + 1) {
            printLevel(matrix, TR, TC, DR, DC, fromUp, ans);
            TR = TC != endCol ? TR : TR + 1;
            TC = TC != endCol ? TC + 1 : TC;
            // 【！！！错误点】下面顺序错了，导致 (DR, DC) 位置移动错误
            // 你要拿 DR 去比，那么 （如果你DR在前，被更改了，那么DC会以更改后的DR进行判断，就错了。正确的是，我们要以更改前的DR进行判断）
            // 因为以上一次节点的行来判断， 所以 行 要 后动 =》 先动 列， 再动 行
//            DR = DR != endRow ? DR + 1 : DR;
//            DC = DR != endRow ? DC : DC + 1;
            DC = DR != endRow ? DC : DC + 1;
            DR = DR != endRow ? DR + 1 : DR;
            fromUp = !fromUp;
        }
        return ans;
    }


    public static void printLevel(int[][] matrix, int TR, int TC, int DR, int DC, boolean fromUp, List<Integer> ans) {
        if (fromUp) {
            // 右上角 (TR, TC) -> 左下角 (DR, DC)
            while (TR != DR + 1) {
                ans.add(matrix[TR++][TC--]);
            }
        }

        if (!fromUp) {
            // 左下角 (DR, DC) -》 右上角 (TR. TC)
            while (DR != TR - 1) {
                ans.add(matrix[DR--][DC++]);
            }
        }
    }

}
