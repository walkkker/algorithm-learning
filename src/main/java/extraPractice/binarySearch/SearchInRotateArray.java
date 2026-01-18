package extraPractice.binarySearch;


public class SearchInRotateArray {
    // https://leetcode-cn.com/problems/search-in-rotated-sorted-array/
    // 无重复元素的 旋转数组 【注意错误点，边界理清】
    public static int search1(int[] nums, int target) {
        int L = 0;
        int R = nums.length - 1;
        while (L <= R) {
            int mid = (L + R) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[L] == nums[mid]) {
                L = L + 1;
            } else if (nums[L] < nums[mid]) { // the left is in order
                if (target < nums[mid] && target >= nums[L]) { // 这里一开始忘了 = 的情况， 画出界限的话应该是 [nums[L], nums[mid])的话，去左侧；否则去右侧
                    R = mid - 1;
                } else {
                    L = mid + 1;
                }
            } else {  // the right is in order
                if (target > nums[mid] && target <= nums[R]) { // (nums[mid], nums[R]]
                    L = mid + 1;
                } else {
                    R = mid - 1;
                }
            }
        }
        return -1;
    }



//  https://leetcode-cn.com/problems/search-in-rotated-sorted-array-ii/
    public boolean search2(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        int L = 0;
        int R = nums.length - 1;
        while (L <= R) {
            int mid = (L + R) / 2;
            if (nums[mid] == target) {
                return true;
            }
            if (nums[L] == nums[mid] && nums[L] == nums[R]) {  // 三者相同时，缩小左右区间。 因为这个时候L和R 都跟M相同，不可能考虑，之间缩小空间即可
                L++;
                R--;
            } else {
                if (nums[L] == nums[mid]) {
                    L = mid + 1;
                } else if (nums[L] < nums[mid]) {
                    if (target < nums[mid] && target >= nums[L]) {
                        R = mid - 1;
                    } else {
                        L = mid + 1;
                    }
                } else {
                    if (target > nums[mid] && target <= nums[R]) {
                        L = mid + 1;
                    } else {
                        R = mid - 1;
                    }
                }
            }
        }
        return false;
    }



    public int search3(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        // 这一块特别重要，保证了 不会出现找右侧区间后，但是被舍弃掉的左侧区间也有答案的情况
        if (arr[0] == target) {
            return 0;
        }
        int L = 0;
        int R = arr.length - 1;
        int ans = -1;
        while (L <= R) {
            int mid = (L + R) / 2;
            if (arr[mid] == target) {
                ans = mid;
                R = mid - 1;
            } else {
                if (arr[L] == arr[mid] && arr[mid] == arr[R]) {
                    L++;
                    R--;
                } else { // 只看 L 和 mid就可以了
                    if (arr[L] == arr[mid]) {
                        L = mid + 1;
                    } else if (arr[L] < arr[mid]) {
                        // 查看是否在左侧区间
                        if (target < arr[mid] && target >= arr[L]) {
                            R = mid - 1;
                        } else {
                            L = mid + 1;
                        }
                    } else {  // 右侧有序
                        if (target > arr[mid] && target <= arr[R]) {
                            L = mid + 1;
                        } else {
                            R = mid - 1;
                        }
                    }
                }
            }
        }
        return ans;
    }



}
