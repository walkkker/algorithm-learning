package BTTraversalQuestions;

import java.util.ArrayList;

public class MaxSubBSTSize {

    /*
    树型dp
    x ： 1. 与X有关；   2. 与X无关  ==》 1，2取最大
    1. 【1】以X为头的整棵树 是 BST，返回 BSTSize
    2. 左树的maxBSTSize, 右树的maxBSTSize

    需要的信息：
    (1)与X无关时，求 判断X非BST
    左： isBST max maxSubBSTSize
    右： isBST min maxSubBSTSize

    (2)与X有关时，要满足X是BST
    1. 左树 isBST max maxSubBSTSize
    2. 右树 isBST min maxSubBSTSize

    总结需要的信息： 判断BST + maxSubBSTSize
     */

    public static int maxSubBSTSize2(Node root) {
        if (root == null) {
            return 0;
        }
        return process(root).maxSubBSTSize;
    }

    public static class Info {
        int max;
        int min;
        boolean isBST;
        int maxSubBSTSize;

        public Info(boolean isBST, int max, int min, int maxSubBSTSize) {
            this.isBST = isBST;
            this.max = max;
            this.min = min;
            this.maxSubBSTSize = maxSubBSTSize;
        }
    }

    public static Info process(Node node) {
        if (node == null) {
            return null;
        }
        Info left = process(node.left);
        Info right = process(node.right);


        int max = node.val;
        int min = node.val;

        if (left != null) {
            max = Math.max(max, left.max);
            min = Math.min(min, left.min);
        }
        if (right != null) {
            max = Math.max(max, right.max);
            min = Math.min(min, right.min);
        }

        boolean leftIsBST = left == null ? true : left.isBST;
        boolean rightIsBST = right == null ? true : right.isBST;
        boolean moreLeftMax = left == null ? true : node.val > left.max;
        boolean lessRightMin = right == null ? true : node.val < right.min;

        boolean isBST = false;
        if (leftIsBST && rightIsBST
            && moreLeftMax && lessRightMin) {
            isBST = true;
        }

        int maxSubBSTSize;

        // 与X无关
        int p1 = left == null ? 0 : left.maxSubBSTSize;
        int p2 = right == null ? 0 : right.maxSubBSTSize;

        // 与X有关
        int p3 = 0;
        if (isBST) {
            p3 = 1;
            if (left != null) {
                p3 += left.maxSubBSTSize;
            }
            if (right != null) {
                p3 += right.maxSubBSTSize;
            }
        }
        maxSubBSTSize = Math.max(p1, Math.max(p2, p3));

        return new Info(isBST, max, min, maxSubBSTSize);
    }



    public static int getBSTSize(Node head) {
        if (head == null) {
            return 0;
        }
        ArrayList<Node> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).val <= arr.get(i - 1).val) {
                return 0;
            }
        }
        return arr.size();
    }

    public static void in(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }

    public static int maxSubBSTSize1(Node head) {
        if (head == null) {
            return 0;
        }
        int h = getBSTSize(head);
        if (h != 0) {
            return h;
        }
        return Math.max(maxSubBSTSize1(head.left), maxSubBSTSize1(head.right));
    }



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
            if (maxSubBSTSize1(head) != maxSubBSTSize2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }



}
