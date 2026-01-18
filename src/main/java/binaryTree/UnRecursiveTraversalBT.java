package binaryTree;

import java.util.Stack;


/**
 * 下面的代码，都是 传入参数 为 Node head; 然后后面的部分， 全部复用 head！！！ 说这个是为了记忆正确
 */
public class UnRecursiveTraversalBT {

    public static class Node{
        int val;
        Node left;
        Node right;

        public Node(int v) {
            val = v;
        }
    }

    // 先序非递归遍历
    // 手动创建栈
    // 1. 首先将头节点压入栈中
    // 2. 只要栈不为空，就从栈中弹出节点，弹出的节点即为要打印的节点
    // 3. 然后 对于弹出节点cur, 有右进右， 有左进左，（从而满足 头左右的弹出顺序）
    public static void preUnRecursive(Node head) {
        System.out.println("pre order:");
        if (head != null) {
            Stack<Node> stack = new Stack<>();
            // 头压栈 ， right left
            stack.push(head);
            while (!stack.isEmpty()) {
                // head 复用
                head = stack.pop();
                System.out.print(head.val + " ");
                if (head.right != null) {
                    stack.push(head.right);
                }
                if (head.left != null) {
                    stack.push(head.left);
                }
            }
        }
        System.out.println();
    }


    // 中序遍历
    // 记住代码, 关键就是 走 左边界
    // 中间的 while 条件是 (!stack.isEmpty() || cur != null)
    public static void in(Node head) {
        System.out.println("in order: ");
        if (head != null) {
            Stack<Node> stack = new Stack<>();
            while (!stack.isEmpty() || head != null) {
                if (head != null) {
                    stack.push(head);
                    // 将 【左边界】 依次 压入 栈中，直到head==null
                    head = head.left;
                } else {
                    head = stack.pop();
                    System.out.print(head.val + " ");
                    head = head.right;
                }
            }
        }
        System.out.println();
    }


    // 后序遍历， 借鉴了先序遍历
    // 先序遍历： 头左右； 后序遍历： 左右头
    // 所以，准备两个栈， 第一个栈实现【头右左】的输出顺序（实现方法与先序非递归相同，只不过是 先压左后压右），第二个栈 用作逆序，实现 头右左 -> 左右头
    // 有了 头右左，  只需要在 处理当前节点时，不打印，而是将他们依次压入栈中，从而就实现了逆序； 依次从第二个栈打印出来后，就为 左右头了
    public static void pos(Node head) {
        System.out.println("post-order traversal: ");
        if (head != null) {
            Stack<Node> stack1 = new Stack<>();
            Stack<Node> stack2 = new Stack<>();
            stack1.push(head);
            while (!stack1.isEmpty()) {
                head = stack1.pop();
                stack2.push(head);
                if (head.left != null) {
                    stack1.push(head.left);
                }
                if (head.right != null) {
                    stack1.push(head.right);
                }
            }
            while (!stack2.isEmpty()) {
                head = stack2.pop();
                System.out.print(head.val + " ");
            }
            System.out.println();
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

        preUnRecursive(head);
        System.out.println("========");
        in(head);
        System.out.println("========");
        pos(head);
        System.out.println("========");
        //pos2(head);
        System.out.println("========");
    }








}
