package linkedList;

import java.util.Stack;

/**
 * 提供 笔试空间复杂度O（N）解法 + 面试 空间复杂度O（1）解法
 */
public class IsPalindromeList {

    // O（N）额外空间复杂度
    public static boolean isPalindrome1(Node head) {
        if (head == null) {
            return true;
        }
        Stack<Node> stack = new Stack<>();
        Node cur = head;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        while (head != null) {
            if (head.value != stack.pop().value) {
                return false;
            }
            head = head.next;
        }
        return true;
    }


    // O(1)额外空间复杂度
    public static boolean isPalindrome2(Node head) {
        if (head == null || head.next == null) {
            return true;
        }

        Node slow = head.next;
        Node fast = head.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        boolean ans = true;
        Node end = reverseLinkedList(slow);
        Node p2 = end;
        Node p1 = head;
        while (p1 != null && p2 != null) {
            if (p1.value != p2.value) {
                ans = false;
                break;
            }
            p1 = p1.next;
            p2 = p2.next;
        }
        reverseLinkedList(end);
        return ans;
    }

    public static Node reverseLinkedList(Node head) {
        Node pre = null;
        Node cur = head;
        while (cur != null) {
            Node next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }


}
