package BTTraversalQuestions;

import java.util.LinkedList;

public class isCBT {

    public static class Node {
        int val;
        Node left;
        Node right;

        public Node(int val) {
            this.val = val;
        }
    }


    public static boolean isCBT2(Node root) {
        return process(root).isCBT;
    }


    public static class Info {
        int height;
        boolean isFull;
        boolean isCBT;

        public Info(int height, boolean isFull, boolean isCBT) {
            this.height = height;
            this.isFull = isFull;
            this.isCBT = isCBT;
        }
    }

    public static Info process(Node root) {
        if (root == null) {
            return new Info(0, true, true);
        }

        Info left = process(root.left);
        Info right = process(root.right);

        int height = Math.max(left.height, right.height) + 1;

        boolean isFull = left.isFull && right.isFull && (left.height == right.height);

        boolean isCBT = false;

        if (left.isFull && right.isCBT && left.height == right.height) {
            isCBT = true;
        }
        if (left.isCBT && right.isFull && left.height == right.height + 1) {
            isCBT = true;
        }

        return new Info(height, isFull, isCBT);
    }


    // for test
    public static boolean isCBT1(Node head) {
        if (head == null) {
            return true;
        }
        LinkedList<Node> queue = new LinkedList<>();
        // 是否遇到过左右两个孩子不双全的节点
        boolean leaf = false;
        Node l = null;
        Node r = null;
        queue.add(head);
        while (!queue.isEmpty()) {
            head = queue.poll();
            l = head.left;
            r = head.right;
            if (
                // 如果遇到了不双全的节点之后，又发现当前节点不是叶节点
                    (leaf && (l != null || r != null))
                            ||
                            (l == null && r != null)

            ) {
                return false;
            }
            if (l != null) {
                queue.add(l);
            }
            if (r != null) {
                queue.add(r);
            }
            if (l == null || r == null) {
                leaf = true;
            }
        }
        return true;
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
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isCBT1(head) != isCBT2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }


}
