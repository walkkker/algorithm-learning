package linkedList;


/**
 *  四个子问题解决即解决总问题：空间复杂度O(1)
 * 【0】获得链表第一个入环节点（无则返回null)
 * 【1】两个无环链表相交节点
 * 【2】两个有环链表相交节点
 * 【3】一个有环链表与一个无环链表不可能相交
 */
public class FindFirstIntersectNode {


    //总问题整合： 给出任意两个链表头节点，请返回相交节点（无相交节点则返回null）
    public static Node getIntersectNode(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        // 1.判断 两个链表 是否有环。       有的话，返回入环节点；无的话，返回Null
        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);
        // 无环链表相交
        if (loop1 == null && loop2 == null) { // 两个无环链表相交
            return noLoop(head1, head2);
        } else if (loop1 != null && loop2 != null) { // 两个有环链表相交，四种情况
            return bothLoop(head1, loop1, head2, loop2);
        }
        // 剩余 一个有环，一个无环情况  =》 此种情况， 不存在相交可能性，所以无相交节点，直接返回null
        return null;
    }


    //【0】获得链表第一个入环节点（无则返回null)
    // 找到第一个入环节点， 无则返回null，意味着 链表无环
    public static Node getLoopNode(Node head) {
        // 快慢指针 第一次相遇时， slow停留原地，fast从头开始，两者STEP全部为1，向前移动，相遇时即为 入环节点
        // 若快慢指针时，fast提前终止，则说明链表无环
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node slow = head.next;
        Node fast = head.next.next;
        while (slow != fast) {
            if (fast.next == null || fast.next.next == null) {
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        fast = head;
        while (fast != slow) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    //【1】两个无环链表相交节点
    public static Node noLoop(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        int n = 0;
        Node cur1 = head1;
        Node cur2 = head2;
        while (cur1.next != null) {
            n++;
            cur1 = cur1.next;
        }
        while (cur2.next != null) {
            n--;
            cur2 = cur2.next;
        }
        if (cur1 != cur2) {
            return null;
        }
        // 说明有相交节点
        cur1 = n > 0 ? head1 : head2;
        cur2 = cur1 == head1 ? head2 : head1;
        n = Math.abs(n);
        // 先让cur1 跳过 多出来的n个
        while (n != 0) {
            n--;
            cur1 = cur1.next;
        }
        // 此时 cur1 cur2长度相同 && 一定有相交节点
        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }


    //【2】两个有环链表相交节点
    public static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {
        // 已经是有环链表了，所以都不为空
        // 考虑四种情况： 1. 非环相交 2.入环节点相交 3.两个入环节点在一个环的两个位置上 4.不相交
        if (loop1 == loop2) { // 1，2情况整合
            Node cur1 = head1;
            Node cur2 = head2;
            int n = 0;
            while (cur1 != loop1) {
                n++;
                cur1 = cur1.next;
            }
            while (cur2 != loop2) {
                n--;
                cur2 = cur2.next;
            }
            cur1 = n > 0 ? head1 : head2;
            cur2 = cur1 == head1 ? head2 : head1;
            n = Math.abs(n);
            while (n > 0) {
                cur1 = cur1.next;
                n--;
            }
            while (cur1 != cur2) {
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            return cur1;
        } else { // 3 4情况整合
            // 先处理情况3， loop1绕环一圈，检查是否有loop2，有的话，则3满足；没有的话，则说明没有相交
            Node cur = loop1.next;
            while (cur != loop1) {
                if (cur == loop2) {
                    return loop1;
                }
                cur = cur.next;
            }
            return null;
        }

    }


}
