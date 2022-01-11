package greedyAlgorithm;

import java.util.PriorityQueue;

public class LessMoneySplitGold {

    // 暴力递归
    public static int lessMoney1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return process(arr);
    }

    // 返回 两两合并的最小值 ==》 (任意两个合并 + 剩余部分递归返回的结果)中的最小值，就是答案
    public static int process(int[] arr) {
        if (arr.length == 1) {
            return 0;
        }
        // 这个技巧挺好的， 因为要取最小值，所以ans初始值 初始成了 Integer.MAX_VALUE
        int ans = Integer.MAX_VALUE;
        // 因为要两两组合，所以 j = i + 1;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                int tmp = arr[i] + arr[j] + process(copyAndMergeTwo(arr, i, j));
                ans = Math.min(ans, tmp);
            }
        }
        return ans;
    }

    public static int[] copyAndMergeTwo(int[] arr, int i, int j) {
        int[] ans = new int[arr.length - 1];
        int index = 0;
        int mergeNum = arr[i] + arr[j];
        for (int k = 0; k < arr.length; k++) {
            if (k != i && k != j) {
                // index永远指向 待插入值的位置。 所以新的值，直接插入到index位置，然后index++指向新位置。
                ans[index++] = arr[k];
            }
        }
        ans[index] = mergeNum;
        return ans;
    }

    // 每次合并最小的两个， 由此进一步的局部就会缩小，最终合成只有一个时，说明切割方案完成，得到最终答案。
    public static int lessMoney2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        // 1. 把所有的数字全部加到 堆 中
        for (int i = 0; i < arr.length; i++) {
            heap.add(arr[i]);
        }
        int cur = 0;
        int ans = 0;
        while (heap.size() > 1) {
            cur = heap.poll() + heap.poll();
            ans += cur;
            heap.offer(cur);
        }
        return ans;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 6;
        int maxValue = 1000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            if (lessMoney1(arr) != lessMoney2(arr)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
