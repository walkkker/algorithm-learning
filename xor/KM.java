package xor;

import java.util.HashMap;
import java.util.HashSet;

/**
 * KM问题： M > 1, K < M   只有一种元素出现了K次，其余元素出现了M次
 *
 * 普通做法：O（N）额外空间复杂度。 哈希表 词频统计，   遍历哈希表，从而找到K次数字
 * 进阶做法： O（1）额外空间复杂度。 创建 长度32的 辅助数组，记录所有数字在 各个位置上1的数量。 通过取模即可找到K次数字
 */
public class KM {

    // 精简版 针对KM问题已足够
    public static int onlyKTimes1(int[] arr, int K, int M) {
        int[] help = new int[32];  // 整数 位数长度为 32位
        for (int i = 0; i < arr.length; i++) {
            for (int j = 31; j >=0; j--) {
                help[j] += (arr[i] >> j) & 1;
            }
        }
        int ans = 0;
        for (int i = 0; i < 32; i++) {
            if (help[i] % M != 0) {
                ans |= (1 << i);
            }
        }
        return ans;
    }


    // 完整版 存在判断数组中元素不为K次情况的 讨论，当报错时，会返回-1
    // 稍微改变了一下对1的读取：精简版使用遍历32位，完整版创建一个哈希表，KEY为记录32位分别为1时所代表的值，VALUE为对应的位数
    public static int onlyKTimes2(int[] arr, int K, int M) {
        HashMap<Integer, Integer> cnt = new HashMap<>();
        for (int i = 0; i < 32; i++) {
            cnt.put(1 << i, i);
        }

        int[] help = new int[32];

        for (int num : arr) {
            while (num != 0) {
                int rightOne = num & (-num);
                help[cnt.get(rightOne)]++;
                // 抹掉最后一个1
                num ^= rightOne;
            }
        }

        int ans = 0;
        // 考虑不存在K的情况， 1.非0但不为K次 2.出现0时，是否为K次
        for (int i =0; i < help.length; i++) {
            if (help[i] % M != 0) {
                if (help[i] % M == K) {
                    ans |= (1 << i);
                } else {
                    return -1;
                }
            }
        }

        // 特殊0的情况. 特别讨论，当 0 出现非K次时，上述不会报错，所以需要特殊讨论
        if (ans == 0) {
            int count = 0;
            for (int num : arr) {
                if (num == 0) {
                    count++;
                }
            }
            if (count != K) {
                return -1;
            }
        }

        return ans;
    }

    // 对数器
    public static int test(int[] arr, int k, int m) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : arr) {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }
        for (int num : map.keySet()) {
            if (map.get(num) == k) {
                return num;
            }
        }
        return -1;
    }



    public static int[] randomArray(int maxKinds, int range, int k, int m) {
        int ktimeNum = randomNumber(range);
        // 真命天子出现的次数
        int times = Math.random() < 0.5 ? k : ((int) (Math.random() * (m - 1)) + 1);
        // 2
        int numKinds = (int) (Math.random() * maxKinds) + 2;
        // k * 1 + (numKinds - 1) * m
        int[] arr = new int[times + (numKinds - 1) * m];
        int index = 0;
        for (; index < times; index++) {
            arr[index] = ktimeNum;
        }
        numKinds--;
        HashSet<Integer> set = new HashSet<>();
        set.add(ktimeNum);
        while (numKinds != 0) {
            int curNum = 0;
            do {
                curNum = randomNumber(range);
            } while (set.contains(curNum));
            set.add(curNum);
            numKinds--;
            for (int i = 0; i < m; i++) {
                arr[index++] = curNum;
            }
        }
        // arr 填好了
        for (int i = 0; i < arr.length; i++) {
            // i 位置的数，我想随机和j位置的数做交换
            int j = (int) (Math.random() * arr.length);// 0 ~ N-1
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
        return arr;
    }

    // [-range, +range]
    public static int randomNumber(int range) {
        return ((int) (Math.random() * range) + 1) - ((int) (Math.random() * range) + 1);
    }

    public static void main(String[] args) {
        int kinds = 10;
        int range = 60;
        int testTime = 100000;
        int max = 9;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int a = (int) (Math.random() * max) + 1; // a 1 ~ 9
            int b = (int) (Math.random() * max) + 1; // b 1 ~ 9
            int k = Math.min(a, b);
            int m = Math.max(a, b);
            // k < m
            if (k == m) {
                m++;
            }
            int[] arr = randomArray(kinds, range, k, m);
            int ans1 = test(arr, k, m);
            int ans2 = onlyKTimes2(arr, k, m);
            if (ans1 != ans2) {
                //if (ans1 == -1) continue;
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("出错了！");
            }
        }
        System.out.println("测试结束");

    }





}
