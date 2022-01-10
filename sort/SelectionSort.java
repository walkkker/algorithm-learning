package sort;

/**
 * 选择排序
 */
public class SelectionSort {
    public static void sortMethod(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int end = 0; end < arr.length; end++) {
            int minIndex = end;
            for (int i = end + 1; i < arr.length; i++) {
                if (arr[i] < arr[minIndex]) {
                    minIndex = i;
                }
            }
            swap(arr, minIndex, end);
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
