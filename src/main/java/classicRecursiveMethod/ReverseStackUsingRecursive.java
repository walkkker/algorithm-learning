package classicRecursiveMethod;

import java.util.Stack;

/**
 * 总体逻辑其实简单，拆分成两个函数，但是要递归才能实现：
 * （1）返回栈底元素，剩余部分保留原位置在栈中
 * （2）依次提取栈底元素，然后从最后的递归依次开始入栈，从而实现逆序
 */
public class ReverseStackUsingRecursive {

    // 该递归函数的功能是 参数为传入一个栈，将栈底元素移除并返回。
    // 问题分解为： 栈顶元素弹出，递归调用剩余部分（栈底元素移除并返回），栈顶元素重新压入
    public static int f(Stack<Integer> stack) {
        int cur = stack.pop();
        if (stack.isEmpty()) {
            return cur;
        }
        int last = f(stack);
        stack.push(cur);
        return last;
    }


    // 主的调用函数： 逆序栈内元素
    // 分解子问题：    【1】从最底端将元素移除并返回，记录在变量a里面
    //              【2】递归剩余部分（实现剩余部分的逆序）
    //              【3】将a变量存的元素，压入堆顶
    // Base case: 因为返回值是void，所以base case情况下，不需要返回值。所以可以直接将 stack.isEmpty()作为base case.
    public static void g(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return;
        }
        int bottom = f(stack);
        g(stack);
        stack.push(bottom);
    }


    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        // bottom -> top: 1,2,3,4,5

        g(stack);
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }


    }
}
