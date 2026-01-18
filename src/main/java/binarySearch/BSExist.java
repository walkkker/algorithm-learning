package binarySearch;

import java.util.Arrays;


public class BSExist {


    // 寻找 等于序列的最左侧值
    public static int exist(int[] sortedArr, int num) {
        if (sortedArr == null || sortedArr.length == 0) {
            return -1;
        }
        int L = 0;
        int R = sortedArr.length - 1;
        int ans = -1;
        while (L <= R) {
            int mid = (L + R) / 2;
            if (sortedArr[mid] == num) {
                ans = mid;
                R = mid - 1;
            } else if (sortedArr[mid] < num) {
                L = mid + 1;
            } else {
                R = mid - 1;
            }
        }
        return ans;
    }

    public static int[] generateSortedArr(int maxLen, int maxValue) {
        int len = (int) (Math.random() * (maxLen + 1));
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * (maxValue + 1));
        }
        Arrays.sort(arr);
        return arr;
    }

    public static int compare(int[] arr, int num) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == num) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int maxLen = 1000;
        int maxValue = 10000;
        int times = 100000;
        for (int i = 0; i < times; i++) {
            int[] testArr = generateSortedArr(maxLen, maxValue);
            int target = (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * (maxValue + 1));
            if (compare(testArr, target) != exist(testArr, target)) {
                System.out.println("BS wrong");
                break;
            }
        }
        System.out.println("Finish");
    }
}
