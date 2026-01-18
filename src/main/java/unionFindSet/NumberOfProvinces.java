package unionFindSet;

// 测试链接：https://leetcode.com/problems/friend-circles/
public class NumberOfProvinces {

    // 首先确定有多少个 对象 要作为 节点
    // 该题中 总共有 N 个节点; 关系数组 表明了 N个对象之间的关系，所以为 (N - 1) * (N - 1)的矩阵，
    // 该题使用【数组】 实现 并查集
    public class UnionFind {
        int[] parent;
        int[] size;
        int[] help; // 用于 【路径压缩】 的 模拟栈过程
        int sets; // 并查集中 集合的 个数； 因为sizeMap不使用HashMap，所以无法清除不为代表节点的size，从而不能直接返回sizeMap.size()
        // size数组 中的个数是一定的，所以单独使用 sets 来进行集合数的记录

        // 生成 【N 个 对象】， 初始化时 对应 【N 个集合】
        public UnionFind(int N) {
            parent = new int[N];    // 每个【节点】对应一个【父节点】，所以长度为 N
            size = new int[N];  // 当节点作为代表节点时，对应的集合的【SIZE】，非代表节点对应的位置本题中无意义。
            help = new int[N];  // 使用数组 在 路径压缩过程中，做【模拟栈】。 数组速度比栈快
            sets = N;   // sets 记录的就是 当前并查集 中，含有多少个集合。 因为初始时，N个集合，每次执行 union操作，sets--
            // 注意，【此时还没有初始化完成】，创建了 parent数组和size数组之后，要对其映射数组内容进行初始化
            // 初始化的过程中，每个对象的父亲是自己（parent表）；每个节点都是代表节点，对应的size==1；
            for (int i = 0; i < N; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        public int find(int i) {
            int helpi = 0;
            while (i != parent[i]) {
                help[helpi++] = i;
                i = parent[i];
            }
            // 路径压缩
            for (helpi--; helpi >= 0; helpi--) {
                parent[help[helpi]] = i;
            }
            return i;
        }

        public void union(int a, int b) {
            int aHead = find(a);
            int bHead = find(b);
            if (aHead != bHead) {
                // 1. 求出 Bigger size的 代表节点
                int aSize = size[aHead];
                int bSize = size[bHead];
                // 重定向
                int big = aSize >= bSize ? aHead : bHead;
                int small = big == aHead ? bHead : aHead;
                // 小挂大
                parent[small] = big;
                size[big] += size[small];
                // 不要忘记，因为合并了集合，所以集合数量 -1；
                sets--;
            }
        }

        public int sets() {
            return sets;
        }
    }

    public int findCircleNum(int[][] isConnected) {
        int N = isConnected.length;
        UnionFind unionFind = new UnionFind(N);
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (isConnected[i][j] == 1) {
                    unionFind.union(i ,j);
                }
            }
        }
        return unionFind.sets();
    }
}
