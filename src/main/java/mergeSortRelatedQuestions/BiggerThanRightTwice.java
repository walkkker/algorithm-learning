package mergeSortRelatedQuestions;


/**
 * 求每一个数num 右边有多少个数*2之后 <num
 *
 * 【数组每个元素】 【右侧】 【小于】 问题 ==》 mergeSort类型问题
 *
 * 但是， 要 ?*2 < num ， 所以 【非元素本身】进行比较 ==》 计算过程（使用指针不回退的技巧， for外循环 + while内部条件） 与 merge过程分开即可 （因为这类题，本质是 依赖 mergeSort的有序性）
 */
public class BiggerThanRightTwice {


    public static int biggerThanRightTwice(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }

    // 返回 L,R 上 当前元素大于右侧*2 的 所有数量
    public static int process(int[] arr, int L, int R) {
        if (L == R) {
            return 0;
        }
        int M = (L + R) / 2;
        return process(arr, L, M)
                + process(arr, M + 1, R)
                + merge(arr, L, M, R);
    }


    /*
       指针不回退的技巧
     */
    public static int merge(int[] arr, int L, int M, int R) {
        int ans = 0;
        // 计算每一个左侧数字，大于右侧两倍的数量
        // 不回退
        int windowR = M + 1; // [M + 1, windowR)
        for (int i = L; i <= M; i++) {
            // 边界条件，满足条件向后移， 直到不满足 ==》 不满足时 即为[M+1, windowR) 右侧开区间，所以就要达到第一个不满足，所以只要满足条件就继续++
            // 因为我要达到第一个不满足条件的地方，所以只要满足条件我就++，当退出循环时，就到达了 第一个不满足条件的地方
            // 因为是开区间，所以就要让windowR变成第一个不满足条件的地方
            while (windowR <= R && arr[windowR] * 2 < arr[i]) {
                windowR++;
            }
            ans += windowR - M - 1;
        }

        int[] help = new int[R - L + 1];
        int p1 = L;
        int p2 = M + 1;
        int i = 0;

        while (p1 <= M && p2 <= R) {
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= M) {
            help[i++] = arr[p1++];
        }
        while (p2 <= R) {
            help[i++] = arr[p2++];
        }
        for (i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
        return ans;
    }


    // for test
    public static int comparator(int[] arr) {
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > (arr[j] << 1)) {
                    ans++;
                }
            }
        }
        return ans;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) ((maxValue + 1) * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            if (biggerThanRightTwice(arr1) != comparator(arr2)) {
                System.out.println("Oops!");
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }



}
