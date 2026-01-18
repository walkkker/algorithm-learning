package BTTraversalQuestions;

import java.util.ArrayList;

public class MaxSubBSTHead {

    public static Node maxSubBSTHead2(Node root) {
        if (root == null) {
            return null;
        }
        return process(root).maxSubBSTHead;
    }

    public static class Info {
        int max;
        int min;
        boolean isBST;
        int maxSubBSTSize;
        Node maxSubBSTHead;

        public Info(boolean isBST, int max, int min, int maxSubBSTSize, Node maxSubBSTHead) {
            this.isBST = isBST;
            this.max = max;
            this.min = min;
            this.maxSubBSTSize = maxSubBSTSize;
            this.maxSubBSTHead = maxSubBSTHead;
        }
    }

    public static Info process(Node head) {
        if (head == null) {
            return null;
        }
        Info left = process(head.left);
        Info right = process(head.right);

        int max = head.val;
        int min = head.val;

        if (left != null) {
            max = Math.max(max, left.max);
            min = Math.min(min, left.min);
        }
        if (right != null) {
            max = Math.max(max, right.max);
            min = Math.min(min, right.min);
        }

        boolean isBST = false;
        boolean leftIsBST = left == null ? true : left.isBST;
        boolean rightIsBST = right == null ? true : right.isBST;
        boolean moreLeftMax = left == null ? true : head.val > left.max;
        boolean lessRightMin = right == null ? true : head.val < right.min;
        if (leftIsBST && rightIsBST && moreLeftMax && lessRightMin) {
            isBST = true;
        }

        int p1 = left == null ? -1 : left.maxSubBSTSize;
        int p2 = right == null ? -1 : right.maxSubBSTSize;
        int p3 = -1;
        if (isBST) {
            p3 = 1;
            if (left != null) {
                p3 += left.maxSubBSTSize;
            }
            if (right != null) {
                p3 += right.maxSubBSTSize;
            }
        }
        int maxSubBSTSize = Math.max(p1, Math.max(p2, p3));

        Node maxSubBSTHead;
        if (isBST) {
            maxSubBSTHead = head;
        } else {
            maxSubBSTHead = p1 >= p2 ? left.maxSubBSTHead : right.maxSubBSTHead;
        }

        // 求结果 1.无关 ->左树结果 + 右树结果   2.有关

        return new Info(isBST, max, min, maxSubBSTSize, maxSubBSTHead);
    }


    // test

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

    public static Node maxSubBSTHead1(Node head) {
        if (head == null) {
            return null;
        }
        if (getBSTSize(head) != 0) {
            return head;
        }
        Node leftAns = maxSubBSTHead1(head.left);
        Node rightAns = maxSubBSTHead1(head.right);
        return getBSTSize(leftAns) >= getBSTSize(rightAns) ? leftAns : rightAns;
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
            if (maxSubBSTHead1(head) != maxSubBSTHead2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }






}
