package extraPractice.binaryTree;

import java.util.LinkedList;
import java.util.Queue;

public class IsSymmetric {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // 递归
    public static boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return process(root.left, root.right);
    }

    public static boolean process(TreeNode head1, TreeNode head2) {
        if (head1 == null ^ head2 == null) {
            return false;
        }

        if (head1 == null && head2 == null) {
            return true;
        }
        if (head1.val != head2.val) {
            return false;
        }
        return process(head1.left, head2.right) && process(head1.right, head2.left);
    }

    // 非递归
    public static boolean isSymmetric2(TreeNode root) {
        return check(root, root);
    }

    // 基于层序遍历进行修改  -> 每次都两两出， 对应着 镜像的点， 然后无问题后， 将下一层的孩子节点，左右 右左的 分别入队
    public static boolean check(TreeNode head1, TreeNode head2) {
        if (head1 == null && head2 == null) {
            return true;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(head1);
        queue.offer(head2);
        while (!queue.isEmpty()) {
            TreeNode cur1 = queue.poll();
            TreeNode cur2 = queue.poll();

            if (cur1 == null && cur2 == null) {
                continue;
            }

            if ((cur1 == null ^ cur2 == null) || cur1.val != cur2.val) {
                return false;
            }

            queue.offer(cur1.left);
            queue.offer(cur2.right);

            queue.offer(cur1.right);
            queue.offer(cur2.left);
        }
        return true;
    }

}
