package BTTraversalQuestions;

public class BTMinDepth {

    public static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;
    }

    public int run(TreeNode root) {
        // write code here
        return process(root);
    }

    public static int process(TreeNode head) {
        if (head == null) {
            return 0;
        }
        int left = process(head.left);
        int right = process(head.right);
        if (left != 0 && right != 0) {
            return Math.min(left, right) + 1;
        } else if (left != 0) {
            return left + 1;
        } else {
            return right + 1;
        }
    }

}
