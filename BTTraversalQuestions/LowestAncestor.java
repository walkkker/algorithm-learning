package BTTraversalQuestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class LowestAncestor {
    /*
    【1】与X无关 （左右子树已经有ancestor）； 【2】与X有关（X为ancestor）

    Info；
    boolean containsA;
    boolean containsB;
    Node ancestor;
     */

    public static Node lowestAncestor2(Node root, Node A, Node B) {
        return process(root, A, B).ancestor;
    }


    public static class Info {
        boolean containsA;
        boolean containsB;
        Node ancestor;

        public Info(boolean conA, boolean conB, Node ancestor) {
            containsA = conA;
            containsB = conB;
            this.ancestor = ancestor;
        }
    }

    public static Info process(Node root, Node A, Node B) {
        if (root == null) {
            return new Info(false, false, null);
        }
        Info left = process(root.left, A, B);
        Info right = process(root.right, A, B);

        boolean containsA = false;
        if (left.containsA || right.containsA || root == A ) {
            containsA = true;
        }
        boolean containsB = false;
        if (left.containsB || right .containsB || root == B) {
            containsB = true;
        }

        Node ancestor = null;
        // 有关 无关
        if (left.ancestor != null || right.ancestor != null) {
            ancestor = left.ancestor != null ? left.ancestor : right.ancestor;
        } else {
            if (containsA && containsB) {
                ancestor = root;
            }
        }
        return new Info(containsA, containsB, ancestor);
    }


    // TEST
    public static Node lowestAncestor1(Node head, Node o1, Node o2) {
        if (head == null) {
            return null;
        }
        // key的父节点是value
        HashMap<Node, Node> parentMap = new HashMap<>();
        parentMap.put(head, null);
        fillParentMap(head, parentMap);
        HashSet<Node> o1Set = new HashSet<>();
        Node cur = o1;
        o1Set.add(cur);
        while (parentMap.get(cur) != null) {
            cur = parentMap.get(cur);
            o1Set.add(cur);
        }
        cur = o2;
        while (!o1Set.contains(cur)) {
            cur = parentMap.get(cur);
        }
        return cur;
    }

    public static void fillParentMap(Node head, HashMap<Node, Node> parentMap) {
        if (head.left != null) {
            parentMap.put(head.left, head);
            fillParentMap(head.left, parentMap);
        }
        if (head.right != null) {
            parentMap.put(head.right, head);
            fillParentMap(head.right, parentMap);
        }
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

    // for test
    public static Node pickRandomOne(Node head) {
        if (head == null) {
            return null;
        }
        ArrayList<Node> arr = new ArrayList<>();
        fillPrelist(head, arr);
        int randomIndex = (int) (Math.random() * arr.size());
        return arr.get(randomIndex);
    }

    // for test
    public static void fillPrelist(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        arr.add(head);
        fillPrelist(head.left, arr);
        fillPrelist(head.right, arr);
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            Node o1 = pickRandomOne(head);
            Node o2 = pickRandomOne(head);
            if (lowestAncestor1(head, o1, o2) != lowestAncestor2(head, o1, o2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
