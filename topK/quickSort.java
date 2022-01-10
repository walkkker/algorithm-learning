package topK;

public class quickSort {

    public static class Solution {
        public int findKth(int[] a, int n, int K) {
            // write code here
            int target = a.length - K;
            int L = 0;
            int R = a.length - 1;
            int[] equalArea = partition(a, L, R);
            while (target > equalArea[1] || target < equalArea[0]) {
                if (target > equalArea[1]) {
                    L = equalArea[1] + 1;
                } else if (target < equalArea[0]) {
                    R = equalArea[0] - 1;
                }
                equalArea = partition(a, L, R);
            }
            return a[equalArea[0]];
        }

        public static int[] partition(int[] arr, int L, int R) {

            int less = L; // [L, less)
            int more = R - 1; // (more, R - 1]
            int index = L;

            while (index <= more) {
                if (arr[index] < arr[R]) {
                    swap(arr, index++, less++);
                } else if (arr[index] == arr[R]) {
                    index++;
                } else {
                    swap(arr, index, more--);
                }
            }
            swap(arr, R, ++more);
            return new int[]{less, more};
        }

        public static void swap(int[] arr, int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }
}
