package linkedList;


/**
 * 提供两种删除节点方式， 第一种更直观一些，定义pre,cur变量实现
 * 核心： 都是 保留前一个节点
 */
public class DeleteGivenValue {

    // 实现： 遍历某个节点时，能够删除该节点
    // 做法： 定义 pre, cur。使用if分支。
    // 每次遍历： 更新 cur
    public static Node deleteGivenValue(Node head, int num) {
        // 特殊情况 排除
        if (head == null) {
            return null;
        }
        // 首先检测 开头是否要删除， 直到找到 第一个 不需要删除的头节点
        while(head != null) {
            if (head.value == num) {
                head = head.next;
            } else {
                break;
            }
        }
        // 注意 此时有可能 head == null
        Node pre = head;
        Node cur = head;
        while (cur != null) {
            if (cur.value == num) {
                pre.next = cur.next;
            } else {
                pre = cur;
            }
            cur = cur.next;
        }
        return head;
    }

    // 删除节点方式 上面的版本 更合适
    // 每次遍历检查： cur.next，所以删除节点时，cur.next自动更新，不需要移动；不删除节点时，cur=cur.next，从而手动更新cur.next
    public static Node removeValue(Node head, int num) {
        while(head != null) {
            if (head.value == num) {
                head = head.next;
            } else {
                break;
            }
        }
        Node cur = head;
        // 不断更新 cur.next
        while (cur != null && cur.next != null) {
            if (cur.next.value == num) {
                cur.next = cur.next.next;
            } else{
                cur = cur.next;
            }
        }
        return head;
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(3);

        head= deleteGivenValue(head,2);
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }
    }
}
