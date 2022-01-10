package stackImplementation;

import java.util.Queue;
import java.util.LinkedList;

public class OneQueueImplementStack {

    public static class MyStack<T>{
        Queue<T> queue = new LinkedList<>();

        // isEmpty(), int Size(), push(), pop(), peek()

        public boolean isEmpty() {
            return queue.isEmpty();
        }

        public int size() {
            return queue.size();
        }

        //使新入队的元素，变为队首。 从而poll时先出， 从而实现【后进先出】
        public void push(T val) {
            queue.offer(val); // 到达队尾
            int times = queue.size() - 1;
            while (times > 0) {
                queue.offer(queue.poll());
                times--;
            }
        }

        public T pop() {
            return queue.poll();
        }

        public T peek() {
            return queue.peek();
        }
    }

    public static void main(String[] args) {
        MyStack<Integer> stack = new MyStack<>();
        stack.push(1);
        stack.push(3);
        stack.push(5);
        System.out.println(stack.peek()); // 5
        while (!stack.isEmpty()) {
            System.out.println(stack.pop()); // 5 3 1
        }
    }


}
