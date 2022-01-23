package extraPractice.maze;

import practice.leetcode.Test;

import java.util.Comparator;
import java.util.PriorityQueue;


/**
 * 除了错误点
 * 1） 还有一个很重要的点，就是使用 boolean数组，标记 哪些位置 不能再次被访问，防止重复死循环。
 * 2） 根据DFS，一条路径上已经访问的点不能被访问，但是 返回的点可以再次被其他路径访问。 所以 递归返回时，要 【恢复现场】
 * 总结： 使用boolean数组      ==》 实现  【标记防止同一路径重复访问】 + 返回时，【恢复现场操作】，允许其他路径再次访问
 */
public class MinOnes {

    // 目前的最优解，使用 Dijkstra算法
    public static int minDistance(int[][] roads) {

        int row = roads.length;
        int col = roads[0].length;

        // computed[i] = true，表示从源出发点到i这个城市，已经计算出最短距离了
        // computed[i] = false，表示从源出发点到i这个城市，还没有计算出最短距离
        boolean[][] computed = new boolean[row][col];
        // 距离小根堆
        PriorityQueue<Node> heap = new PriorityQueue<>(new MyComparator());

        // 【错误点！！！】下面这句不对啊，与普通dijkstra算法实现的差异点
        //heap.add(new Node(0, 0, 0));
        heap.add(new Node(0, 0, roads[0][0]));
        while (!heap.isEmpty()) {
            Node cur = heap.poll();
            int r = cur.row;
            int c = cur.col;
            int dis = cur.distance;
            if (computed[r][c]) {
                continue;
            }
            if (r == row - 1 && c == col - 1) {
                return dis;
            }

            computed[r][c] = true;
            // 开始 计算相邻边+cur.distance 的值 对应每个节点，放入 heap中
            // 注意 放进去的对象 一定是要有效的，所以使用if语句 进行判断

            if (isValid(r - 1, c, roads, computed)) {
                heap.add(new Node(r - 1, c, dis + roads[r - 1][c]));
            }
            if (isValid(r + 1, c, roads, computed)) {
                heap.add(new Node(r + 1, c, dis + roads[r + 1][c]));
            }
            if (isValid(r , c - 1, roads, computed)) {
                heap.add(new Node(r, c - 1, dis + roads[r][c - 1]));
            }
            if (isValid(r, c + 1, roads, computed)) {
                heap.add(new Node(r, c + 1, dis + roads[r][c + 1]));
            }
        }
        return Integer.MAX_VALUE;
    }

    public static boolean isValid(int r, int c, int[][] roads, boolean[][] visited) {
        int rows = roads.length;
        int cols = roads[0].length;
        if (r < 0 || r >= rows) {
            return false;
        }
        if (c < 0 || c >= cols) {
            return false;
        }
        if (visited[r][c]) {
            return false;
        }
        return true;
    }

    public static class Node {
        int row;
        int col;
        int distance;

        public Node(int row, int col, int distance) {
            this.row = row;
            this.col = col;
            this.distance = distance;
        }
    }

    public static class MyComparator implements Comparator<Node> {
        public int compare(Node o1, Node o2) {
            return o1.distance - o2.distance;
        }
    }



    // 优化解法
    public static int minOnes2(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        boolean[][] visited = new boolean[row][col];
        return process2(0, 0, matrix, visited);
    }

    // DFS + 回溯
    // 从 i,j 出发，返回到达右下角的最少的1的个数
    public static int process2(int i, int j, int[][] matrix, boolean[][] visited) {
        int row = matrix.length;
        int col = matrix[0].length;
        if (i < 0 || i >= row || j < 0 || j >= col) {
            return Integer.MAX_VALUE;
        }

        if (i == row - 1 && j == col - 1) {
            return matrix[i][j];
        }

        if (visited[i][j]) {
            return Integer.MAX_VALUE;
        }

//        不能放在这里
//        if (visited[i - 1][j] && visited[i + 1][j] && visited[i][j - 1] && visited[i][j + 1]) {
//            return Integer.MAX_VALUE;
//        }

        visited[i][j] = true;
        int n = matrix[i][j];

        int p1 = process2(i - 1, j, matrix, visited);
        int p2 = process2(i + 1, j, matrix, visited);
        int p3 = process2(i, j - 1, matrix, visited);
        int p4 = process2(i, j + 1, matrix, visited);
        // Todo： 全部是 无效值 的情况，【转成圈把自己憋死】就是这句话，憋了几个小时
        // 错误点就是 问题理解不深入，考虑不完全。 不能只考虑起始点，要考虑每一个点可能遇到的可能性，从而写出 完善的逻辑。
        // 少讨论了一种可能性, 被四面包围的可能性
        if (p1 == p2 && p1 == p3 && p1 == p4 && p1 == Integer.MAX_VALUE) {
            n = Integer.MAX_VALUE;
        } else {
            n += Math.min(Math.min(p1, p2), Math.min(p3, p4));
        }
        visited[i][j] = false;
        return n;
    }

    // 错误版本 第一次编写的错误版本，同样的问题，没有考虑 四个方向全是 无效值的情况
    // 该递归函数的含义为： 从（0,0）位置 到达 i,j 位置，返回 实现路径中 包含最少1的个数
    // 这个方法可以做，但是不好说，也不好说通。 最好的方式就是使用DFS + 回溯 =》 动态规划
//    public static int process(int i, int j, int[][] matrix, boolean[][] visited) {
//        int row = matrix.length;
//        int col = matrix[0].length;
//        if (i < 0 || i >= row || j < 0 || j >= col) {
//            return -1;
//        }
//
//        if (visited[i][j]) {
//            return -1;
//        }
//
//        if (i == 0 && j == 0) {
//            return matrix[i][j];
//        }
//
//        int n = matrix[i][j];
//
//        visited[i][j] = true;
//
//        int next1 = process(i - 1, j, matrix, visited);
//        int p1 = next1 == -1 ? Integer.MAX_VALUE : next1;
//
//        int next2 = process(i + 1, j, matrix, visited);
//        int p2 = next2 == -1 ? Integer.MAX_VALUE : next2;
//
//        int next3 = process(i, j - 1, matrix, visited);
//        int p3 = next3 == -1 ? Integer.MAX_VALUE : next3;
//
//        int next4 = process(i, j + 1, matrix, visited);
//        int p4 = next4 == -1 ? Integer.MAX_VALUE : next4;
//        n += Math.min(Math.min(p1, p2), Math.min(p3, p4));
//
//        //visited[i][j] = false;
//        return n;
//    }

    // 稍微差一点的解法
    public static int minOnes1(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        boolean[][] visited = new boolean[row][col];
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        process(0, 0, matrix, visited, 0, heap);
        return heap.poll();
    }

    public static void process(int i, int j, int[][] matrix, boolean[][] visited, int pathSum, PriorityQueue<Integer> heap) {
        int row = matrix.length;
        int col = matrix[0].length;
        if (i < 0 || i >= row || j < 0 || j >= col) {
            return;
        }

        if (i == row - 1 && j == col - 1) {
            heap.add(pathSum + matrix[i][j]);
            return;
        }

        if (visited[i][j]) {
            return;
        }

        visited[i][j] = true;
        pathSum += matrix[i][j];

        process(i - 1, j, matrix, visited, pathSum, heap);
        process(i + 1, j, matrix, visited, pathSum, heap);
        process(i, j - 1, matrix, visited, pathSum, heap);
        process(i, j + 1, matrix, visited, pathSum, heap);
        visited[i][j] = false;
    }



    // 为了测试！！！
    public static int[][] randomRoads(int r, int c) {
        int[][] roads = new int[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                roads[i][j] = Math.random() > 0.5 ? 1 : 0;
            }
        }
        return roads;
    }

    // 为了测试
    public static void main(String[] args) {
        int[][] testArr = randomRoads(5,9);
        System.out.println(minDistance(testArr));
        System.out.println(MinOnes.minOnes1(testArr));
        System.out.println(MinOnes.minOnes2(testArr));
        System.out.println();
        printArr(testArr);
    }

    public static void printArr(int[][] arr) {
        int rows = arr.length;
        int cols = arr[0].length;
        for (int i = 0; i  < rows; i++) {
            for (int j  = 0; j < cols; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }


}
