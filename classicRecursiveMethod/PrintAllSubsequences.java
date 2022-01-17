package classicRecursiveMethod;

import java.util.ArrayList;
import java.util.List;

public class PrintAllSubsequences {

    // 【递归中的递解决问题】  -》 从左往右的尝试模型。 要/不要
    // 后面subs2的代码是使用的 【递归中的归】来实现的。我们也可以用【递归中的递】来实现，【递归中的递】可以更少的在
    // 每一层递归中构建新的List，而只使用 主函数传进去的List。 并且更为关键的是，【递归中的递】解决问题，更趋向于【动态规划】 对应的递归方法。因为有更明显的依赖关系。
    public static List<String> subs(String s) {
        if (s == null) {
            return null;
        }
        List<String> ans = new ArrayList<>();
        char[] str = s.toCharArray();
        process(str, 0, ans, "");
        return ans;
    }

    // 从左往右的递归

    // str 固定参数
    // 来到了str[index]字符，index是位置
    // str[0..index-1]已经走过了！之前的决定，都在path上
    // 之前的决定已经不能改变了，就是path
    // str[index....]还能决定，之前已经确定，而后面还能自由选择的话，
    // 把所有生成的子序列，放入到ans里去
    // 重复： ！！！ path存的是之前已经确定的路径
    public static void process(char[] str, int index, List<String> ans, String path) {
        // base case： 当 index==str.length时，意味着 当前自由组合的子序列可以直接确定就是， path，所以可以直接执行不用再递归，直接将path + "" 加入到ans中去
        if (index == str.length) {
            ans.add(path);
            return;
        }

        // 普通情况处理 分解成子问题：
        // 【1】要当前的字符str[i]， 递归参数为， 将path = path+str[i]， 然后递归剩余部分(index + 1)
        // 【2】不要当前字符str[i]，参数为 path = path， 递归剩余部分(index + 1)。
        // 记住递归函数的含义，将 之前的path与【当前的 index之后的位置的自由组合】分别合并，加入到ans中去

        process(str, index + 1, ans, path);
        // 要index位置的字符
        process(str, index + 1, ans, path + String.valueOf(str[index]));
    }




    //  这段代码是我一开始写的，没有任何错误，不过用到了 【递归中的归】来收集信息，就像链表反转的递归实现。
    // 不过这个递归虽然使用的参数少，但是我感觉写的不好，不如上面左老师那一种。
    public static List<String> subs2(String s) {
        if (s == null) {
            return new ArrayList<String>();
        }
        char[] str = s.toCharArray();
        return process(str, 0);
    }

    // 首先设定递归函数的含义 返回index及以后的所有组合，以List形式返回
    public static List<String> process(char[] str, int index) {
        // base case
        if (index == str.length) {
            List<String> ans = new ArrayList<>();
            ans.add("");
            return ans;
        }
        // 注意不要只是用ans一个List,会出现迭代器失效
        List<String> ans = new ArrayList<>();
        List<String> rest = process(str, index + 1);
        for (String each : rest) {
            ans.add(each);
            ans.add(String.valueOf(str[index]) + each);
        }
        return ans;
    }

    public static void main(String[] args) {
        String test = "abcd";
        printList(subs(test));
        System.out.println();
        printList(subs2(test));
    }

    public static void printList(List<String> list) {
        for (String str : list) {
            System.out.println(str);
        }
    }

}
