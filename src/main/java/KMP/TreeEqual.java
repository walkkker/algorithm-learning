package KMP;

import java.util.LinkedList;
import java.util.List;

public class TreeEqual {
    public class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    // https://leetcode-cn.com/problems/check-subtree-lcci/
    class Solution {
        public boolean checkSubTree(TreeNode t1, TreeNode t2) {
            String[] str = serialise(t1);
            String[] match = serialise(t2);
            int[] next = getNextArr(match);
            int x = 0;
            int y = 0;
            while (x < str.length && y < match.length) {
                // 【大错误点！！！】字符串比较一定要使用 equals！！！
                if (str[x].equals(match[y])) {
                    x++;
                    y++;
                } else {
                    if (next[y] != -1) {
                        y = next[y];
                    } else {
                        x++;
                    }
                }
            }
            return y == match.length ? true : false;
        }

        public String[] serialise(TreeNode head) {
            LinkedList<String> list = new LinkedList<>();
            pre(head, list);
            String[] ans = new String[list.size()];
            int index = 0;
            for (String c : list) {
                ans[index++] = c;
            }
            return ans;
        }

        public void pre(TreeNode head, List<String> list) {
            if (head == null) {
                list.add("null");
                return;
            }
            list.add(String.valueOf(head.val));
            pre(head.left, list);
            pre(head.right, list);
        }

        public int[] getNextArr(String[] match) {
            if (match.length == 1) {
                return new int[]{-1};
            }
            int[] next = new int[match.length];
            next[0] = -1;
            next[1] = 0;
            int cn = 0;
            int i = 2;
            while (i < match.length) {
                if (match[cn] == match[i - 1]) {
                    next[i++] = ++cn;
                } else {
                    if (cn > 0) {
                        cn = next[cn];
                    } else {
                        next[i++] = 0;
                    }
                }
            }
            return next;
        }
    }

    class Solution2 {
        public boolean checkSubTree(TreeNode t1, TreeNode t2) {
            return dfs(t1, t2);
        }

        public boolean dfs(TreeNode head1, TreeNode head2) {
            if (head1 == null) {
                return process(head1, head2);
            }
            boolean res = process(head1, head2);
            if (res) {
                return res;
            }
            boolean left = dfs(head1.left, head2);
            if (left) {
                return left;
            }
            boolean right = dfs(head1.right, head2);
            return right;
        }

        public boolean process(TreeNode head1, TreeNode head2) {
            if (head1 == null && head2 == null) {
                return true;
            }
            if (head1 == null || head2 == null) {
                return false;
            }
            if (head1.val != head2.val) {
                return false;
            }
            boolean left = process(head1.left, head2.left);
            if (left == false) {
                return false;
            }
            boolean right = process(head1.right, head2.right);
            return right;
        }
    }
}
