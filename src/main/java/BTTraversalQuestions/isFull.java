package BTTraversalQuestions;

public class isFull {

    /*
        判断满二叉树：
        1. 左满
        2. 右满
        3. 左高度 == 右高度
     */

    public static boolean isFull2(Node root) {
        return process(root).isFull;
    }

    public static class Info {
        int height;
        boolean isFull;

        public Info(int height, boolean isFull) {
            this.height = height;
            this.isFull = isFull;
        }
    }

    public static Info process(Node root) {
        if (root == null) {
            return new Info(0, true);
        }
        Info left = process(root.left);
        Info right = process(root.right);

        int height = Math.max(left.height, right.height) + 1;
        boolean isFull = false;
        if (left.isFull && right.isFull && left.height == right.height) {
            isFull = true;
        }
        return new Info(height, isFull);
    }


    // test

    public static boolean isFull1(Node head) {
        if (head == null) {
            return true;
        }
        int height = h(head);
        int nodes = n(head);
        return (1 << height) - 1 == nodes;
    }

    public static int h(Node head) {
        if (head == null) {
            return 0;
        }
        return Math.max(h(head.left), h(head.right)) + 1;
    }

    public static int n(Node head) {
        if (head == null) {
            return 0;
        }
        return n(head.left) + n(head.right) + 1;
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
            if (isFull1(head) != isFull2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }



}
