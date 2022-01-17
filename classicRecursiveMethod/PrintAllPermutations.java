package classicRecursiveMethod;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class PrintAllPermutations {


    public static List<String> printAllPermutations(String s) {
        List<String> ans = new ArrayList<>();
        char[] str = s.toCharArray();
        process(str, 0, ans);
        System.out.println(Arrays.toString(str)); // 由于恢复了现场，所以 str 还是初始的顺序
        return ans;
    }

    // 数组上的 深度优先遍历。 要index表示位置，每次递归位置+1
    // 当前位置来到index,那么Index之前的位置都已经确定，现在确定index位置上的字符，然后剩余部分全排列。
    public static void process(char[] str, int index, List<String> ans) {
        if (index == str.length) {
            ans.add(String.valueOf(str));
            return;
        }

        // 选定index位置的字符
        for (int i = index; i < str.length; i++) {
            swap(str, index, i); // 确定i位置的数字
            process(str, index + 1, ans);
            swap(str, index, i); // 这里一定要恢复现场
        }
    }

    public static void swap(char[] arr, int i, int j) {
        char tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }


    public static void main(String[] args) {
        String test = "abcde";
        List<String> ans = printAllPermutations(test);
        System.out.println();
        for (String cur : ans) {
            System.out.println(cur);
        }
    }

}
