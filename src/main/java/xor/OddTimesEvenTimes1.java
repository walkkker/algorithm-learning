package xor;

/**
 * 一个数字为奇数次，其他数字为偶数次
 */
public class OddTimesEvenTimes1 {
    public static int printOddTimesNum(int[] arr) {
        int ans = 0;
        for (int num : arr) {
            ans ^= num;
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr1 = {1,1,1,2,3,4,5,6,2,3,4,5,6,1,9};
        System.out.println(printOddTimesNum(arr1));
        int[] arr2 = { 3, 3, 2, 3, 1, 1, 1, 3, 1, 1, 1 };
        System.out.println(printOddTimesNum(arr2));
    }

}
