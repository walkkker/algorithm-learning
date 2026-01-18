package unionFindSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/number-of-islands/
 *
 * 实现： 深度优先遍历递归 + 并查集数组实现形式
 */

public class NumOfIslands {

    // 深度优先搜索
    public int numIslands1(char[][] grid) {
        if (grid == null) {
            return 0;
        }
        int ans = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    ans++;
                    infects(grid, i, j);
                }
            }
        }
        return ans;
    }

    // 从(i,j)这个位置出发，把所有连成一片的'1'字符，变成0
    public void infects(char[][] grid, int i, int j) {
        if (i < 0 || i == grid.length || j < 0 || j == grid[0].length) {
            return;
        }
        if (grid[i][j] != '1') {
            return;
        }
        grid[i][j] = 0;
        infects(grid, i - 1, j);
        infects(grid, i + 1, j);
        infects(grid, i, j - 1);
        infects(grid, i, j + 1);
    }


    // 并查集解法 1 - HashMap实现； 但是常数时间很大
    public int numIslands2(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        Dot[][] dots = new Dot[row][col];
        List<Dot> dotList = new ArrayList<>();
        // 使用 dots里面的内存地址 来区分 board里面的 1
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] == '1') {
                    dots[i][j] = new Dot();
                    dotList.add(dots[i][j]);
                }
            }
        }
        UnionFind1<Dot> uf = new UnionFind1<>(dotList);
        for (int j = 1; j < col; j++) {
            // (0,j)  (0,0)跳过了  (0,1) (0,2) (0,3)
            if (board[0][j - 1] == '1' && board[0][j] == '1') {
                uf.union(dots[0][j - 1], dots[0][j]);
            }
        }
        for (int i = 1; i < row; i++) {
            if (board[i - 1][0] == '1' && board[i][0] == '1') {
                uf.union(dots[i - 1][0], dots[i][0]);
            }
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (board[i][j] == '1') {
                    if (board[i][j - 1] == '1') {
                        uf.union(dots[i][j - 1], dots[i][j]);
                    }
                    if (board[i - 1][j] == '1') {
                        uf.union(dots[i - 1][j], dots[i][j]);
                    }
                }
            }
        }
        return uf.sets();
    }


    public class Dot {

    }


    public class UnionFind1<V> {
        public HashMap<V, V> parents;
        public HashMap<V, Integer> sizeMap;

        public UnionFind1(List<V> values) {
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V cur : values) {
                parents.put(cur, cur);
                sizeMap.put(cur, 1);
            }
        }

        public V findFather(V cur) {
            Stack<V> path = new Stack<>();
            while (cur != parents.get(cur)) {
                path.push(cur);
                cur = parents.get(cur);
            }
            while (!path.isEmpty()) {
                parents.put(path.pop(), cur);
            }
            return cur;
        }

        public void union(V a, V b) {
            V aHead = findFather(a);
            V bHead = findFather(b);
            if (aHead != bHead) {
                int aSetSize = sizeMap.get(aHead);
                int bSetSize = sizeMap.get(bHead);
                V big = aSetSize >= bSetSize ? aHead : bHead;
                V small = big == aHead ? bHead : aHead;
                parents.put(small, big);
                sizeMap.put(big, aSetSize + bSetSize);
                sizeMap.remove(small);
            }
        }

        public int sets() {
            return sizeMap.size();
        }

    }


    // 并查集解法2 - 数组实现形式
    public int numIslands(char[][] grid) {
        // 每个位置都代表 一个 集合元素，所以创建 row * col长度的 parent,size数组
        if (grid == null || grid.length == 0) {
            return 0;
        }
        UnionFind2 uf = new UnionFind2(grid);
        int row = grid.length;
        int col = grid[0].length;
        // 每个节点 与 左上部分进行检验，两者都为一则进行集合合并
        // 第一行，无上
        for (int j = 1; j < col; j++) {
            if (grid[0][j - 1] == '1' && grid[0][j] == '1') {
                uf.union(0 , j - 1, 0, j);
            }
        }

        for (int i = 1; i < row; i++) {
            if (grid[i - 1][0] == '1' && grid[i][0] == '1') {
                uf.union(i - 1, 0, i, 0);
            }
        }

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (grid[i][j - 1] == '1' && grid[i][j] == '1') {
                    uf.union(i, j-1, i , j);
                }
                if (grid[i - 1][j] == '1' && grid[i][j] == '1') {
                    uf.union(i - 1, j, i , j);
                }
            }
        }
        return uf.sets();
    }

    public class UnionFind2 {
        int[] parent;
        int[] size;
        int[] help;
        int sets;
        int col;

        public UnionFind2(char[][] grid) {
            int row = grid.length;
            col = grid[0].length;
            int length = row * col;
            parent = new int[length];
            size = new int[length];
            help = new int[length];
            sets = 0;
            // 创建完 parent, size后 要在构造器中进行初始化
            for (int r = 0; r < row; r++) {
                for (int c = 0; c < col; c++) {
                    if (grid[r][c] == '1') {
                        int index = index(r, c);
                        parent[index] = index;
                        size[index] = 1;
                        sets++;
                    }
                }
            }
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
            int i1 = index(r1, c1);
            int i2 = index(r2, c2);
            int aHead = find(i1);
            int bHead = find(i2);
            if (aHead != bHead) {
                int aSize = size[aHead];
                int bSize = size[bHead];
                int big = aSize >= bSize ? aHead : bHead;
                int small = big == aHead ? bHead : aHead;
                parent[small] = big;
                size[big] += size[small];
                sets--;
            }
        }

        public int sets() {
            return sets;
        }
    }
}
