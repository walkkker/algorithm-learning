package slideWindow;

import java.util.LinkedList;

// 双端队列实现
public class SlidingWindowMaxArray {

    // w 为窗口大小
    public static int[] getMaxWindow(int[] arr, int w) {
        if (arr == null || w < 1 || w > arr.length) {
            return null;
        }

        // qmax -> 窗口最大值的更新结构
        // 【里面存的是下标】, L右移时， 检查下标是否是过期
        LinkedList<Integer> qmax = new LinkedList<>();
        int N = arr.length;

        int[] ans = new int[N - w + 1];   // [0, end] 求end: [end, N) len = w  ==> end = N - w  ==> 所以 [0, end]的长度为 => N - w + 1
        int index = 0;   // 依次将 0位置的数字填好，1位置的数字填好....

        // R 表示 窗口右边界， R不断右移
        for (int R = 0; R < N; R++) {
            // 【STEP1】 右进
            // R 右移， arr[R] 入 qmax， 先看是否window中是否要弹出数字
            while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[R]) {
                qmax.pollLast();
            }
            qmax.offerLast(R);

            // 【STEP2】 左出
            // 求左侧位置
            // 这个时候谁过期了 ？  [L, R]  -> w + 1   ===> R - L == w
            // 所以此时 L == R - w
            // 有可能 L 是负数， 那么无所谓  ==》 因为 L ！= qmax.peekFirst(), 所以不会影响 窗口，不会有元素弹出
            if (qmax.peekFirst() == R - w) {
                qmax.pollFirst();
            }

            // 此时 窗口只有两种可能 ---> [1] 未形成完全窗口  [2]已经形成完全窗口
            // 往 ans数组 中加入答案
            // 什么时候可以加入答案呢？ [0, R] == w 开始，就可以每次右移窗口的时候加入答案了。
            if (R >= w - 1) {
                ans[index++] = arr[qmax.peekFirst()];
            }
        }
        return ans;
    }

}
