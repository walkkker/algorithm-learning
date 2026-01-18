package makeCharts;

public class AppleMinBags {

    public static int minNums(int N) {
        int minBags = Integer.MAX_VALUE;
        for (int i = 0; i * 8 <= N; i++) {
            int remain = N - i * 8;
            if (remain % 6 == 0) {
                minBags = Math.min(minBags, (i + remain / 6));
            }
        }
        if (minBags == Integer.MAX_VALUE) {
            return -1;
        } else {
            return minBags;
        }
    }


    public static int optimalMethod(int N) {
        if (N <= 17) {
            if (N == 0) {
                return 0;
            } else if (N == 6 || N == 8) {
                return 1;
            } else if (N == 12 || N == 14 || N == 16) {
                return 2;
            } else {
                return -1;
            }
        }
        if ((N & 1) == 1) {
            return -1;
        }
        int remain = N - 18;
        return 3 + (remain / 8);
    }


    public static void main(String[] args) {
        int times = 1000000;
        for (int i = 0; i < times; i++) {
            if(minNums(i) != optimalMethod(i)) {
                System.out.println(i);
                System.out.println("opps");
            }
//            System.out.println(i + ": " + minNums(i));
        }
    }
}
