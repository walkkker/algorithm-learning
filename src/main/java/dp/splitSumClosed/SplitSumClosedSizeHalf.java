package dp.splitSumClosed;


public class SplitSumClosedSizeHalf {

    public static int minimumDifference(int[] nums) {
        int N = nums.length;
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (N % 2 == 0) {
            return process(0, sum / 2, N / 2, nums);
        } else {
            return Math.max(process(0, sum / 2, N / 2, nums),
                            process(0, sum / 2, N / 2 + 1, nums));
        }
    }


    public static int process(int index, int rest, int restNums, int[] nums) {
        if (index == nums.length) {
            return rest >= 0 && restNums == 0 ? 0 : Integer.MIN_VALUE;
        }
        // 未到达 结尾
        // 剪枝 在这里  ==>  可变参数要剪枝
        // 第一个可变参数 rest 的负值 范围 的讨论
        if (rest < 0) {
            return Integer.MIN_VALUE;
        }

        if (restNums < 0) {
            return Integer.MIN_VALUE;
        }

        // OK, 此时 index 未到达结尾， rest >= 0, restNums >= 0; >= 0 是 合法的
        int p1 = process(index + 1, rest - nums[index], restNums - 1, nums);
        if (p1 != Integer.MIN_VALUE) {
            p1 += nums[index];
        }

        int p2 = process(index + 1, rest, restNums, nums);
        return Math.max(p1, p2);
    }

    public static int minimumDifference2(int[] nums) {
        int N = nums.length;
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        int[][][] dp = new int[N + 1][sum / 2 + 1][N / 2 + 2];

        for (int rest = 0; rest <= sum / 2; rest++) {
            for (int restNums = 1; restNums <= N / 2 + 1; restNums++) {
                dp[N][rest][restNums] = Integer.MIN_VALUE;
            }
        }

        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= sum / 2; rest++) {
                for (int restNums = 0; restNums <= N / 2 + 1; restNums++) {
                    int p1 = pick(index + 1, rest - nums[index], restNums - 1, dp);
                    if (p1 != Integer.MIN_VALUE) {
                        p1 += nums[index];
                    }
                    int p2 = pick(index + 1, rest, restNums, dp);
                    dp[index][rest][restNums] = Math.max(p1, p2);
                }
            }
        }
        if (N % 2 == 0) {
            return dp[0][sum/2][N/2];
        } else {
            return Math.max(dp[0][sum / 2][N / 2], dp[0][sum / 2][N / 2 + 1]);
        }
    }

    public static int pick(int index, int rest, int restNums, int[][][] dp) {
        if (rest < 0) {
            return Integer.MIN_VALUE;
        }

        if (restNums < 0) {
            return Integer.MIN_VALUE;
        }
        return dp[index][rest][restNums];
    }


    // for test
    public static int[] randomArray(int len, int value) {
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * value);
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 50;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = randomArray(len, maxValue);
            int ans1 = right(arr);
            int ans2 = minimumDifference(arr);
            int ans3 = minimumDifference2(arr);
            if (ans1 != ans2 || ans1 != ans3) {
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }



    public static int right(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        if ((arr.length & 1) == 0) {
            return process(arr, 0, arr.length / 2, sum / 2);
        } else {
            return Math.max(process(arr, 0, arr.length / 2, sum / 2), process(arr, 0, arr.length / 2 + 1, sum / 2));
        }
    }

    // arr[i....]自由选择，挑选的个数一定要是picks个，累加和<=rest, 离rest最近的返回
    public static int process(int[] arr, int i, int picks, int rest) {
        if (i == arr.length) {
            return picks == 0 ? 0 : -1;
        } else {
            int p1 = process(arr, i + 1, picks, rest);
            // 就是要使用arr[i]这个数
            int p2 = -1;
            int next = -1;
            if (arr[i] <= rest) {
                next = process(arr, i + 1, picks - 1, rest - arr[i]);
            }
            if (next != -1) {
                p2 = arr[i] + next;
            }
            return Math.max(p1, p2);
        }
    }

}
