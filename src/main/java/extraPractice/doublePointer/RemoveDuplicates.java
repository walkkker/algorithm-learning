package extraPractice.doublePointer;

/**
 * https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array/
 */

public class RemoveDuplicates {

    public static int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int done = 0; // 标记 完成区的最后一个位置
        for (int cur = 1; cur < nums.length; cur++) {
            if (nums[done] == nums[cur]) {
                continue;
            } else {
                nums[++done] = nums[cur];
            }
        }
        return done + 1;
    }

}
