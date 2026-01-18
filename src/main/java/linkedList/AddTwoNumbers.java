package linkedList;

public class AddTwoNumbers {

    /*
        三个阶段：
        1. 短先走完
        2. 长走完
        3. 判断是否最后还有进位，有的话，最后一个节点后面创建新的节点
     */

    public static Node addTwoNums(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return head1 == null ? head2 : head1;
        }
        Node p1 = getListLength(head1) > getListLength(head2) ? head1 : head2;
        Node p2 = p1 == head1 ? head2 : head1;
        head1 = p1;
        int carry = 0;
        int value = 0;
        Node pre = p1;
        // 第一阶段
        while (p2 != null) {
            value = (p1.value + p2.value + carry) % 10;
            carry = (p1.value + p2.value + carry) / 10;
            p1.value = value;
            pre = p1;
            p1 = p1.next;
            p2 = p2.next;
        }
        // 第二阶段
        while (p1 != null) {
            value = (p1.value + carry) % 10;
            carry = (p1.value + carry) / 10;
            p1.value = value;
            pre = p1;
            p1 = p1.next;
        }
        // 退出时，p1 == null,而pre指向末尾元素
        // 第三阶段
        if (carry > 0) {
            pre.next = new Node(1);
        }
        return head1;
    }


    public static int getListLength(Node head) {
        int n = 0;
        while (head != null) {
            n++;
            head = head.next;
        }
        return n;
    }
}
