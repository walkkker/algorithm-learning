package extraPractice.linkedList;

public class ReverseBetween {


    //链表内指定区间反转
    public static class ListNode {
        int val;
        ListNode next = null;
    }


    /**
     * @param head ListNode类
     * @param m    int整型
     * @param n    int整型
     * @return ListNode类
     */
    public ListNode reverseBetween(ListNode head, int m, int n) {
        // write code here
        if (m == 1) {
            ListNode N = head;
            while (--n > 0) {
                N = N.next;
            }
            reverse(head, N);
            return N;
        }
        ListNode MPre = head;
        ListNode N = head;
        m = m - 1;
        while (--m > 0) {
            MPre = MPre.next;
        }
        while (--n > 0) {
            N = N.next;
        }
        reverse(MPre.next, N);
        MPre.next = N;
        return head;
    }

    public static void reverse(ListNode start, ListNode end) {
        end = end.next;
        ListNode pre = null;
        ListNode cur = start;
        while (cur != end) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        start.next = end;
    }


}
