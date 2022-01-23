package dp;

public class ConvertToLetterString {

    public int translateNum(int num) {
        String number = String.valueOf(num);
        return process(0, number.toCharArray());
    }

    // 返回 组合数目
    // 含义： 返回从 str 的 i...结尾 位置上的组合数目
    public static int process(int i, char[] str) {
        if (i == str.length) {
            return 1;
            // 这里的 base case 返回值好好思考一下意思。 大体上就是 前边的组合都完成了，那么到了结尾，所以返回1
            // 还有一种选择，就是 当i==str.length的时候，没有字符可以组成了，这是可以确定 空字符只能组成一种。
        }
        // 不在末尾位置的话,就分解子问题
        if (str[i] == '1') {
            int p1 = process(i + 1, str);
            int p2 = 0;
            if (i + 1 < str.length) {
                p2 = process(i + 2, str);
            }
            return p1 + p2;
        }

        if (str[i] == '2') {
            int p1 = process(i + 1, str);
            int p2 = 0;
            if (i + 1 < str.length && str[i + 1] >= '0' && str[i + 1] <= '5') {
                p2 = process(i + 2, str);
            }
            return p1 + p2;
        }
        return process(i + 1, str);
    }

    public int translateNum2(int num) {
        String number = String.valueOf(num);
        char[] str = number.toCharArray();
        int N = str.length;
        int[] dp = new int[N + 1];
        dp[N] = 1;

        for (int i = N - 1; i >= 0; i-- ){
            if (str[i] == '1') {
                int p1 = dp[i + 1];
                int p2 = 0;
                if (i + 1 < str.length) {
                    p2 = dp[i + 2];
                }
                dp[i] = p1 + p2;
            } else if (str[i] == '2') {
                int p1 = dp[i + 1];
                int p2 = 0;
                if (i + 1 < str.length && str[i + 1] >= '0' && str[i + 1] <= '5') {
                    p2 = dp[i + 2];
                }
                dp[i] = p1 + p2;
            } else {
                dp[i] =  dp[i + 1];
            }
        }
        return dp[0];
    }



}
