package dp;

/**
 * N - (1, N)个位置
 * start 起始位置
 * aim 目标位置
 * K 总共给予的步数
 */
public class RobotWalk {

    // 首先完成 【从左至右的尝试】递归方法
    public static int ways1 (int N, int start, int aim, int K) {
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 0) {
            return -1;
        }

        return process(start, aim, K, N);
    }

    // 参数含义： 当前在【cur】位置上，目标是【aim】，还剩【rest】步，活动范围是 【1 ~ n】，
    // 函数含义： 返回从 cur 到达 aim 的所有方法数量。
    // 分解子问题： cur移动1步位置， 子问题为 (newCur, aim, rest - 1, N), 实现从左至右的尝试模型。
    // 因为要做动态规划，所以 首先确定 可变参数： int cur, int rest
    public static int process(int cur, int aim, int rest, int N) {
        // 这个地方不应该是 return 0；
        // 当到达 cur 位置时，返回 还剩rest步时候的方法数，
        // 这样就意味着，当rest==0时，方法数目可以确定
        // 之前是因为 不能确定，所以递归求解
        // 当到达 base case时刻，要认真思考 process递归函数的含义，返回 base case情况下的值，满足Process的含义。
        // 所以当 rest==0时，cur无法走动，此时方法数为 cur == aim ? 1 : 0;
        // 返回1的意思是，【当rest==0情况下，我们可以直接求得方法数，所以rest==0为base case】
        // 那么 rest==0 的 情况下，方法数是多少呢？ 如果 cur==aim,【说明有一种方法，即不走动的情况从cur下到达aim。所以返回1】；否则无法从cur到达aim，所以返回0
        if (rest == 0) {
            return cur == aim ? 1 : 0;
        }
        int ans = 0;
        if (cur == 1) {
            ans = process(cur + 1, aim, rest - 1, N);
        } else if (cur == N) {
            ans = process(cur - 1, aim, rest - 1, N);
        } else {
            ans = process(cur - 1, aim, rest - 1, N)
                    + process(cur + 1, aim , rest - 1, N);
        }
        return ans;
    }


    // 由暴力递归改动态规划 -> 中间省略 记忆化搜索（记忆化搜索很简单，根据可变参数的范围建立dp表，然后每次递归时先去dp表里看有没有记录）
    // 有的话，直接取缓存；没有的话，正常进行递归，但是记得在返回之前，将 本次递归（一对 可变参数的组合）的答案 存到 缓存表里面

    public static int dpWays(int N, int start, int aim, int K) {
        // 边界条件 corner condition
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 0) {
            return -1;
        }
        // 1.首先确定 可变参数有哪些？ cur, rest
        // 2. 依次确定可3变参数的范围： 【1】cur的范围[1 , N] -> [0, N]  【2】rest范围：[0, K]
        int[][] dp = new int[N + 1][K + 1];
        dp[aim][0] = 1;
        for (int rest = 1; rest <= K; rest++) {
            for (int cur = 1; cur <= N; cur++) {
                if (cur == 1) {
                    dp[cur][rest] = dp[cur + 1][rest - 1];
                } else if (cur == N) {
                    dp[cur][rest] = dp[cur - 1][rest - 1];
                } else {
                    dp[cur][rest] = dp[cur - 1][rest - 1] + dp[cur + 1][rest - 1];
                }
            }
        }
        return dp[start][K];
    }

    // 优化版本
    // 进一步空间压缩 将空间复杂度由 O（N*K） 变成 O（N）；
    public static int dpWaysPro(int N, int start, int aim, int K) {
        // 边界条件 corner condition
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 0) {
            return -1;
        }
        // 1.首先确定 可变参数有哪些？ cur, rest
        // 2. 依次确定可3变参数的范围： 【1】cur的范围[1 , N] -> [0, N]  【2】rest范围：[0, K]
        int[] dp = new int[N + 1];
        dp[aim] = 1;
        int tmp1 = 0, tmp2 = 0;
        for (int rest = 1; rest <= K; rest++) {
            for (int cur = 1; cur <= N; cur++) {
                tmp1 = dp[cur];
                if (cur == 1) {
                    dp[cur] = dp[cur + 1];
                } else if (cur == N) {
                    dp[cur] = tmp2;
                } else {
                    dp[cur] = tmp2 + dp[cur + 1];
                }
                tmp2 = tmp1;
            }
        }
        return dp[start];
    }

    public static void main(String[] args) {
        System.out.println(ways1(5, 2, 4, 6));
        System.out.println(dpWays(5,2,4,6));
        System.out.println(dpWaysPro(5,2,4,6));
    }

}
