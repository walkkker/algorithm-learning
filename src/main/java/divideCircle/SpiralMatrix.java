package divideCircle;

import java.util.*;

public class SpiralMatrix {

    class Solution {
        public List<Integer> spiralOrder(int[][] matrix) {
            // (a, b)左上角 +  (c, d)右下角
            int a = 0;
            int b = 0;
            int c = matrix.length - 1;
            int d = matrix[0].length - 1;
            List<Integer> ans = new ArrayList<>();
            System.out.println(c);
            // 【错误点1】这个边界 也 超麻烦。 少了考虑一列的越界情况。 (a, b) (c, d) 四个都要参与进来，才能真正保证 不会出现越界导致的错误情况
            // 之前只使用了 while (a <= c), 导致 只有 一列得时候。 该终止的时候没有终止，从而导致数组越界
            while (a <= c && b <= d) {
                printEdge(matrix, a++, b++, c--, d--, ans);
            }
            return ans;
        }

        public void printEdge(int[][] matrix, int a, int b, int c, int d, List<Integer> ans) {
            // 【错误点2】
            // 最直接的方案 就是 最正确的方案！！！
            // 先不要想着简化代码，先把各种情况所包含的代码都先写出来，然后再看是否要简化。不要想着一步登天！！！ 步步为营更招人喜欢而且巩固！！！

            // 此处注意一点， 针对只有一个点的情况也进行过讨论。 但是coding过程中， 发现，如果至右一个点，会被包含进 if (a == c) 这一条分支。 就是最后 ans.add(matrix[c][d])

            // 首先处理特殊情况， 即 一维情况，包含 只剩 一列或者一行的时候
            if (a == c) {
                for (int j = b; j < d; j++) {
                    ans.add(matrix[a][j]);
                }
                ans.add(matrix[c][d]);
            } else if (b == d) {
                for (int i = a; i < c; i++) {
                    ans.add(matrix[i][d]);
                }
                ans.add(matrix[c][d]);
            } else {     // 二维  普遍情况  处理方式
                // top
                for (int j = b; j < d; j++) {
                    ans.add(matrix[a][j]);
                }
                // right
                for (int i = a; i < c; i++) {
                    ans.add(matrix[i][d]);
                }
                // down
                for (int j = d; j > b; j--) {
                    ans.add(matrix[c][j]);
                }
                // left
                for (int i = c; i > a; i--) {
                    ans.add(matrix[i][b]);
                }
            }
        }
    }
}
