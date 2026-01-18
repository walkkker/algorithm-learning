package backtracking;

import java.util.*;

public class Combinations {

    // 1. 笨蛋版本 V1
    // 笨蛋在哪里？ 【1】多写一个copyList函数用于复制List，其实可以直接用 deque或者stack去操纵，然后 new ArrayList<>()构造器中，可以讲这些Collection的子类都转成ArrayList
    // 【2】没有剪枝
    public List<List<Integer>> combine1(int n, int k) {
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        int[] nums = new int[n];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = i + 1;
        }
        process1(0, nums, k, new ArrayList<Integer>(), list);
        return list;
    }

    public void process1(int index, int[] nums, int rest, List<Integer> tmp, List<List<Integer>> list) {
        if (rest == 0) {
            list.add(copyList(tmp));
        }

        for (int i = index; i < nums.length; i++) {
            tmp.add(nums[i]);
            process1(i + 1, nums, rest - 1, tmp, list);
            tmp.remove(tmp.size() - 1);
        }
    }

    public List<Integer> copyList(List<Integer> list) {
        List<Integer> copy =  new ArrayList<>();
        for (int num : list) {
            copy.add(num);
        }
        return copy;
    }


    // V2 用了deque的实现，并且进行了了剪枝 ==》 最优解
    public List<List<Integer>> combine2(int n, int k) {
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        int[] nums = new int[n];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = i + 1;
        }
        process2(0, nums, k, new LinkedList<>(), list);
        return list;
    }

    public void process2(int startIndex, int[] nums, int rest, Deque<Integer> tmp, List<List<Integer>> list) {
        if (rest == 0) {
            // 不用自己编写 copy函数，直接使用下面的方式就可以
            list.add(new ArrayList<>(tmp));
            return;
        }

        for (int i = startIndex; i <= nums.length - rest; i++) {
            tmp.offerLast(nums[i]);
            process2(i + 1, nums, rest - 1, tmp, list);
            tmp.pollLast();
        }
    }
}
