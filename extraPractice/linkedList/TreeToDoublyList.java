package extraPractice.linkedList;

public class TreeToDoublyList {

    public static class Node {
        public int val;
        public Node left;
        public Node right;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val,Node _left,Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    };


    public static class Solution {
        Node pre, head;
        public Node treeToDoublyList(Node root) {
            if (root == null) {
                return null;
            }
            process(root);
            head.left = pre;
            pre.right = head;
            return head;
        }

        public void process(Node root) {
            if (root == null) {
                return;
            }
            process(root.left);

            if (pre == null) {
                head = root;
            } else {
                pre.right = root;
                root.left = pre;
            }
            pre = root;
            process(root.right);
        }
    }

}
