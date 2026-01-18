package extraPractice.binaryTree;

import java.util.HashMap;

// https://leetcode-cn.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
public class PreOrderInOrderConstructBT {

    // 建立反向索引表，用于快速查找inorder中节点数值的下标
    HashMap<Integer, Integer> indexMap = new HashMap<>();
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        for (int i = 0; i < inorder.length; i++) {
            indexMap.put(inorder[i], i);
        }

        return process(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    // 递归函数接收 preorder 和 inorder 的数组和对应数的区间，构建好对应数组范围的树，并返回头节点
    // 范围全部使用双闭区间
    public TreeNode process(int[] pre, int L1, int R1, int[] in, int L2, int R2) {
        // L1 > L2 表示该区间，无节点，所以为空树，返回空节点
        if (L1 > R1) {
            return null;
        }
        int find = indexMap.get(pre[L1]);
        TreeNode root = new TreeNode(pre[L1]);
        // 距离不要算错了，画区间来搞清楚
        int leftSize = find - L2;   // [L2, head)
        int rightSize = R2 - find;  // (head, R2]

        // 全部使用双闭区间 []
        root.left = process(pre, L1 + 1, L1 + leftSize, in, L2, find - 1);
        root.right = process(pre, L1 + leftSize + 1, R1, in, find + 1, R2);

        return root;
    }
}
