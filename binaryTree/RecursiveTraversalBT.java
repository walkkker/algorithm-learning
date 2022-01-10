package binaryTree;

public class RecursiveTraversalBT {

    public static class Node{
        int val;
        Node left;
        Node right;

        public Node(int v) {
            val = v;
            left = null;
            right = null;
        }
    }

    // 递归序
    // 递归函数
    public static void f(Node head) {
        // base case  所以，对于任何非null 节点， 均会 经过 三次！
        if (head == null) {
            return;
        }
        // 1
        f(head.left);
        // 2
        f(head.right);
        // 3
    }

    // 先序 中序 后序 打印所有节点
    public static void pre(Node head) {
        if (head == null) {
            return;
        }
        System.out.println(head.val);
        pre(head.left);
        pre(head.right);
    }

    public static void in(Node head) {
        if (head == null) {
            return;
        }
        in(head.left);
        System.out.println(head.val);
        in(head.right);
    }

    public static void post(Node head) {
        if (head == null) {
            return;
        }
        post(head.left);
        post(head.right);
        System.out.println(head.val);
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        pre(head);
        System.out.println("========");
        in(head);
        System.out.println("========");
        post(head);
        System.out.println("========");

    }



}
