package sort;

import java.util.Arrays;

public class Compare {

    public static int[] generateRandomArray(int maxLen, int maxValue) {
        int len = (int) (Math.random() * (maxLen + 1));
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            // [0, maxValue]
            // [-maxValue, maxValue] -> [0, maxValue] - [0, maxValue]
            arr[i] = (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int N = arr.length;
        int[] newArr = new int[N];
        for (int i = 0; i < N; i++) {
            newArr[i] = arr[i];
        }
        return newArr;
    }

    public static boolean compare(int[] arr1, int[] arr2) {
        if (arr1.length != arr2.length) {
            return false;
        }
        int N = arr1.length;
        for (int i = 0; i < N; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int maxLen = 100;
        int maxValue = 10000;
        int times = 100000;
        for (int i = 0; i < times; i++) {
            int[] ans = generateRandomArray(maxLen, maxValue);
            int[] copy = copyArray(ans);
            Arrays.sort(ans);

            int[] bubble = copyArray(copy);
            BubbleSort.sortMethod(bubble);
            if(!compare(ans, bubble)) {
                System.out.println("bubble sort wrong");
                break;
            }

            int[] insert = copyArray(copy);
            InsertionSort.sortMethod(insert);
            if(!compare(ans, insert)) {
                System.out.println("insertion sort wrong");
                break;
            }

            int[] selection = copyArray(copy);
            SelectionSort.sortMethod(selection);
            if(!compare(ans, selection)) {
                System.out.println("selection sort wrong");
                break;
            }

            int[] mergeSortRecursive = copyArray(copy);
            MergeSort.sortMethodRecursive(mergeSortRecursive);
            if(!compare(ans, mergeSortRecursive)) {
                System.out.println("Merge sort recursive wrong");
                break;
            }

            int[] mergeSortUnRecursive = copyArray(copy);
            MergeSort.sortMethodUnRecursive(mergeSortUnRecursive);
            if(!compare(ans, mergeSortUnRecursive)) {
                System.out.println("Merge sort non-recursive wrong");
                break;
            }


            int[] quick1 = copyArray(copy);
            QuickSort.sortMethod1(quick1);
            if(!compare(ans, quick1)) {
                System.out.println("quicksort1 wrong");
                break;
            }


            int[] quick2 = copyArray(copy);
            QuickSort.sortMethod2(quick2);
            if(!compare(ans, quick2)) {
                System.out.println("quicksort2 wrong");
                break;
            }

            int[] quickUnrecursive = copyArray(copy);
            QuickSort.sortMethodNonRecursive(quickUnrecursive);
            if(!compare(ans, quickUnrecursive)) {
                System.out.println("quicksort Non-recursive wrong");
                break;
            }

        }
        System.out.println("Finish");
    }
}
