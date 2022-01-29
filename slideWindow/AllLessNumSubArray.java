package slideWindow;

import java.util.Deque;
import java.util.LinkedList;

public class AllLessNumSubArray {

    public static int subArray(int[] nums, int target) {
        int ans = 0;
        int N = nums.length;
        int R = 0;    // 待插入的位置（操作: 先插入，后更新）。（也就是说，我一旦把它插入了，就里面更新）   [L, R)  已加入qmax, qmin，但是不满足条件的位置
        // 窗口的最大值更新结构
        Deque<Integer> qmax = new LinkedList<>();
        // 窗口的最小值更新结构
        Deque<Integer> qmin = new LinkedList<>();


        // R 是 先插入，后更新
        for (int L = 0; L < N; L++) {
            // 有效才往后走， R的位置标记无效
           while (R < N) {
              while (!qmax.isEmpty() && nums[qmax.peekLast()] <= nums[R]) {
                  qmax.pollLast();
              }
              qmax.offerLast(R);
              while (!qmin.isEmpty() && nums[qmin.peekLast()] >= nums[R]) {
                  qmin.pollLast();
              }
              qmin.offerLast(R);
              if (nums[qmax.peekFirst()] - nums[qmin.peekFirst()] <= target) {
                  R++;
              } else {
                  break;
              }
           }
            // 退出时的R位置， 在 max - min不满足 的 第一个 位置
            // [L, R) 是 以 L开头的 最长满足条件的子 数组。
            // 以L为开头， 以 [L, R) 为结尾的 数组 为 当前求得的以L为开头的 子数组
            ans += R - L;

            // 为下一轮 缩小L ==> L 向右移动 一位
            // 每次R右移动，qmax一定有数字，因为 cur 本身数字至少是满足条件的
            if (L == qmax.peekFirst()) {
                qmax.pollFirst();
            }
            if (L == qmin.peekFirst()) {
                qmin.pollFirst();
            }
        }
        return ans;
    }




    public static int subArray2(int[] nums, int target) {
        int ans = 0;
        int N = nums.length;
        int R = -1;    // 待插入的位置（操作: 先插入，后更新）。（也就是说，我一旦把它插入了，就里面更新）   [L, R)  已加入qmax, qmin，但是不满足条件的位置
        // 窗口的最大值更新结构
        Deque<Integer> qmax = new LinkedList<>();
        // 窗口的最小值更新结构
        Deque<Integer> qmin = new LinkedList<>();

        // R 是 先插入，后更新
        for (int L = 0; L < N; L++) {
            // 有效才往后走， R的位置标记无效
            while ((qmax.isEmpty() || nums[qmax.peekFirst()] - nums[qmin.peekFirst()] <= target) && R < N) {
                R++;   // 因为先R++了， 所以R的初始值 要设为 -1
                if (R == N) {
                    break;
                }
                while (!qmax.isEmpty() && nums[qmax.peekLast()] <= nums[R]) {
                    qmax.pollLast();
                }
                qmax.offerLast(R);
                while (!qmin.isEmpty() && nums[qmin.peekLast()] >= nums[R]) {
                    qmin.pollLast();
                }
                qmin.addLast(R);
            }
            // 退出时的R位置， 在 max - min不满足 的 第一个 位置
            // [L, R) 是 以 L开头的 最长满足条件的子 数组。
            // 以L为开头， 以 [L, R) 为结尾的 数组 为 当前求得的以L为开头的 子数组
            ans += R - L;

            // 为下一轮 缩小L ==> L 向右移动 一位
            // 每次R右移动，qmax一定有数字，因为 cur 本身数字至少是满足条件的
            if (L == qmax.peekFirst()) {
                qmax.pollFirst();
            }
            if (L == qmin.peekFirst()) {
                qmin.pollFirst();
            }
        }
        return ans;
    }

    // for test
    public static int[] generateRandomArray(int maxLen, int maxValue) {
        int len = (int) (Math.random() * (maxLen + 1));
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int maxLen = 100;
        int maxValue = 200;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxLen, maxValue);
            int sum = (int) (Math.random() * (maxValue + 1));
            int ans1 = right(arr, sum);
            int ans2 = subArray2(arr, sum);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(sum);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("测试结束");

    }

    // 暴力的对数器方法
    public static int right(int[] arr, int sum) {
        if (arr == null || arr.length == 0 || sum < 0) {
            return 0;
        }
        int N = arr.length;
        int count = 0;
        for (int L = 0; L < N; L++) {
            for (int R = L; R < N; R++) {
                int max = arr[L];
                int min = arr[L];
                for (int i = L + 1; i <= R; i++) {
                    max = Math.max(max, arr[i]);
                    min = Math.min(min, arr[i]);
                }
                if (max - min <= sum) {
                    count++;
                }
            }
        }
        return count;
    }


}
