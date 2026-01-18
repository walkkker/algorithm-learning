package linkedList;

public class ReverseNodesInKGroup {

    public Node reverseKGroup(Node head, int k) {
        // 总共就三个变量 start, end, lastEnd(设计的很巧妙)
        
        Node start = head;
        Node end = getKNode(start, k);
        head = end == null ? head : end;
        myReverse(start, end);
        Node lastEnd = start;
        
        while (lastEnd.next != null) {
            start = lastEnd.next;
            end = getKNode(start, k);
            if (end == null) {
                return head;
            }
            myReverse(start, end);
            lastEnd.next = end;
            lastEnd = start;
        }
        return head;
    }


    public static Node getKNode(Node start, int k) {
        while (--k > 0 && start != null) {
            start = start.next;
        }
        // 循环跳出时 两种情况： 1. start==null（包含中间退出 和 到达第K个节点）;     2.start == KthNode
        return start;
    }

    // 上游 保证 start,end != null；
    public static void myReverse(Node start, Node end) {
        end = end.next;
        Node pre = null;
        Node cur = start;
        while (cur != end) {
            Node next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        start.next = end;
    }

}
