package classicRecursiveMethod;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PrintAllSubsequencesNoRepeat {

    // 从左往右尝试模型， 递归函数使用set接收，从而去重
    public static List<String> subsNoRepeat(String s) {
        if (s == null) {
            return null;
        }
        Set<String> set = new HashSet<>();
        List<String> ans = new ArrayList<>();
        char[] str = s.toCharArray();
        process(str, 0, set, "");
        for (String cur : set) {
            ans.add(cur);
        }
        return ans;
    }

    // 从左往右尝试模型，一个一个尝试（由于在一个一个尝试，所以需要存储前面的结果）。 【递归中的递】解法，将需要较多参数，来保存之前的答案。
    // 来到 str的【index】 位置，如果是末尾，那么将之前的【path】加入到ans中；否则的话，对当前【index】的字符进行要或不要两种可能性
    // 然后 结合之前的【path】，一同传给下一个位置。
    public static void process(char[] str, int index, Set<String> ans, String path) {
        if (index == str.length) {
            ans.add(path);
            return;
        }
        // 不要
        process(str, index + 1, ans, path);
        // 要
        process(str, index + 1, ans, path + String.valueOf(str[index]));
    }


    public static void main(String[] args) {
        String test = "acccc";
        List<String> ans = subsNoRepeat(test);
        for (String cur : ans) {
            System.out.println(cur);
        }
    }
}
