package dp.coinsSeries;

/**
 * 背包问题
 */
public class CoinsWayEveryPaperDifferent {


    // 注意数组长度为0时，也可能满足情况。 此时 aim = 0；
    public static int ways1(int[] arr, int aim) {
        if (arr == null) {
            return 0;
        }
        return process(0, aim, arr);
    }

    public static int process(int index, int rest, int[] arr) {
        if (rest == 0) {
            return 1;
        }
        if (rest < 0) {
            return 0;
        }
        if (index == arr.length) {
            return 0;
        }

        // pick / not pick
        int pick = process(index + 1, rest - arr[index], arr);
        int noPick = process(index + 1, rest, arr);

        return pick + noPick;
    }


    public static int waysDp(int[] arr, int aim) {
        if (arr == null) {
            return 0;
        }
        int N = arr.length;
        int[] dp = new int[aim + 1];
        dp[0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            dp[0] = 1;
            for (int rest = aim; rest >= 1; rest--) {
                if (rest - arr[index] >= 0) {
                    dp[rest] += dp[rest - arr[index]];
                }
            }
        }
        return dp[aim];
    }


    // 为了测试
    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen);
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
        }
        return arr;
    }

    // 为了测试
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // 为了测试
    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = ways1(arr, aim);
            int ans2 = waysDp(arr, aim);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("测试结束");
    }



}
