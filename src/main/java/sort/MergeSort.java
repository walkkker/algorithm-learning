package sort;

/**
 * 归并排序： 递归 + 非递归32
 */
public class MergeSort {


    // 递归实现
    // mid = (L + R) >> 1; [L, mid] 有序  [mid + 1, R] 有序， 合并两个有序列表
    public static void merge(int[] arr, int L, int R) {
        // 遍历赋值
        int[] help = new int[R - L + 1];
        int mid = (L + R) >> 1;
        int p1 = L;
        int p2 = mid + 1;
        int i = 0;
        while (p1 <= mid && p2 <= R) {
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= mid) {
            help[i++] = arr[p1++];
        }
        while (p2 <= R) {
            help[i++] = arr[p2++];
        }
        for (i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
    }

    public static void process(int[] arr, int L, int R) {
        if (L == R) {
            return;
        }
        int mid = (L + R) >> 1;
        process(arr, L, mid);
        process(arr, mid + 1, R);
        merge(arr, L, R);
    }

    public static void sortMethodRecursive(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }


    // 非递归
    // 因为是非递归，所以要手动记录L,R
    public static void sortMethodUnRecursive(int[] arr) {
        int N = arr.length;
        // 初始步长
        int mergeSize = 1;
        while (mergeSize < N) {
            int L = 0;
            // [L, ?) ? - L = mergeSize
            // 没有组时 停止循环 -> L >= N => 只要L < N，他就循环 分组 merge
            // 左右一组  左右一组
            while (L < N) {
                // [L, ?]  ? - L + 1 = mergeSize
                int M = mergeSize + L - 1;
                // 判断是否只有左组 提前结束
                if (M >= N - 1) {
                    break;
                }
                // 否则此时，一定存在右组至少一个节点，找右组
                // 右组的起始点 是 M + 1（一定存在）； 默认终止点为 [M + 1, ?] mergeSize -> mergeSize + M
                int R = mergeSize + M < N ? mergeSize + M : N - 1;
                merge(arr, L, M, R);
                L = R + 1;
            }
            // 防止溢出
            if (mergeSize > N / 2) {
                break;
            }
            mergeSize <<= 1;
        }


    }

    public static void merge(int[] arr, int L, int M, int R) {
        int[] help = new int[R - L + 1];
        int p1 = L;
        int p2 = M + 1;
        int index = 0;
        while (p1 <= M && p2 <= R) {
            help[index++] = arr[p1] > arr[p2] ? arr[p2++] : arr[p1++];
        }
        while (p1 <= M) {
            help[index++] = arr[p1++];
        }
        while (p2 <= R) {
            help[index++] = arr[p2++];
        }
        for (index = 0; index < help.length; index++) {
            arr[L + index] = help[index];
        }
    }

}
