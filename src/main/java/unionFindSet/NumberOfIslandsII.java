package unionFindSet;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/number-of-islands-ii/
 */
public class NumberOfIslandsII {

    public static List<Integer> numIslands2(int m, int n, int[][] positions) {
        UnionFind uf = new UnionFind(m, n);
        List<Integer> ans = new ArrayList<>();
        for (int[] position : positions) {
            ans.add(uf.addLand(position[0], position[1]));
        }
        return ans;
    }


    public static class UnionFind {
        int[] parent;
        int[] size;
        int[] help;
        int sets;
        int col;
        int row;

        public UnionFind(int m, int n) {
            row = m;
            col = n;
            int length = m * n;
            parent = new int[length];
            size = new int[length];
            help = new int[length];
            sets = 0;
        }

        public int index(int r, int c) {
            return r * col + c;
        }

        public int find(int i) {
            int hi = 0;
            while (i != parent[i]) {
                help[hi++] = i;
                i = parent[i];
            }
            for (hi--; hi >= 0; hi--) {
                parent[help[hi]] = i;
            }
            return i;
        }

        public void union(int r1, int c1, int r2, int c2) {
            if (r1 < 0 || r1 == row || r2 < 0 || r2 == row
                    || c1 < 0 || c1 == col || c2 < 0 || c2 == col) {
                return;
            }
            int i1 = index(r1, c1);
            int i2 = index(r2, c2);
            // size[i]==0 表示该位置 没有被 置为1过
            if (size[i1] == 0 || size[i2] == 0) { // 两个位置都为0，或者其中一个位置为0，那么不合并，直接返回
                return;
            }
            // 既满足边界条件（不越界），又满足合并条件（两个位置都为1/两个位置都是有效元素位置），开始合并
            int aHead = find(i1);
            int bHead = find(i2);
            // 注意，求出aHead和bHead后， union时要判断 两个节点是否已经在一个集合中了。则直接返回，无需后续操作。
            if (aHead == bHead) {
                return;
            }
            // != 情况下，进行 代表节点的合并
            int aSize = size[aHead];
            int bSize = size[bHead];
            int big = aSize >= bSize ? aHead : bHead;
            int small = big == aHead ? bHead : aHead;
            parent[small] = big;
            size[big] += size[small];
            sets--;
        }

        public int addLand(int r, int c) {
            int i = index(r, c);
            if (size[i] != 0) {    //已经为1
                return sets;
            }
            // 此时将 该位置的1 添加进 相关的 数组信息中
            parent[i] = i;
            size[i] = 1;
            sets++;
            // 然后开始上下左右四个方向进行合并
            // 因为再union函数中已经加入了判断，所以上游不需要判断，可以直接传入参数
            union(r, c, r - 1, c);
            union(r, c, r + 1, c);
            union(r, c, r, c - 1);
            union(r, c, r, c + 1);
            return sets;
        }
    }

    // test 对数器
    public static List<Integer> numIslands21(int m, int n, int[][] positions) {
        UnionFind1 uf = new UnionFind1(m, n);
        List<Integer> ans = new ArrayList<>();
        for (int[] position : positions) {
            ans.add(uf.connect(position[0], position[1]));
        }
        return ans;
    }

    public static class UnionFind1 {
        private int[] parent;
        private int[] size;
        private int[] help;
        private final int row;
        private final int col;
        private int sets;

        public UnionFind1(int m, int n) {
            row = m;
            col = n;
            sets = 0;
            int len = row * col;
            parent = new int[len];
            size = new int[len];
            help = new int[len];
        }

        private int index(int r, int c) {
            return r * col + c;
        }

        private int find(int i) {
            int hi = 0;
            while (i != parent[i]) {
                help[hi++] = i;
                i = parent[i];
            }
            for (hi--; hi >= 0; hi--) {
                parent[help[hi]] = i;
            }
            return i;
        }

        private void union(int r1, int c1, int r2, int c2) {
            if (r1 < 0 || r1 == row || r2 < 0 || r2 == row || c1 < 0 || c1 == col || c2 < 0 || c2 == col) {
                return;
            }
            int i1 = index(r1, c1);
            int i2 = index(r2, c2);
            if (size[i1] == 0 || size[i2] == 0) {
                return;
            }
            int f1 = find(i1);
            int f2 = find(i2);
            if (f1 != f2) {
                if (size[f1] >= size[f2]) {
                    size[f1] += size[f2];
                    parent[f2] = f1;
                } else {
                    size[f2] += size[f1];
                    parent[f1] = f2;
                }
                sets--;
            }
        }

        public int connect(int r, int c) {
            int index = index(r, c);
            if (size[index] == 0) {
                parent[index] = index;
                size[index] = 1;
                sets++;
                union(r - 1, c, r, c);
                union(r + 1, c, r, c);
                union(r, c - 1, r, c);
                union(r, c + 1, r, c);
            }
            return sets;
        }
    }


    public static int[][] generateRandomArray(int maxM, int maxN) {
        int m = (int) (Math.random() * maxM) + 1;
        int n = (int) (Math.random() * maxN) + 1;
        int[][] ans = new int[m][n]; // 初始值就已经全部为0了
//        for (int i = 0; i < m; i++) {
//            for (int j = 0; j < n; j++) {
//                if (Math.random() < 0.4) {
//                    ans[i][j] = 1;
//                } else {
//                    ans[i][j] = 0;
//                }
//            }
//        }
        return ans;
    }

    public static void printArr(int[][] arr) {
        int row = arr.length;
        int col = arr[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int[][] generatePositions(int M, int N) {
        int length = (int) (Math.random() * 25);
        int[][] positions = new int[length][2];
        for (int i = 0; i < length; i++) {
            positions[i][0] = (int) (Math.random() * (M));
            positions[i][1] = (int) (Math.random() * (N));
        }
        return positions;
    }

    public static void print(List<Integer> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            System.out.print(list.get(i) + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int maxM = 10;
        int maxN = 15;
        int times = 10000;

        Outer:
        for (int i = 0; i < times; i++) {
            int[][] testArr = generateRandomArray(maxM, maxN);
            int m = testArr.length;
            int n = testArr[0].length;
            int[][] positions = generatePositions(m, n);
            List<Integer> ans1 = numIslands2(m, n, positions);
            List<Integer> ans2 = numIslands21(m, n, positions);
            if (ans1.size() != ans2.size()) {
                System.out.println("size Opps");
                break;
            } else {
                int size = ans1.size();
                for (int k = 0; k < size; k++) {
                    if (ans1.get(k) != ans2.get(k)) {
                        System.out.println(" value Opps");
                        System.out.println("Arr:");
                        printArr(testArr);
                        System.out.println("positions:");
                        printArr(positions);
                        print(ans1);
                        print(ans2);
                        break Outer;
                    }
                }
            }
        }
        System.out.println("Finish");
    }
}
