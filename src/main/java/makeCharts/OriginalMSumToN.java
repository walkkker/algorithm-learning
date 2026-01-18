package makeCharts;

/**
 * 最原始的题意： 只是让你找到 连续数字的累加和 等于 N， 是否存在
 *
 * 直接变成 【子数组的规定累加和问题】
 *
 * 窗口内范围单调性 -> 双指针滑动窗口   --->   O(N)
 *
 *
 * 进一步： 我们发现 这道题 的 【输入 -> 输出】 为  【int -> boolean】
 * 所以，使用 打表法 来查看 是否有 O（1）复杂度的解法。
 */
public class OriginalMSumToN {

    public static boolean MSumToN1(int N) {
        int R = 0;   // [L, R), R 指向 窗口的右边界的下一个位置， 好coding
        int sum = 0;
        for (int L = 1; L < N; L++) {
            // 【STEP1】 右边界右移
            while (R < N && sum + R <= N) {
                sum += R;
                R++;
            }
            // 【STEP2】 针对当前L的最大窗口，进行判断
            if (sum == N) {
                return true;
            }
            // 【STEP3】 L要右移了，清除现在L位置的影响
            sum -= L;
        }
        return false;
    }

    public static void main(String[] args) {
        int times = 100000;
        for (int i = 1; i < times; i++) {
            if (MSumToN1(i) != MSumToN2(i)) {
                System.out.println("opps");
            }
        }
    }

    public static boolean MSumToN2(int N) {
        if (N == (N & -N)) {    // 判断数字是否是 2 的 平方数  ---> 位运算
            return false;
        } else {
            return true;
        }
    }


}
