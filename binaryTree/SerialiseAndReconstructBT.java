package binaryTree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


/*
 * 二叉树可以通过先序、后序或者按层遍历的方式序列化和反序列化，
 * 以下代码全部实现了。
 * 但是，二叉树无法通过中序遍历的方式实现序列化和反序列化
 * 因为不同的两棵树，可能得到同样的中序序列，即便补了空位置也可能一样。
 * 比如如下两棵树
 *         __2
 *        /
 *       1
 *       和
 *       1__
 *          \
 *           2
 * 补足空位置的中序遍历结果都是{ null, 1, null, 2, null}
 *
 * */
public class SerialiseAndReconstructBT {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int v) {
            val = v;
        }
    }


    // 先序遍历 （1）序列化
    public static String preSerial(TreeNode head) {
        Queue<String> queue = new LinkedList<>();
        pre(head, queue);
        StringBuilder str = new StringBuilder();
        while (!queue.isEmpty()) {
            str.append(queue.poll());
            str.append(",");
        }
        return str.toString();
    }

    public static void pre(TreeNode head, Queue<String> queue) {
        if (head == null) {
            queue.offer("null");
            return;
        }
        queue.offer(String.valueOf(head.val));
        pre(head.left, queue);
        pre(head.right, queue);
    }

    // 先序遍历（2） 反序列化
    public static TreeNode preDeSerial(String str) {
        String[] strArr = str.split(",");
        Queue<String> queue = new LinkedList<>();
        for (String s : strArr) {
            queue.offer(s);
        }
        return preDe(queue);
    }

    public static TreeNode preDe(Queue<String> queue) {
        String cur = queue.poll();
        if (cur.equals("null")) {
            return null;
        }
        TreeNode curNode = new TreeNode(Integer.valueOf(cur));
        curNode.left = preDe(queue);
        curNode.right = preDe(queue);
        return curNode;
    }



    /*
        后序遍历（1） 序列化
     */
    public static String posSerial(TreeNode head) {
        Queue<String> ans = new LinkedList<>();
        pos(head, ans);
        StringBuffer str = new StringBuffer();
        while (!ans.isEmpty()) {
            str.append(ans.poll());
            str.append(",");
        }
        return str.toString();
    }

    public static void pos(TreeNode head, Queue<String> ans) {
        if (head == null) {
            ans.offer("null");
            return;
        }
        pos(head.left, ans);
        pos(head.right, ans);
        ans.offer(String.valueOf(head.val));
    }

    public static TreeNode posDeserial(String str) {
        Stack<String> stack = new Stack<>();
        String[] strArr = str.split(",");
        for (String s : strArr) {
            stack.push(s);
        }
        // todo
        return pos2(stack);
    }

    public static TreeNode pos2(Stack<String> stack) {
        String curStr = stack.pop();
        if (curStr.equals("null")) {
            return null;
        }
        TreeNode cur = new TreeNode(Integer.valueOf(curStr));
        cur.right = pos2(stack);
        cur.left = pos2(stack);
        return cur;
    }


    // 层次遍历 序列化与反序列化
    // 因为要记录null， 所以是没有办法在【本节点】记录的，所以要在【遍历父节点】的时候，将 子节点 记录到字符串中
    public static String levelSerial(TreeNode root) {
        if (root == null) {
            return "null,";
        }
        StringBuilder ans = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        ans.append(root.val + ",");
        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            if (cur.left != null) {
                queue.offer(cur.left);
                ans.append(cur.left.val + ",");
            } else {
                ans.append("null,");
            }

            if (cur.right != null) {
                queue.offer(cur.right);
                ans.append(cur.right.val + ",");
            } else {
                ans.append("null,");
            }
        }
        return ans.toString();
    }

    public static TreeNode deSerialise(String s) {
        String[] strArr = s.split(",");
        if (strArr[0].equals("null")) {
            return null;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        Queue<String> help = new LinkedList<>();
        for (String str : strArr) {
            help.offer(str);
        }
        TreeNode head = generateNode(help.poll());
        queue.offer(head);
        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            cur.left = generateNode(help.poll());
            cur.right = generateNode(help.poll());
            if (cur.left != null) {
                queue.offer(cur.left);
            }
            if (cur.right != null) {
                queue.offer(cur.right);
            }
        }
        return head;
    }

    public static TreeNode generateNode(String str) {
        if (str.equals("null")) {
            return null;
        } else {
            return new TreeNode(Integer.valueOf(str));
        }
    }





    public static void main(String[] args) {
        String str = "null,null,2,null,null,4,null,null,5,3,1,";
        TreeNode head = posDeserial(str);
        System.out.println(head.val);
    }

}
