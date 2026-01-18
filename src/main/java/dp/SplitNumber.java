package dp;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class SplitNumber {

    // 前一个 拆的数字 是 1， 还剩rest要去拆
    // 方法1 ： 暴力递归。 并且增加了一个功能： 将 满足条件的路径 使用回溯法 添加到list里面，从而可以查看结果的正确性
    public static int process(int index, int rest, int N, Deque<Integer> deque, ArrayList<ArrayList<Integer>> list) {
        if (rest == 0) {
            list.add(new ArrayList<>(deque));
            return 1;
        }
        if (rest < 0) {
            return 0;
        }

        int ans = 0;


        // todo【错误点】 也不算是错误，这里有一个地方可以进行剪枝，但是保留了错误答案，所以在此标记出来。
        // todo 条件可以改成是   for (int i = index; i <= rest; i++)
        // todo 后面的dp都是按照没有剪枝的版本写的，在此标注出来，复习时候注意 这个 【剪枝】 操作
        for (int i = index; i <= N; i++) {
            deque.offerLast(i);
            ans += process(i, rest - i, N, deque, list);
            deque.pollLast();
        }
        return ans;
    }

    public static int splitNumber(int n) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        int ans = process(1, n, n, new LinkedList<>(), list);
        for (List<Integer> l : list) {
            System.out.println(l);
        }
        return ans;
    }

    // 方法2： 普通暴力递归改dp
    public static int splitNumberDp(int n) {
        int[][] dp = new int[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i][0] = 1;
        }

        for (int index = n; index >= 1; index--) {
            for (int rest = 1; rest <= n; rest++) {
                int ans = 0;

                for (int i = index; i <= n; i++) {
                    ans += rest - i < 0 ? 0 : dp[i][rest - i];
                }
                dp[index][rest] = ans;
            }
        }
        return dp[1][n];
    }

    // 方法三： 斜率优化版本， 将【枚举行为】 变为 有限个元素的计算
    public static int dpPro(int n) {
        int[][] dp = new int[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i][0] = 1;
        }
        dp[n][n] = 1;
        for (int index = n - 1; index >= 1; index--) {
            for (int rest = 1; rest <= n; rest++) {
                if (rest - index < 0) {
                    dp[index][rest] = dp[index + 1][rest];
                } else {
                    dp[index][rest] = dp[index + 1][rest] + dp[index][rest - index];
                }
            }
        }
        return dp[1][n];
    }

    // 方法四： 斜率优化 + 空间压缩 =》 减少时间复杂度，并将二维dp表压缩为一维dp表 =》 最优解
    public static int finalDp(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[n] = 1;
        for (int index = n - 1; index >= 1; index--) {
            for (int rest = 1; rest <= n; rest++) {
                dp[rest] = rest - index < 0 ? dp[rest] : dp[rest - index] + dp[rest];
            }
        }
        return dp[n];
    }

    // TEST
    public static void main(String[] args) {
        System.out.println(splitNumber(15));
        System.out.println(splitNumberDp(15));
        System.out.println(dpPro(15));
        System.out.println(finalDp(15));
    }
}
