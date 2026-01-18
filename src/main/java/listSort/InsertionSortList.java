package listSort;

import java.util.ArrayList;

/**
 * 链表的插入排序： 包含 额外空间复杂度O(N), 以及 额外空间复杂度O（1）【很重要的】
 */
public class InsertionSortList {

    public static class ListNode {
        int val;
        ListNode next;

        public ListNode(int v) {
            val = v;
        }
    }

    // 额外空间复杂度 O（N）
    public ListNode insertionSortList (ListNode head) {
        // write code here
        ArrayList<ListNode> arr = new ArrayList<>();
        ListNode cur = head;
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        for (int end = 1; end < arr.size(); end++) {
            for (int i = end; i - 1 >= 0 && arr.get(i).val < arr.get(i - 1).val; i--) {
                swap(arr, i, i - 1);
            }
        }
        // dummy Node
        head = new ListNode(0);
        cur = head;
        for (int i = 0; i < arr.size(); i++) {
            cur.next = arr.get(i);
            cur = cur.next;
        }
        // 一定要注意 排序后的最后一个节点 的next指针 要设为null，不然会出现环
        cur.next = null;
        return head.next;
    }

    public static void swap(ArrayList<ListNode> arr, int i, int j) {
        ListNode tmp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, tmp);
    }

    // O(1) 空间复杂度
    public static ListNode insertionSortList2(ListNode head) {
        if (head == null) {
            return null;
        }
        // 因为排序 有可能换头，所以创建 dummy head
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        ListNode lastSorted = head;
        ListNode cur = lastSorted.next;
        while (cur != null) {
            // 讨论 两种情况： (1) cur >= lastSorted, lastSorted 直接后移
            //              (2) cur插入中间，lastSorted不动
            if (cur.val >=  lastSorted.val) {
                lastSorted = lastSorted.next;
            } else {
                // 插入情况
                ListNode pre = dummyHead;
                // 因为一定能够找到比 cur.val 大 的节点，所以不需要判断边界 !=null
                // 从左往右，找到第一个比 cur.val 大的数字
                while (pre.next.val <= cur.val) {
                    pre = pre.next;
                }
                // 开始拼接
                //(1) 删除cur节点 (2)在pre后面，插入cur节点
                lastSorted.next = cur.next;
                cur.next = pre.next;
                pre.next = cur;
            }
            // 新的 cur， 排好序的部分 的 下一个元素
            cur = lastSorted.next;
        }
        return dummyHead.next;
    }
}
