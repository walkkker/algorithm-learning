package makeCharts;

public class EatGrass {

    public static String whoWins1(int N) {
        if (N == 0) {
            return "后手";
        }
        // 【错误点】
        // for (int want = 1; want * 4 <= N; want *= 4) {
        for (int want = 1; want <= N; want *= 4) {
            if (whoWins1(N - want).equals("后手")) {
                return "先手";
            } else {
                if (want > N / 4) {  // 【很重要】 防止 want * 4 出现 int溢出 （当 N 接近 2 ^ 31 次方时）
                    break;
                }
            }
        }
        return "后手";
    }

    public static String whoWins2(int N) {
        int remainder = N % 5;
        return remainder == 0 || remainder == 2 ? "后手" : "先手";
    }

    public static void main(String[] args) {
        int times = 100;

        for (int i = 0; i < times; i++) {
           if (whoWins1(i) != whoWins2(i)) {
               System.out.println("opps");
           }
        }
    }
}
