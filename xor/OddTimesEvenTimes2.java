package xor;

/**
 * 两个数字为奇数次，其他数字为偶数次
 */
public class OddTimesEvenTimes2 {

    public static void printOddTimesNum(int[] arr) {
        int ans = 0;
        for (int num : arr) {
            ans ^= num;
        }
        // 最右为1的数字
        int mostRight = ans & (-ans);
        int ans1 = 0;
        for (int num : arr) {
            if ((num & mostRight) == 0) {
                ans1 ^= num;
            }
        }
        ans = ans ^ ans1;
        System.out.println(ans + " " + ans1);
    }

    public static void main(String[] args) {
        int[] arr = { 4, 3, 4, 2, 2, 2, 4, 1, 1, 1, 3, 3, 1, 1, 1, 4, 2, 2 };
        printOddTimesNum(arr);
    }
}
