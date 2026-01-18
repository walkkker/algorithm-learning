package makeCharts;

import java.util.ArrayList;
import java.util.List;

/**
 * 输入N， 输出boolean. 除了动态规划，还可以考虑打表
 */
public class SearchMSumToN {

    /**
     * 下面这是一开始没有理解题意，一开始以为是 递增序列 能否 组成该数字。 结果打表的时候发现，全部都true。
     * 于是乎，为了确认，做了一个搜索函数。 查询 【1】是否有递增序列能够组成该数字 【2】如果有的话，返回任意组合（该实现返回的是 从最小开始数字 开始的 递增序列）
     * 之所以特殊说明，是因为该题不同于 排列组合简单回溯，不需要判断 该路径是否满足条件。 该题需要判断正确的路径，并将正确的路径 进行返回。
     * 总体思路， 就是 递归函数函数返回值使用 boolean 判断是否该路径是正确的，然后参数中使用List，当作记录路径的列表。 还是使用了 回溯。
     * 核心： 判断式搜索！！！ 返回值用于判断， + 参数中List 用作回溯
     * @param N 要检查的整数值
     * @param ans 创建的列表
     * @return
     */
    public static boolean judge(int N, List<Integer> ans) {
        return process(0, N, ans);
    }

    public static boolean process(int preNum, int rest, List<Integer> ans) {
        if (rest == 0) {
            return true;
        }
        boolean result = false;
        for (int start = preNum + 1; start <= rest; start++) {
            ans.add(start);
            result |= process(start, rest - start, ans);
            if (result) {
                break;
            }
            ans.remove(ans.size() - 1);
        }
        return result;
    }

    public static void main(String[] args) {
        int times = 1000;
        List<Integer> ans = new ArrayList<>();
        judge(19, ans);
        for (int num : ans) {
            System.out.print(num + " ");
        }
    }




}
