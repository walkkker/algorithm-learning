package classicRecursiveMethod;

/**
 * process1 process2 说明的问题是 base case只要设置的合理，然后逻辑处理正确（【1】不递归地处理情况【2】return返回），
 * 那么 base case 并没有唯一的答案，有时多种base case都可以得到准确答案。
 */
public class Hanoi {

    // N 表示几层汉诺塔；  process的含义是，将传入的参数N层汉诺塔，移到 "other" 柱子上去。
    public static void process1(int N, String from, String to, String other) {
        if (N == 0) {
            return;
        }
        process1(N - 1, from, other, to);
        System.out.println("从" + from + "到" + to);
        process1(N - 1, other, to, from);
    }

    public static void process2(int N, String from, String to, String other) {
        if (N == 1) {
            System.out.println("从" + from + "到" + to);
            return;
        }
        process2(N - 1, from, other, to);
        System.out.println("从" + from + "到" + to);
        process2(N - 1, other, to, from);
    }

    public static void main(String[] args) {
        process1(3, "左", "右", "中");
        System.out.println();
        System.out.println();
        process2(3, "左", "右", "中");
    }
}
