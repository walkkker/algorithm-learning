package backtracking;

import java.util.ArrayList;
import java.util.List;

public class Permutation {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        process(nums, 0, list);
        return list;
    }

    public void process(int[] nums, int startIndex, List<List<Integer>> list) {
        if (startIndex == nums.length) {
            list.add(arrToList(nums));
            return;
        }

        for (int i = startIndex; i < nums.length; i++) {
            swap(nums, i, startIndex);
            process(nums, startIndex + 1, list);
            swap(nums, i, startIndex);
        }

    }

    public void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public List<Integer> arrToList(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            list.add(num);
        }
        return list;
    }
}
