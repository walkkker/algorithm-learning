package slideWindow;

import java.util.*;

public class GasStation {

    public int canCompleteCircuit(int[] gas, int[] cost) {
        int N = gas.length;
        int[] preSum = new int[2 * N];
        preSum[0] = gas[0] - cost[0];
        for (int i = 1; i < preSum.length; i++) {
            preSum[i] = preSum[i - 1] + (gas[i % N] - cost[i % N]);
        }

        Deque<Integer> qmin = new LinkedList<>();
        // window.len == N
        // 转变成了 固定大小的 滑动窗口问题
        // 首先确定几个变量
        // [L, R] == N     ==>    L = R + 1 - N
        // 窗口大小 N
        for (int R = 0; R <= 2 * N - 2; R++) {
            // R 右移动
            int L = R - N + 1;
            while (!qmin.isEmpty() && preSum[qmin.peekLast()] >= preSum[R]) {
                qmin.pollLast();
            }
            qmin.addLast(R);

            // R 向右移动时， L也需要向右移动  ==》 (L, R) 为 固定窗口
            if (L - 1 == qmin.peekFirst()) {
                qmin.pollFirst();
            }

            // 此时，滑动窗口与L,R 正确对应, 从而可以 使用 滑动窗口的最小值结构来进行 判断

            // 这个地方老错，老忘记
            // 记住，使用的时候，都是使用的下标，所以要转换成数组的使用方式
            //if (qmin.peekFirst() >= 0) {
            // 还要注意 L >= 0的条件，并列； 因为 滑动窗口成长期就可能出现 >= 0的情况，但是此时 固定的滑动窗口并没有成型， 不能算作判断条件。 必须当 L>=0 时，（L,R）所组成的滑动窗口才属于真正成型状态。 当 L>=0时，每一次移动滑动窗口都可以得到 对应的 最小值。
            // 改后如下依然错了
            // if (L >= 0 && preSum[qmin.peekFirst()] >= 0) {
            //     return L;
            // }
            // 【大错误点】这里还有一个非常重要的问题，就是 对于每一个滑动窗口，它实际对应的值 是要将当前 窗口内的数组每个值 减去 （L-1） 位置的值！！！！才代表从L出发的真实的累加和
            if (L == 0 && preSum[qmin.peekFirst()] >= 0) {
                return L;
            }
            if (L > 0 && (preSum[qmin.peekFirst()] - preSum[L - 1]) >= 0) {
                return L;
            }
        }
        return -1;
    }

}
