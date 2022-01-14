package graph;

import java.util.HashSet;
import java.util.Stack;

public class DFS {

    // 入栈时打印,入栈时入set
    public static void dfsNoRecursive(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        HashSet<Node> set = new HashSet<>();
        // init
        stack.push(head);
        set.add(head);
        System.out.print(head.val + " ");
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            for (Node next : cur.nexts) {
                if (!set.contains(next)) {
                    stack.push(cur);
                    stack.push(next);
                    set.add(next);
                    System.out.print(next.val + " ");
                    break;
                }
            }
        }
    }


    // 递归
    public static void dfsRecursive(Node head) {
        if (head == null) {
            return;
        }
        HashSet<Node> set = new HashSet<>();
        process(head, set);
    }

    // 打印 head 为头节点的整个子图 （自带base case, 当 node.nexts 长度为0时，递归自动终止（配合set去重复防环））
    // 分解子问题： 先打印当前节点，然后，循环遍历孩子节点，
    public static void process(Node head, HashSet<Node> set) {
        if (head == null) {
            return;
        }
        set.add(head); // 【错误之处】 第一次写的时候忘记了，一定要把 节点 加入到set中去
        System.out.print(head.val + " ");
        for (Node next : head.nexts) {
            if (!set.contains(next)) {
                process(next, set);
            }
        }
    }


    public static void main(String[] args) {
        int[][] matrix = {{2,3,5},
                          {1,7,9},
                          {5,4,6},
                          {3,7,2},
                          {3,2,3},
                          {9,5,1},
                          {2,5,7},
                          {9,3,4}};
        Graph graph = GraphGenerator.generateGraph(matrix);
        Node head = graph.nodes.get(2);
        dfsNoRecursive(head);
        System.out.println();
        dfsRecursive(head);
    }

}
