package binaryTree;

public class SuccessorNode {

    public static class Node{
        int value;
        Node left;
        Node right;
        Node parent;

        public Node(int v) {
            value = v;
        }

    }


    // 有两种情况，1. 存在右子树的情况下，取右子树的最左节点
    // 2. 无右子树的情况下， 寻找 node作为左树姿态情况下的， 第一个父节点 -> 如果有，则父节点为后继节点； 如果无，则无后继节点
    public static Node getSuccessorNode(Node node) {
        if (node == null) {
            return null;
        }
        Node ans;
        // 右子树存在
        if (node.right != null) {
            // 下面这句错了！！！
            //ans = getMostLeft(node);
            ans = getMostLeft(node.right);
        } else {
            // 右子树不存在，找node作为左树姿态下的 第一个父节点
            Node parent = node.parent;
            while (parent != null && parent.left != node) {
                node = parent;
                parent = parent.parent;
            }
//            if (node.parent == null) {
//                ans = null;
//            } else {
//                ans = node.parent;
//            }
            ans = parent;
        }
        return ans;
    }

    public static Node getMostLeft(Node node) {
        if (node == null) {
            return null;
        }
        Node pre = node;
        while (node != null) {
            pre = node;
            node = node.left;
        }
        return pre;
    }



    public static void main(String[] args) {
        Node head = new Node(6);
        head.parent = null;
        head.left = new Node(3);
        head.left.parent = head;
        head.left.left = new Node(1);
        head.left.left.parent = head.left;
        head.left.left.right = new Node(2);
        head.left.left.right.parent = head.left.left;
        head.left.right = new Node(4);
        head.left.right.parent = head.left;
        head.left.right.right = new Node(5);
        head.left.right.right.parent = head.left.right;
        head.right = new Node(9);
        head.right.parent = head;
        head.right.left = new Node(8);
        head.right.left.parent = head.right;
        head.right.left.left = new Node(7);
        head.right.left.left.parent = head.right.left;
        head.right.right = new Node(10);
        head.right.right.parent = head.right;

        Node test = head.left.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.left.left.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.left.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.left.right.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.right.left.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.right.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.right.right; // 10's next is null
        System.out.println(test.value + " next: " + getSuccessorNode(test));
    }

}
