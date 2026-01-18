package sort;

import java.util.Stack;

/**
 * 荷兰国旗问题 + 快排
 * 归位函数 ： 返回的是 归位的位置
 * <p>
 * quickSort1 普通版本 partition过程 一次 归位 【一个】数字
 * quickSort2 改善版本 荷兰国旗问题 -> 一次 归位 【目标数字及其相同数字】， 并返回其 左右区间
 * quickSort2 防止发生倾斜，引入随机因子对目标数字进行选择，从而实现 随机快排 时间复杂度O(N*LogN)
 */
public class QuickSort {


    // 归位 quickSort1 初始版本
    public static int partition(int[] arr, int L, int R) {
        if (L > R) {
            return -1;
        }
        if (L == R) {
            return L;
        }
        // [L, lessEqual)
        int lessEqual = L; // 表示 没有包含任何数字
        int index = L; // 要归位的起始位置
        while (index < R) { // 首先排除 R 位置
            // 规则 : 1. 小于等于的数字 与左边界前一个数字进行交换，左边界++
            // 2. 大于的数字： 不做任何处理，只index++
            if (arr[index] <= arr[R]) {
                swap(arr, index, lessEqual++);
            }
            index++;
        }
        swap(arr, R, lessEqual);
        return lessEqual;
    }

    public static void process1(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        int M = partition(arr, L, R);
        process1(arr, L, M - 1);
        process1(arr, M + 1, R);
    }

    public static void sortMethod1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process1(arr, 0, arr.length - 1);
    }


    /*
     quickSort2 第二版本
     1. 荷兰国旗问题 进行 partition归位
     2. 一次固定一个区间
     3. 并加入随机数选取 归位值
     */
    public static int[] partition2(int[] arr, int L, int R) {
        if (L > R) {
            return new int[]{-1, -1};
        }
        if (L == R) {
            return new int[]{L, L};
        }
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
        swap(arr, ++more, R);
        return new int[]{less, more};
    }

    public static void process2(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        // 加入随机 -> 随机快排
        swap(arr, (int) (Math.random() * (R - L + 1)) + L, R);
        int[] equalArea = partition2(arr, L, R);
        process2(arr, L, equalArea[0] - 1);
        process2(arr, equalArea[1] + 1, R);
    }

    public static void sortMethod2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process2(arr, 0, arr.length - 1);
    }


    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }


    // 非递归
    // 快排非递归版本需要的辅助类
    // 要处理的是什么范围上的排序
    public static class Op {
        int l;
        int r;

        public Op(int l, int r) {
            this.l = l;
            this.r = r;
        }
    }

    public static void sortMethodNonRecursive(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        swap(arr, arr.length -1, (int) (Math.random() * arr.length));
        int[] equalArea = netherLandsFlag(arr, 0, arr.length - 1);
        int el = equalArea[0];
        int er = equalArea[1];
        Stack<Op> stack = new Stack<>();
        stack.push(new Op(er + 1, arr.length - 1));
        stack.push(new Op(0, el - 1));
        while (!stack.isEmpty()) {
            Op op = stack.pop();
            if (op.l < op.r) {
                // 在partition之前，先随机 最右侧数字
                swap(arr, op.r, op.l + ((int) (Math.random() * (op.r - op.l + 1))));
                equalArea = netherLandsFlag(arr, op.l, op.r);
                el = equalArea[0];
                er = equalArea[1];
                stack.push(new Op(er + 1, op.r));
                stack.push(new Op(op.l, el - 1));
            }
        }
    }


    public static int[] netherLandsFlag(int[] arr, int L, int R) {
        if (L > R) {
            return new int[]{-1, -1};
        }
        if (L == R) {
            return new int[]{L, L};
        }
        // L < R
        int less = L; // [L, less)
        int more = R - 1; // (more, R - 1]
        int index = L;
        while (index <= more) {
            if (arr[index] == arr[R]) {
                index++;
            } else if (arr[index] < arr[R]) {
                swap(arr, less++, index++);
            } else {
                swap(arr, index, more--);
            }
        }
        swap(arr, R, more + 1);
        more++;
        return new int[]{less, more};
    }


}
