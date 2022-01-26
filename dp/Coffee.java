package dp;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

// 题目
// 数组arr代表每一个咖啡机冲一杯咖啡的时间，每个咖啡机只能串行的制造咖啡。
// 现在有n个人需要喝咖啡，只能用咖啡机来制造咖啡。
// 认为每个人喝咖啡的时间非常短，冲好的时间即是喝完的时间。
// 每个人喝完之后咖啡杯可以选择洗或者自然挥发干净，只有一台洗咖啡杯的机器，只能串行的洗咖啡杯。
// 洗杯子的机器洗完一个杯子时间为a，任何一个杯子自然挥发干净的时间为b。
// 四个参数：arr, n, a, b
// 假设时间点从0开始，返回所有人喝完咖啡并洗完咖啡杯的全部过程结束后，至少来到什么时间点。
public class Coffee {

    /*
        int[] arr -> 每一个咖啡机冲一杯咖啡的时间
        n -> n个人
        wash -> 洗碗机 洗一杯咖啡的时间
        air -> 咖啡杯挥发的时间

        该问题实际包含两个小问题： 【1】求出每个人最早喝完咖啡的时间   【2】在【1】的基础上，最早洗完咖啡的时间点
     */
    public static int minTime1(int[] arr, int n, int wash, int air) {
        PriorityQueue<Machine> heap = new PriorityQueue<>(new MachineComparator());
        for (int time : arr) {
            heap.add(new Machine(0, time));
        }

        int[] drinks = new int[n];

        for (int i = 0; i < n; i++) {
            Machine cur = heap.poll();
            drinks[i] = cur.timePoint + cur.workTime;
            cur.timePoint = drinks[i];
            heap.offer(cur);
        }
        return process(drinks, 0, 0, wash, air);
    }


    public static class Machine{
        int timePoint;
        int workTime;

        public Machine(int t, int w) {
            timePoint = t;
            workTime = w;
        }
    }

    public static class MachineComparator implements Comparator<Machine> {
        public int compare(Machine o1, Machine o2) {
            return (o1.timePoint + o1.workTime) - (o2.timePoint + o2.workTime);
        }
    }

    public static int process(int[] drinks, int index, int timeLine, int wash, int air) {
        if (index == drinks.length) {
            return 0;
        }

        // index wash
        int p1 = Math.max(drinks[index], timeLine) + wash;
        int p2 = process(drinks, index + 1, p1, wash, air);
        int ans1 = Math.max(p1, p2);

        // index air
        int p3 = drinks[index] + air;
        int p4 = process(drinks, index + 1, timeLine, wash, air);
        int ans2 = Math.max(p3, p4);

        return Math.min(ans1, ans2);
    }


    // DP method
    public static int minTime2(int[] arr, int n, int wash, int air) {
        PriorityQueue<Machine> heap = new PriorityQueue<>(new MachineComparator());
        for (int time : arr) {
            heap.add(new Machine(0, time));
        }

        int[] drinks = new int[n];

        for (int i = 0; i < n; i++) {
            Machine cur = heap.poll();
            drinks[i] = cur.timePoint + cur.workTime;
            cur.timePoint = drinks[i];
            heap.offer(cur);
        }

        int maxTimeLine = 0;
        // 求出最差timeLine的情况， timeLine不可能再变更大了。 如果使用的是，有air，那么 最终的timeLine之后 <= maxTimeLine
        for (int i = 0; i < n; i++) {
            maxTimeLine = Math.max(maxTimeLine, drinks[i]) + wash;
        }

        int[][] dp = new int[n + 1][maxTimeLine + 1];

        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j <= maxTimeLine; j++) {
                int selfClean1 = Math.max(drinks[i], j) + wash;
                // 很重要很难理解： 不存在 j > maxTimeLine的可能性，所以对应这样依赖的点，也不需要考虑
                if (selfClean1 > maxTimeLine) {
                    continue;
                }
                int restClean1 = dp[i + 1][selfClean1];
                int ans1 = Math.max(selfClean1, restClean1);


                int selfClean2 = drinks[i] + air;
                int restClean2 = dp[i + 1][j];
                int ans2 = Math.max(selfClean2, restClean2);

                dp[i][j] = Math.min(ans1, ans2);
            }
        }
        return dp[0][0];
    }


//        下面这样写也可以，就是判断无效值，然后不让无效值参与计算就可以。 但是常数时间比较长
//        int[][] dp = new int[n + 1][maxTimeLine + 1];
//
//        for (int i = n - 1; i >= 0; i--) {
//            for (int j = 0; j <= maxTimeLine; j++) {
//                int selfClean1 = Math.max(drinks[i], j) + wash;
//                // 很重要很难理解： 不存在 j > maxTimeLine的可能性，所以对应这样依赖的点，也不需要考虑
//                int restClean1;
//                if (selfClean1 > maxTimeLine) {
//                    restClean1 = Integer.MIN_VALUE;
//                } else {
//                    restClean1 = dp[i + 1][selfClean1];
//                }
//                int ans1 = Math.max(selfClean1, restClean1);
//
//
//                int selfClean2 = drinks[i] + air;
//                int restClean2 = dp[i + 1][j];
//                int ans2 = Math.max(selfClean2, restClean2);
//
//                dp[i][j] = Math.min(ans1, ans2);
//            }
//        }
//        return dp[0][0];
//    }


    // TEST
    public static int right(int[] arr, int n, int a, int b) {
        int[] times = new int[arr.length];
        int[] drink = new int[n];
        return forceMake(arr, times, 0, drink, n, a, b);
    }

    // 每个人暴力尝试用每一个咖啡机给自己做咖啡
    public static int forceMake(int[] arr, int[] times, int kth, int[] drink, int n, int a, int b) {
        if (kth == n) {
            int[] drinkSorted = Arrays.copyOf(drink, kth);
            Arrays.sort(drinkSorted);
            return forceWash(drinkSorted, a, b, 0, 0, 0);
        }
        int time = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            int work = arr[i];
            int pre = times[i];
            drink[kth] = pre + work;
            times[i] = pre + work;
            time = Math.min(time, forceMake(arr, times, kth + 1, drink, n, a, b));
            drink[kth] = 0;
            times[i] = pre;
        }
        return time;
    }

    public static int forceWash(int[] drinks, int a, int b, int index, int washLine, int time) {
        if (index == drinks.length) {
            return time;
        }
        // 选择一：当前index号咖啡杯，选择用洗咖啡机刷干净
        int wash = Math.max(drinks[index], washLine) + a;
        int ans1 = forceWash(drinks, a, b, index + 1, wash, Math.max(wash, time));

        // 选择二：当前index号咖啡杯，选择自然挥发
        int dry = drinks[index] + b;
        int ans2 = forceWash(drinks, a, b, index + 1, washLine, Math.max(dry, time));
        return Math.min(ans1, ans2);
    }


    // for test
    public static int[] randomArray(int len, int max) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * max) + 1;
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        System.out.print("arr : ");
        for (int j = 0; j < arr.length; j++) {
            System.out.print(arr[j] + ", ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int len = 10;
        int max = 10;
        int testTime = 10;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(len, max);
            int n = (int) (Math.random() * 7) + 1;
            int a = (int) (Math.random() * 7) + 1;
            int b = (int) (Math.random() * 10) + 1;
            int ans1 = right(arr, n, a, b);
            int ans2 = minTime1(arr, n, a, b);
            int ans3 = minTime2(arr, n, a, b);
            if (ans1 != ans2 || ans2 != ans3) {
                printArray(arr);
                System.out.println("n : " + n);
                System.out.println("a : " + a);
                System.out.println("b : " + b);
                System.out.println(ans1 + " , " + ans2 + " , " + ans3);
                System.out.println("===============");
                break;
            }
        }
        System.out.println("测试结束");

    }

}
