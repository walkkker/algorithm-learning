package linkedList;


import java.util.HashMap;

/**
 * O（N）空间复杂度 --> 哈希表
 * O（1）空间复杂度 --> 1 1‘ 2 2‘ 3 3’
 */
public class CopyListWithRandom {

    public static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }


    // 空间复杂度 O(n): 哈希表
    public static Node copyRandomList1(Node head) {
        if (head == null) {
            return null;
        }
        HashMap<Node, Node> map = new HashMap<>();
        Node cur = head;
        while (cur != null) {
            map.put(cur, new Node(cur.val));
            cur = cur.next;
        }
        // 因为 random和next可以指向null, 所以也添加进去
        map.put(null, null);
        cur = head;
        while (cur != null) {
            map.get(cur).next = map.get(cur.next);
            map.get(cur).random = map.get(cur.random);
            cur = cur.next;
        }
        return map.get(head);
    }


    // 空间复杂度 O(1) 1 1' 2 2' 3 3'
    public static Node copyRandomList2(Node head) {
        if (head == null) {
            return null;
        }

        Node cur = head;
        // STEP1: 1 2 3 4 --> 1 1' 2 2' 3 3' 4 4'
        while (cur != null) {
            Node next = cur.next;
            cur.next = new Node(cur.val);
            cur.next.next = next;
            cur = next;
        }

        // STEP2: 配置 random
        cur = head;
        while (cur != null) {
            Node next = cur.next.next;
            cur.next.random = cur.random == null ? null : cur.random.next;
            cur = next;
        }

        // STEP3: 分离 old 和 new， 完成最终next配置
        Node ans = head.next;
        cur = head;
        while (cur != null) {
            Node next = cur.next.next;
            Node copy = cur.next;
            copy.next = next == null ? null : next.next;
            cur.next = next;
            cur = next;
        }
        return ans;
    }



}
