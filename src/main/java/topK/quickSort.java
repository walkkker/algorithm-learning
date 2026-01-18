package topK;

public class quickSort {

    // 最优解 快排 + 二分
    // 不管是 topKMAX, 还是 topKMin， 都最终将其转换成 目标下标就可以。
    // 对于 topKMAX 而言，寻找的下标就为 len(arr) - K;
    // 对于 topKMIN而言， 寻找的下标就是 K - 1;
    // 这些都是建立在 荷兰国旗问题上的 排序（左小右大）

    public int topK(int[] arr, int K) {
        if (K > arr.length) {
            return -1;
        }
        int index = arr.length - K;    // 找到 index 的位置之后，就是查找 index 所在位置的值了
        int L = 0;
        int R = arr.length - 1;
        int[] range;
        while (L <= R) {
            swap(arr, L + (int) (Math.random() * (R - L + 1)), R);
            range = partition(arr, L, R);
            if (index < range[0]) {
                R = range[0] - 1;
            } else if (index > range[1]) {
                L = range[1] + 1;
            } else {
                return arr[index];
            }
        }
        return -1;
    }


    public static int[] partition(int[] arr, int L, int R) {
        if (L > R) {
            return new int[]{-1, -1};
        }
        int less = L - 1; // [L, less]
        int more = R;     // [more, R - 1], 起始值长度都为0
        int index = L;    // 从 输入范围的起始点开始 [L, R], 所以 index 起始值 为 L
        while (index < more) {  // index 指向 要处理的数值，当index == more时，表示当前数字已经被处理过了，所以退出循环
            // 遵循三大规则即可
            if (arr[index] == arr[R]) {
                index++;
            } else if (arr[index] < arr[R]) {
                swap(arr, index++, ++less);
            } else {       // if (arr[index] > arr[R])
                swap(arr, index, --more);
            }
        }
        swap(arr, R, more);
        return new int[]{less + 1, more};
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
