package sort;

/**
 * 插入排序
 */
public class InsertionSort {
    public static void sortMethod(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int end = 1; end < arr.length; end++) {
            for (int i = end; i >= 1 && arr[i - 1] > arr[i]; i--) {
                swap(arr, i, i - 1);
            }
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
