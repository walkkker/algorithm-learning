package binaryTree;

public class PaperFolding {

    public static void printAllFolds(int N) {
        process(1, N, true);
    }


    // 当前你来了一个节点，脑海中想象的！
    // 这个节点在第i层，一共有N层，N固定不变的
    // 这个节点如果是凹的话，down = T
    // 这个节点如果是凸的话，down = F
    // 函数的功能：中序打印以你想象的节点为头的整棵树！


    // i -> 当前在第几层, i 从 1 开始
    // N -> 总共要求几层
    // down: true “凹”，  false "凸"
    public static void process(int i, int N, boolean down) {
        // base case
        if (i > N) {
            return;
        }
        //中序遍历
        // 左孩子 为 凹， 右孩子 为 凸
        process(i + 1, N, true);
        System.out.print(down ? "凹 " : "凸 ");
        process(i + 1, N, false);
    }

    public static void main(String[] args) {
        int N = 3;
        printAllFolds(N);
    }

}
