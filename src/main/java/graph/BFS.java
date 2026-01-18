package graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 队列 + set（防环路，防止重复元素入队
 *
 */
public class BFS {

    public static void bfs(Node head) {
        if (head == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> set = new HashSet<>();
        // init
        queue.add(head);
        set.add(head);
        // condition 条件
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            System.out.println(cur.val);
            // nexts 入队
            for (Node next : cur.nexts) {
                if (!set.contains(next)) {
                    queue.add(next);
                    set.add(next);
                }
            }
        }
    }
}
