package unionFindSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

// https://leetcode-cn.com/problems/number-of-islands/
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



}
