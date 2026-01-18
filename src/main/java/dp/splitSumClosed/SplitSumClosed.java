package dp.splitSumClosed;

public class SplitSumClosed {

    public static int splitSumClosed(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        int ans1 = process(0, sum / 2, arr);
        return ans1;
    }

    // 返回最逼近rest的值, <= rest
    public static int process(int index, int rest, int[] arr) {

        // 背包问题，base case 全跑完到最后位置，然后选择出 符合要求的结果
        // 因为到达了结尾位置，我前面跑出了 所有的 排列组合。 使用三目表达式 对最终答案进行 分支讨论。
        // if rest >= 0; 这个组合是满足条件的,正常返回（当前没有值可选取，返回0）；   if rest < 0 ==> 这个组合是不满足条件的
        if (index == arr.length) {
            return rest >= 0 ? 0 : Integer.MIN_VALUE;
        }

        if (rest < 0) {
            return Integer.MIN_VALUE;
        }

        // 此时，未到达终点， 进行 【要和不要】 【分支】 【计算】
        int p1 = process(index + 1, rest - arr[index], arr);
        if (p1 != Integer.MIN_VALUE) {
            p1 =  arr[index] + p1;
        }


        int p2 = process(index + 1, rest, arr);

        return Math.max(p1, p2);
    }


    public static int splitSumClosed1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        int[] dp = new int[sum / 2 + 1];
        int tmp = 0;
        int ans = 0;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = dp.length - 1; rest >= 0; rest--) {
                if (rest - arr[index] >= 0) {
                    dp[rest] = Math.max(dp[rest], dp[rest - arr[index]] + arr[index]);
                }
            }
        }
        return dp[sum / 2];
    }



//        for (int index = N - 1; index >= 0; index--) {
//        for (int rest = 0; rest < arr[index]; rest++) {
//            for (int i = rest; i < sum / 2 + 1; i = i + arr[index]) {
//                ans = dp[i];
//                if (i - arr[index] >= 0) {
//                    ans = Math.max(ans, tmp + arr[index]);
//                }
//                tmp = dp[i];
//                dp[i] = ans;
//            }
//        }
////    }


    public static int[] randomArray(int len, int value) {
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * value);
        }
        return arr;
    }

    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 50;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = randomArray(len, maxValue);
            int ans1 = right(arr);
            int ans2 = splitSumClosed1(arr);
            if (ans1 != ans2) {
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
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
        return process(arr, 0, sum / 2);
    }

    // arr[i...]可以自由选择，请返回累加和尽量接近rest，但不能超过rest的情况下，最接近的累加和是多少？
    public static int process(int[] arr, int i, int rest) {
        if (i == arr.length) {
            return 0;
        } else { // 还有数，arr[i]这个数
            // 可能性1，不使用arr[i]
            int p1 = process(arr, i + 1, rest);
            // 可能性2，要使用arr[i]
            int p2 = 0;
            if (arr[i] <= rest) {
                p2 = arr[i] + process(arr, i + 1, rest - arr[i]);
            }
            return Math.max(p1, p2);
        }
    }

}
