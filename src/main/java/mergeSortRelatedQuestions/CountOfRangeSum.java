package mergeSortRelatedQuestions;

/**
 * 给你一个整数数组nums 以及两个整数lower 和 upper 。求数组中，值位于范围 [lower, upper] （包含lower和upper）之内的 区间和的个数 。
 *
 *
 * 转化为： 必须以每一个元素为结尾的区间和 在 [lower, upper]中的个数， 加起来，就是整个数组的区间和个数了。
 *
 * 以每一个元素为结尾的区间和 在 [lower, upper]上，我们可以采用 前缀和数组来实现。 只要pre[j] 在 [pre[i] - upper, pre[i] - lower]，就说明 [j+1, i]满足区间和。
 *
 * 也就是说， 对于 num[i]， 求左侧范围元素 num[j] （这次不是比大小了），而是求 左侧元素 num[j] 在 [pre[i] - upper, pre[i] - lower]区间上，但是由于这个区间还是跟 num[i]有关，所以呈现单调性
 *
 * 也就是说，滑动窗口+指针不回退
 *
 * 总结： 1.转化为 前缀和（因为要求区间和）
 *      2. 关键1： 求当前元素i 的 左侧范围元素的【个数】！！！ =》 想到mergeSort
 *      3. 关键2： 对于左侧元素，要求满足条件：其在 [pre[i] - upper, pre[i] - lower]范围上，而该范围的 上下界限 都是单调性
 *      也就是说， 遍历右侧元素时，窗口的上下界限 都只会往右滑，满足指针不回退的技巧
 *
 *  注意一个点：！！！ 题目给定 单个元素范围 -2^31 <= nums[i] <= 2^31 - 1，所以正常方求 【前缀和】时 要使用【Long类型】进行存储
 */
public class CountOfRangeSum {

    public static int countRangeSum(int[] arr, int lower, int upper) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // 制作 前缀和 数组
        long[] preSum = new long[arr.length];
        preSum[0] = arr[0];
        for (int i = 1; i < preSum.length; i++) {
            preSum[i] = preSum[i - 1] + arr[i];
        }
        return process(preSum, 0, preSum.length - 1, lower, upper);
    }

    public static int process(long[] sum, int L, int R, int lower, int upper) {
        if (L == R) {
            return sum[L] >= lower && sum[L] <= upper ? 1 : 0;
        }
        int M = L + ((R - L) / 2);
        return process(sum, L, M, lower, upper)
                + process(sum, M + 1, R, lower, upper)
                + merge(sum, L, M, R, lower, upper);
    }

    public static int merge(long[] arr, int L, int M, int R, int lower, int upper) {
        // 滑动窗口 + 不回退技巧
        // 确定 当前元素 左侧元素 在范围[min, max]的个数
        // 左组为 [L, M], 右组为 [M + 1, R]
        int ans = 0;
        int windowL = L;
        int windowR = L; // [windowL, windowR)
        for (int i = M + 1; i <= R; i++) {
            long min = arr[i] - upper;
            long max = arr[i] - lower; // [min, max]
            // windowL, windowR 都属于左组， 所以 前提是 都要 <= M
            while (windowL <= M && arr[windowL] < min) {
                windowL++;
            }
            while (windowR <= M && arr[windowR] <= max) {
                windowR++;
            }
            ans += windowR - windowL;
        }

        long[] help = new long[R - L + 1];
        int p1 = L;
        int p2 = M + 1;
        int i = 0;

        while (p1 <= M && p2 <= R) {
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= M) {
            help[i++] = arr[p1++];
        }
        while (p2 <= R) {
            help[i++] = arr[p2++];
        }
        for (i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
        return ans;
    }

}
