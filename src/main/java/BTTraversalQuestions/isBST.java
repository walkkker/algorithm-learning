package BTTraversalQuestions;

import java.util.ArrayList;

public class isBST {
    /*
    一棵树是二叉搜索树（经典BST中无重复元素）：
    1. 左树是BST
    2. 右数是BST
    3. 左树的最大值 < cur.val
    4. 右树的最小值 > cur.val
     */


    public static boolean isBST2(Node root) {
        if (root == null) {
            return true;
        }
        return process(root).isBST;
    }

    public static class Info {
        int max;
        int min;
        boolean isBST;

        public Info(int max, int min, boolean isBST) {
            this.max = max;
            this.min = min;
            this.isBST = isBST;
        }
    }

    public static Info process(Node root) {
        if (root == null) {
            return null;
        }
        Info left = process(root.left);
        Info right = process(root.right);

        int max = root.val;
        int min = root.val;
        if (left != null) {
            max = Math.max(max, left.max);
            min = Math.min(min, left.min);
        }
        if (right != null) {
            max = Math.max(max, right.max);
            min = Math.min(min, right.min);
        }

        boolean isBST = false;

        // 当 left OR right == null时，则对应的boolean变量不应该影响最终结果，所以直接返回true即可
        // 可以直接想 left == null & right = null的情况，于是四项全为true;
        boolean leftIsBST = left == null ? true : left.isBST;
        boolean rightIsBST = right == null ? true : right.isBST;
        boolean moreLeftMax = left == null ? true : root.val > left.max;
        boolean lessRightMin = right == null ? true : root.val < right.min;
        if (leftIsBST && rightIsBST && moreLeftMax && lessRightMin) {
            isBST = true;
        }
        return new Info(max, min, isBST);
    }

    // 方法二
    // 中序遍历， 看 是否 中序遍历结果 是有序的
    public static boolean isBST1(Node head) {
        if (head == null) {
            return true;
        }
        ArrayList<Node> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).val <= arr.get(i - 1).val) {
                return false;
            }
        }
        return true;
    }

    public static void in(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }

    // test

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isBST1(head) != isBST2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
