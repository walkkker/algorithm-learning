package stackImplementation;
import java.util.*;

public class GetMinStack {
    public static class MinStack {

        Stack<Integer> dataStack;
        Stack<Integer> minStack;
        public MinStack() {
            dataStack = new Stack<>();
            minStack = new Stack<>();
        }

        public void push(int val) {
            // dataStack 是一定要 push 的， 所以放在最后面
            // 先处理 minStack
            if (minStack.isEmpty()) {
                minStack.push(val);
            } else if (val <= minStack.peek()){
                minStack.push(val);
            }
            dataStack.push(val);
        }

        public void pop() {
            if (dataStack.isEmpty()) {
                throw new RuntimeException("The stack is Empty!");
            }
            int ret = dataStack.pop();
            if (ret == minStack.peek()) {
                minStack.pop();
            }
        }

        public int top() {
            return dataStack.peek();
        }

        public int getMin() {
            return minStack.peek();
        }
    }
}
