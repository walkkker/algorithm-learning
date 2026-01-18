package binaryTree;

import java.util.LinkedList;
import java.util.Queue;


/**
 * 包含 层序遍历， 以及 层序遍历的分层打印
 */
public class LevelTraversalBT {

    public static class Node{
        int val;
        Node left;
        Node right;

        public Node(int v) {
            val = v;
        }
    }


    public static void level(Node head) {
        if (head == null) {
            return;
        }
        // head 不为空， 则 head 首先进入 queue
        Queue<Node> queue = new LinkedList<>();
        queue.offer(head);
        while (!queue.isEmpty()) {
            head = queue.poll();
            System.out.print(head.val + " ");
            if (head.left != null) {
                queue.offer(head.left);
            }
            if (head.right != null) {
                queue.offer(head.right);
            }
        }
    }


    public static void levelPro(Node head) {
        if (head == null) {
            return;
        }
        // head 不为空， 则 head 首先进入 queue
        Queue<Node> queue = new LinkedList<>();
        Node curEnd = head;
        Node nextEnd = head;
        queue.offer(head);
        while (!queue.isEmpty()) {
            head = queue.poll();
            System.out.print(head.val + " ");
            if (head.left != null) {
                queue.offer(head.left);
                nextEnd = head.left;
            }
            if (head.right != null) {
                queue.offer(head.right);
                nextEnd = head.right;
            }
            if (head == curEnd) {
                System.out.println();
                curEnd = nextEnd;
            }
        }
    }


    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        levelPro(head);
        System.out.println("========");
    }

}
