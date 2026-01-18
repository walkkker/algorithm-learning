package queueImplementation;

import java.util.*;


public class TwoStackImplementQueue {
    public static class CQueue {

        Stack<Integer> pushStack;
        Stack<Integer> popStack;

        public CQueue() {
            pushStack = new Stack<>();
            popStack = new Stack<>();
        }

        public void appendTail(int value) {
            pushStack.push(value);
            pushToPop();
        }

        public int deleteHead() {
            if (pushStack.isEmpty() && popStack.isEmpty()) {
                return -1;
            }
            // 保证 popStack 可以弹出数字
            pushToPop();
            return popStack.pop();
        }

        // 这个函数是关键
        public void pushToPop() {
            if (popStack.isEmpty()) {
                while (!pushStack.isEmpty()) {
                    popStack.push(pushStack.pop());
                }
            }
        }
    }
}
