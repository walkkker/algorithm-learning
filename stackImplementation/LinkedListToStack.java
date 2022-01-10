package stackImplementation;

import java.util.Stack;

public class LinkedListToStack {

    public static class Node<T> {
        T val;
        Node next;

        Node(T v) {
            val = v;
            next= null;
        }
    }


    // 自己实现栈 只需要head头节点即可（因为是头插法 -> 后进先出），一定要记住 push pop时，调整size
    // !!! 并且， push 时 要区分size==0/size!=0两种情况， ==0时不使用头插法，直接赋值，与QUEUE实现一样
    // 其实， size==0 也可以转换为 head==null
    public static class MyStack<T>{
        Node<T> head;
        int size;

        public MyStack() {
            head = null;
            size = 0;
        }

        // 实现 isEmpty(), size(), push(), pop(), peek()
        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }

        public void push(T val) {

            Node<T> cur = new Node(val);

            if (head == null) { // 如果head==null, head = null
                head = cur;
            } else { //否则的话 头插法
                cur.next = head;
                head = cur;
            }
            size++;
        }

        public T pop() {
            if (size == 0) {
                return null;
            }
            T ans = head.val;
            head = head.next;
            size--;
            return ans;
        }

        public T peek() {
            if (size == 0) {
                return null;
            }
            return head.val;
        }
    }


    public static void testStack() {
        MyStack<Integer> myStack = new MyStack<>();
        Stack<Integer> test = new Stack<>();
        int testTime = 5000000;
        int maxValue = 200000000;
        System.out.println("测试开始！");
        for (int i = 0; i < testTime; i++) {
            if (myStack.isEmpty() != test.isEmpty()) {
                System.out.println("Oops!");
            }
            if (myStack.size() != test.size()) {
                System.out.println("Oops!");
            }
            double decide = Math.random();
            if (decide < 0.33) {
                int num = (int) (Math.random() * maxValue);
                myStack.push(num);
                test.push(num);
            } else if (decide < 0.66) {
                if (!myStack.isEmpty()) {
                    int num1 = myStack.pop();
                    int num2 = test.pop();
                    if (num1 != num2) {
                        System.out.println("Oops!");
                    }
                }
            } else {
                if (!myStack.isEmpty()) {
                    int num1 = myStack.peek();
                    int num2 = test.peek();
                    if (num1 != num2) {
                        System.out.println("Oops!");
                    }
                }
            }
        }
        if (myStack.size() != test.size()) {
            System.out.println("Oops!");
        }
        while (!myStack.isEmpty()) {
            int num1 = myStack.pop();
            int num2 = test.pop();
            if (num1 != num2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束！");
    }

    public static void main(String[] args) {
        testStack();
    }
}
