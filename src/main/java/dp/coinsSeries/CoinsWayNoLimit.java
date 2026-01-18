package dp.coinsSeries;

public class CoinsWayNoLimit {

    public static int ways(int[] arr, int aim) {
        if (arr == null) {
            return 0;
        }
        return process(0, aim, arr);
    }

    public static int process(int index, int rest,int[] arr) {
        if (index == arr.length) {
            return rest == 0 ? 1 : 0;
        }
        // 0 ~ k pieces
        int ans = 0;
        // 【错误点】，这里 要使用 i * arr[index] <= rest, 当初错误的时候 没有加 = 号。
        // 因为这里要求的是 不要超过rest，也就是说不要>REST,也就是说<=rest
        for (int i = 0; i * arr[index] <= rest; i++) {
            ans += process(index + 1, rest - i * arr[index], arr);
        }
        return ans;
    }


    public static int waysDp(int[] arr, int aim) {
        if (arr == null) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = aim; rest >= 0; rest--) {
                for (int i = 0; i * arr[index] <= rest; i++) {
                    dp[index][rest] += dp[index + 1][rest - i * arr[index]];
                }
            }
        }
       return dp[0][aim];
    }


    // 特别注意，状态压缩之后，有可能，随着位置依赖的改变， dp表的更新方向也会发生改变。不然会出错！！！
    public static int waysDp1(int[] arr, int aim) {
        if (arr == null) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                if (rest - arr[index] >= 0) {
                    dp[index][rest] += dp[index][rest - arr[index]];
                }
            }
        }
        return dp[0][aim];
    }


    // 单纯空间压缩时，不需要改变 for 循环顺序
    public static int waysDp2(int[] arr, int aim) {
        if (arr == null) {
            return 0;
        }
        int N = arr.length;
        int[] dp = new int[aim + 1];
        dp[0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                if (rest - arr[index] >= 0) {
                    dp[rest] += dp[rest - arr[index]];
                }
            }
        }
        return dp[aim];
    }



}
