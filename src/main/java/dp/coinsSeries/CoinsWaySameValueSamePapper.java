package dp.coinsSeries;

import java.util.HashMap;
import java.util.Map.Entry;

public class CoinsWaySameValueSamePapper {

    public static class Info {
        int[] coins;
        int[] nums;

        public Info(int[] c, int[] n) {
            coins = c;
            nums = n;
        }
    }

    public static Info getInfo(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : arr) {
            if (!map.containsKey(num)) {
                map.put(num, 1);
            } else {
                map.put(num, map.get(num) + 1);
            }
        }
        int size = map.size();
        int[] coins = new int[size];
        int[] nums = new int[size];
        int index = 0;
        for (Entry<Integer, Integer> entry : map.entrySet()) {
            coins[index] = entry.getKey();
            nums[index] = entry.getValue();
            index++;
        }
        return new Info(coins, nums);
    }

    public static int ways1(int[] arr, int aim) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] nums = info.nums;
        return process(0, aim, coins, nums);
    }

    public static int process(int index, int rest, int[] coins, int[] nums) {
        if (index == coins.length) {
            return rest == 0 ? 1 : 0;
        }

        int ans = 0;
        for (int i = 0; i <= nums[index] && i * coins[index] <= rest; i++) {
            ans += process(index + 1, rest - i * coins[index], coins, nums);
        }
        return ans;
    }

    public static int waysDp(int[] arr, int aim) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] nums = info.nums;
        int N = coins.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                for (int i = 0; i <= nums[index] && i * coins[index] <= rest; i++) {
                    dp[index][rest] += dp[index + 1][rest - i * coins[index]];
                }
            }
        }
        return dp[0][aim];
    }


    public static int waysDpPro(int[] arr, int aim) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] nums = info.nums;
        int N = coins.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            // 【错误点1】：状态压缩后 -》 没有更改 遍历的顺序，从而导致错误。 【改正】斜率优化后，要重新查看遍历的顺序是否正确，防止依赖了未更新的数据（主要是同index行的顺序）。
            for (int rest = 0; rest <= aim; rest++) {
                if (rest - coins[index] >= 0) {
                    if ((rest - (nums[index] + 1) * coins[index]) >= 0) {
                        dp[index][rest] = dp[index + 1][rest] + dp[index][rest - coins[index]] - dp[index + 1][rest - (nums[index] + 1) * coins[index]];
                    } else {
                        dp[index][rest] = dp[index + 1][rest] + dp[index][rest - coins[index]];
                    }
                } else { // 【错误点，很久】不要漏掉了无左侧元素的情况： 二维dp表，即便 dp[index][rest]无左侧依赖，由于它的值默认是0，此时依然需要 下方依赖，所以一定要记得更新值！！
                    dp[index][rest] = dp[index + 1][rest];
                }
            }
        }
        return dp[0][aim];
    }

    // 还是上面的版本，稍微改进了一下 代码
    public static int waysDpPro1(int[] arr, int aim) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] nums = info.nums;
        int N = coins.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            // 【错误点1】：状态压缩后 -》 没有更改 遍历的顺序，从而导致错误。 【改正】斜率优化后，要重新查看遍历的顺序是否正确，防止依赖了未更新的数据（主要是同index行的顺序）。
            for (int rest = 0; rest <= aim; rest++) {
                // 因为 必 依赖下边，所以初始化下边
                dp[index][rest] = dp[index + 1][rest];

                // 接着， 查看 是否 左侧元素 存在，存在的话就加上
                if (rest - coins[index] >= 0) {
                    dp[index][rest] += dp[index][rest - coins[index]];
                }
                // 最后，查看 是否 前一个元素的最左依赖存在，经分析： 【1】如果不存在的话，目前结果不需要处理； 【2】如果存在的话，则减去
                if ((rest - (nums[index] + 1) * coins[index]) >= 0) {
                    dp[index][rest] -= dp[index + 1][rest - (nums[index] + 1) * coins[index]];
                }
            }
        }
        return dp[0][aim];
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
        int maxLen = 10;
        int maxValue = 20;
        int testTime = 1000000;
        int successTimes = 0;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = ways1(arr, aim);
            int ans2 = waysDpPro1(arr, aim);
            int ans3 = dp2(arr, aim);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
                System.out.println("SuccessTimes: " + successTimes + "/" + testTime);
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                break;
            }
            successTimes++;
        }
        System.out.println("测试结束");
    }


    public static int dp2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] zhangs = info.nums;
        int N = coins.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                if (rest - coins[index] >= 0) {
                    dp[index][rest] += dp[index][rest - coins[index]];
                }
                if (rest - coins[index] * (zhangs[index] + 1) >= 0) {
                    dp[index][rest] -= dp[index + 1][rest - coins[index] * (zhangs[index] + 1)];
                }
            }
        }
        return dp[0][aim];
    }

}
