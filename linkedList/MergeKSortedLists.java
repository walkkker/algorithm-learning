package linkedList;

import java.util.PriorityQueue;
import java.util.Comparator;


// https://leetcode-cn.com/problems/merge-k-sorted-lists/submissions/
public class MergeKSortedLists {

    public static class ListNode{
        int val;
        ListNode next;

        public ListNode(int v) {
            val = v;
        }
    }

    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> heap = new PriorityQueue<>(new MyComparator());
        for (int i = 0; i < lists.length; i++) {
            if (lists[i] != null) {
                heap.offer(lists[i]);
            }
        }
        if (heap.isEmpty()) {
            return null;
        }
        ListNode head = heap.poll();
        if (head.next != null) {
            heap.offer(head.next);
        }
        ListNode pre = head;
        while (!heap.isEmpty()) {
            pre.next = heap.poll();
            pre = pre.next;
            if (pre.next != null) {
                heap.offer(pre.next);
            }
        }
        return head;
    }

    public static class MyComparator implements Comparator<ListNode> {
        public int compare(ListNode o1, ListNode o2) {
            return o1.val - o2.val;
        }
    }
}
