package linkedList;

public class MergeTwoSortedLinkedList {

    public static Node mergeTwoLists(Node head1, Node head2) {
        // 任意一个节点为空， 返回另一个节点
        if (head1 == null || head2 == null) {
            return head1 == null ? head2 : head1;
        }
        // 首先获取最小节点，作为head
        Node head = head1.value <= head2.value ? head1 : head2;
        Node cur1 = head.next;
        Node cur2 = head == head1 ? head2 : head1;

        //从此 两个head和返回头都确定了
        Node pre = head;
        while (cur1 != null && cur2 != null) {
            if (cur1.value < cur2.value) {
                pre.next = cur1;
                cur1 = cur1.next;
            } else {
                pre.next = cur2;
                cur2 = cur2.next;
            }
            pre = pre.next;
        }
        pre.next = cur1 != null ? cur1 : cur2;
        return head;
    }

}
