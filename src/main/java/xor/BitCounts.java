package xor;

public class BitCounts {
    public static int bitCounts(int num) {
        int counts = 0;
        int rightOne = 0;
        while (num != 0) {
            rightOne = num & (-num);
            num ^= rightOne;
            counts++;
        }
        return counts;
    }

    public static int bitCounts2(int num) {
        int counts = 0;
        int rightOne = 0;
        while (num != 0) {
            rightOne = num & (-num);
            //num ^= rightOne;
            num &= ~rightOne;
            counts++;
        }
        return counts;
    }

    // 打印二进制形式
    public static void printBinary(int num) {
        for (int i = 31; i >= 0; i--) {
            System.out.print((num >> i) & 1);
        }
        System.out.println();
    }



    public static void main(String[] args) {
        int num = 17;
        printBinary(num);
        System.out.println(bitCounts(num));
        System.out.println(bitCounts2(num));
    }
}
