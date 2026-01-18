package queueImplementation;


import java.util.LinkedList;
import java.util.Queue;

/**
 * 单链表实现队列
 */
public class LinkedListToQueue {

    public static class Node<T> {
        T val;
        Node next;

        public Node(T val) {
            this.val = val;
            next = null;
        }
    }

    // 队列需要 head 和 tail，并且为了随时可以返回size，还要一个属性为size
    // 使用封装，private
    // 总结： 单链表实现队列，头尾指针，尾插法
    public static class MyQueue<T> {
        private Node<T> head;
        private Node<T> tail;
        private int size;

        public MyQueue() {
            head = null;
            tail = null;
            size = 0;
        }

        /*
        首先说明要实现哪些方法 1.isEmpty() 2. size() 3.poll()出队 4.offer() 入队 5.peek() 返回队首元素
         */
        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }

        public void offer(T val) {
            Node<T> cur = new Node(val);
            if (head == null) {
                head = cur;
                tail = cur;
            } else {
                tail.next = cur;
                tail = cur;
            }
            // 注意： ！！！ 不要忘了 【size++】
            size++;
        }

        public T poll() {
            if (size == 0) {
                return null;
            }
            T ans = head.val;
            head = head.next;
            size--;

            // ！！！注意一个场景，当size==1时，弹出之后, head == null,但是tail依然指向最后一个元素
            if (head == null) {
                tail = null;
            }
            return ans;
        }

        public T peek() {
            if (size == 0) {
                return null;
            }
            return head.val;
        }
    }


    public static void testQueue() {
        MyQueue<Integer> myQueue = new MyQueue<>();
        Queue<Integer> test = new LinkedList<>();
        int testTime = 5000000;
        int maxValue = 200000000;
        System.out.println("测试开始！");
        for (int i = 0; i < testTime; i++) {
            if (myQueue.isEmpty() != test.isEmpty()) {
                System.out.println("Oops!");
            }
            if (myQueue.size() != test.size()) {
                System.out.println("Oops!");
            }
            double decide = Math.random();
            if (decide < 0.33) {
                int num = (int) (Math.random() * maxValue);
                myQueue.offer(num);
                test.offer(num);
            } else if (decide < 0.66) {
                if (!myQueue.isEmpty()) {
                    int num1 = myQueue.poll();
                    int num2 = test.poll();
                    if (num1 != num2) {
                        System.out.println("Oops!");
                    }
                }
            } else {
                if (!myQueue.isEmpty()) {
                    int num1 = myQueue.peek();
                    int num2 = test.peek();
                    if (num1 != num2) {
                        System.out.println("Oops!");
                    }
                }
            }
        }
        if (myQueue.size() != test.size()) {
            System.out.println("Oops!");
        }
        while (!myQueue.isEmpty()) {
            int num1 = myQueue.poll();
            int num2 = test.poll();
            if (num1 != num2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束！");
    }


    public static void main(String[] args) {
        testQueue();
    }



}
