package dp;

import java.util.Comparator;

public class KillMonster {

    public static double killMonster(int N, int M, int K) {
        if (N <= 0 || M <= 0 || K <= 0) {
            return 0;
        }
        // 记得 将整数转成 double
        long ans = process1(K, N, M);
//        System.out.println(ans);
        return 1 - (double) ans / Math.pow(M + 1, K);
    }

    public static long process1(int steps, int hp, int M) {
        // 先检查是否越界, 怪兽死亡
//        if (hp <= 0) {
//            return 0;
//        }
        if (steps == 0) {
            return 1;
        }
        //  hp > 0 && steps > 0
        long ans = 0;
        // 不要写 右侧的这个 && 表达式，因为他很特别，他只要求>0（使得依赖不包含0） 而不是 >=0；这使得dp时，只能使用[1, N],而必须特别避免 0这一列。变得容易出错。 我们想要的是，包含整张dp表最好
        for (int killPoint = 0; killPoint <= M && hp - killPoint > 0; killPoint++) {
            ans += process1(steps - 1, hp - killPoint, M);
        }
        return ans;
    }

    // 不要写上面那种递归，
    public static double killMonsterDpPre(int K, int M, int N) {
        if (K < 1 || M < 1 || N < 1) {
            return 0;
        }
        int[][] dp = new int[K + 1][N + 1];
        for (int j = 0; j <= N; j++) {
            dp[0][j] = 1;
        }
        for (int i = 1; i <= K; i++) {
            for (int j = 0; j <= N; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j - 1 > 0) {
                    dp[i][j] += dp[i][j - 1];
                }
                if (j - M - 1 > 0) {
                    dp[i][j] -= dp[i - 1][j - M - 1];
                }
            }
        }
        return 1 - (double) dp[K][N] / Math.pow(M + 1, K);
    }


    public static double killMonsterDp(int K, int M, int N) {
        if (N <= 0 || M <= 0 || K <= 0) {
            return 0;
        }
        int[][] dp = new int[K + 1][N + 1];
        for (int hp = 1; hp <= N; hp++) {
            dp[0][hp] = 1;
        }
        for (int step = 1; step <= K; step++) {
            for (int hp = 1; hp <= N; hp++) {
                dp[step][hp] = dp[step - 1][hp] + dp[step][hp - 1];
                if (hp - (M + 1) >= 0) {
                    dp[step][hp] -= dp[step - 1][hp - (M + 1)];
                }
            }
        }
        return 1 - (double) dp[K][N] / Math.pow(M + 1, K);
    }


    // TEST
    public static double right(int N, int M, int K) {
        if (N < 1 || M < 1 || K < 1) {
            return 0;
        }
        long all = (long) Math.pow(M + 1, K);
        long kill = process(K, M, N);
        return (double) ((double) kill / (double) all);
    }

    // 怪兽还剩hp点血
    // 每次的伤害在[0~M]范围上
    // 还有times次可以砍
    // 返回砍死的情况数！
    public static long process(int times, int M, int hp) {
        if (times == 0) {
            return hp <= 0 ? 1 : 0;
        }
        if (hp <= 0) {
            return (long) Math.pow(M + 1, times);
        }
        long ways = 0;
        for (int i = 0; i <= M; i++) {
            ways += process(times - 1, M, hp - i);
        }
        return ways;
    }


    public static void main(String[] args) {
        DoubleComparator comp = new DoubleComparator();
        int NMax = 10;
        int MMax = 10;
        int KMax = 10;
        int testTime = 200;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * NMax);
            int M = (int) (Math.random() * MMax);
            int K = (int) (Math.random() * KMax);
            double ans1 = right(N, M, K);
            double ans2 = killMonster(N, M, K);
            double ans3 = killMonsterDpPre(K, M, N); // 注意这里参数是反的
            if (comp.compare(ans1, ans2) != 0 || comp.compare(ans2, ans3) != 0) {
                System.out.println("Oops!");
                System.out.println(ans1 + " " + ans2 + " " + ans3);
                break;
            }
        }
        System.out.println("测试结束");
    }


    public static class DoubleComparator implements Comparator<Double> {
        public int compare(Double o1, Double o2) {
            if (Math.abs(o1 - o2) < 0.000001) {
                return 0;
            } else {
                return o1 < o2 ? -1 : 1;
            }
        }
    }




    public static double dp2(int N, int M, int K) {
        if (N < 1 || M < 1 || K < 1) {
            return 0;
        }
        long all = (long) Math.pow(M + 1, K);
        long[][] dp = new long[K + 1][N + 1];
        dp[0][0] = 1;
        for (int times = 1; times <= K; times++) {
            dp[times][0] = (long) Math.pow(M + 1, times);
            for (int hp = 1; hp <= N; hp++) {
                dp[times][hp] = dp[times][hp - 1] + dp[times - 1][hp];
                if (hp - 1 - M >= 0) {
                    dp[times][hp] -= dp[times - 1][hp - 1 - M];
                } else {
                    dp[times][hp] -= Math.pow(M + 1, times - 1);
                }
            }
        }
        long kill = dp[K][N];
        return (double) ((double) kill / (double) all);
    }
}

