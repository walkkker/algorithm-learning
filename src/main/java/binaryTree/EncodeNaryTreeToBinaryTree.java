package binaryTree;

import java.util.ArrayList;
import java.util.List;

/**
 * 该题待 提交检验！ 检测正误 https://leetcode-cn.com/problems/serialize-and-deserialize-n-ary-tree/
 */
public class EncodeNaryTreeToBinaryTree {

    public static class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    };

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static TreeNode encode(Node root) {
        if (root == null) {
            return null;
        }
        TreeNode head = new TreeNode(root.val);
        head.left = en(root.children);
        return head;
    }

    /*
    建立一个 二叉树，所有 children节点都在右边界上，返回头节点
     */
    public static TreeNode en(List<Node> children) {
        TreeNode head = null;
        TreeNode cur = null;
        for (Node child : children) {
            TreeNode node = new TreeNode(child.val);
            node.left = en(child.children);
            if (head == null) {
                head = node;
                cur = head;
            } else {
                cur.right = node;
                cur = cur.right;
            }
        }
        return head;
    }


    public static Node decode(TreeNode root) {
        if (root == null) {
            return null;
        }
        Node head = new Node(root.val, de(root.left));
        return head;
    }

    // 传入 左树头节点， de 将右边界上的所有节点 转化为 List<Node>返回。 并且要求，每个List中的节点，都是设置好孩子的
    public static List<Node> de(TreeNode root) {
        List<Node> children = new ArrayList<>();
        while (root != null) {
            children.add(new Node(root.val, de(root.left)));
            root = root.right;
        }
        return children;
    }





}
