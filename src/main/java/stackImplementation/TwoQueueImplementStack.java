package stackImplementation;

import java.util.LinkedList;
import java.util.Queue;

public class TwoQueueImplementStack {

    public static class MyStack {

        Queue<Integer> queue;
        Queue<Integer> help;

        public MyStack() {
            queue = new LinkedList<>();
            help = new LinkedList<>();
        }

        public void push(int x) {
            queue.offer(x);
        }

        public int pop() {
            while (queue.size() > 1) {
                help.offer(queue.poll());
            }
            int ans = queue.poll();
            Queue<Integer> tmp = queue;
            queue = help;
            help = tmp;
            return ans;
        }

        public int top() {
            while (queue.size() > 1) {
                help.offer(queue.poll());
            }
            int ans = queue.poll();
            help.offer(ans);
            Queue<Integer> tmp = queue;
            queue = help;
            help = tmp;
            return ans;
        }

        public boolean empty() {
            return queue.isEmpty();
        }
    }

}
