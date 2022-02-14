package catalan;


/**
 * 使用公式 k(n) = c(2n, n) / (n + 1)
 */
public class CatalanNumber {

    // 注意使用 long 类型
    public static long catalanNum(int N) {
        if (N < 0) {
            return 0;
        }
        if (N < 2) {
            return 1;
        }

        long a = 1;
        long b = 1;

        for (int i = 1, j = N + 1; i <= N; i++, j++) {
            a *= i;
            b *= j;

            long gcd = gcd(a, b);

            a /= gcd;
            b /= gcd;
        }

        return (b / a) / (N + 1);

    }

    // 功能函数 -> 求最大公约数
    public static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
