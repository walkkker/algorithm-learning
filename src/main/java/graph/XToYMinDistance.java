package graph;


import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

// 用邻接矩阵的方式又实现一遍Dijkstra
// 题目描述如下
//
// 一天Peiger捧着一本世界地图在看，突然他拿起笔
// 将他最爱的那些城市标记出来，并且随机的将这些城市中的某些用线段两两连接起来。
// Peiger量出了每条线段的长度，现在Peiger想知道在这些线段组成的图中任意两个城市之间的最短距离是多少。
//
// 输入
// 输入包含多组测试数据。
// 每组输入第一行为两个正整数n（n<=10）和m（m<=n*(n-1)/2），n表示城市个数，m表示线段个数。
// 接下来m行，每行输入三个整数a，b和l，表示a市与b市之间存在一条线段，线段长度为l。（a与b不同）
// 每组最后一行输入两个整数x和y，表示问题：x市与y市之间的最短距离是多少。（x与y不同）
// 城市标号为1~n，l<=20。
//
// 输出
// 对于每组输入，输出x市与y市之间的最短距离，如果x市与y市之间非连通，则输出“No path”。
//
// 样例输入
// 4 4
// 1 2 4
// 1 3 1
// 1 4 1
// 2 3 1
// 2 4
//
// 样例输出
// 3

// 假设有两条路
// [1,3,7]，这条路是从1到3，距离是7
// [1,3,4]，这条路是从1到3，距离是4
// 那么应该忽略[1,3,7]，因为[1,3,4]比它好


public class XToYMinDistance {

    // n 是城市数量, 城市标号为1~n， m是 边的数量，roads a，b和l， x起始点， y是终点

    // 方法1：暴力递归：得到所有x->y的可能性的pathSum,返回最短的pathSum
    public static int minDistance1(int n, int m, int[][] roads, int x, int y) {
        // 转邻接矩阵
        // 因为邻接矩阵为 n*n，其中n为直接映射的 节点范围。 图中节点范围为1-n, 所以数组应该设置成如下形式int[0-n][0-n]
        int[][] matrix = new int[n + 1][n + 1];

        // 这里要注意，因为给定的 数组中 只记载了 连接线段的信息，所以我们要将邻接矩阵matrix初始化为 Integer.MAX_VALUE,表示无边
        // 从而，没有被 roads 涉及到的边，就自动变成了 系统最大值，表示无边
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                matrix[i][j] = Integer.MAX_VALUE;
            }
        }
        // 根据 roads 对 matrix 赋值
        for (int[] road : roads) {
            int from = road[0];
            int to = road[1];
            int weight = road[2];
            matrix[from][to] = Math.min(matrix[from][to], weight);
            matrix[to][from] = Math.min(matrix[to][from], weight);
        }
        // 为了防止 x->y 的过程中，y又考虑回到X的路径，所以设置 visited数组，将X置为true表示y->x无边，从而不考虑。不然的话会x-y死循环。
        // visited 表示哪些节点 已经包含，从而后续节点不去计算 下一个节点为这些已包含节点的 数值
        boolean[] visited = new boolean[n + 1];
        // 初始化完毕
        return process(x, y, matrix, visited);
    }

    // 递归函数： 返回 从 cur -> end 的最短路径和
    public static int process(int cur, int end, int[][] matrix, boolean[] visited) {
        if (cur == end) {
            return 0;
        }
        if (visited[cur]) {
            return Integer.MAX_VALUE;
        }
        visited[cur] = true;
        int pathSum = Integer.MAX_VALUE;
        for (int j = 0; j < matrix.length; j++) {
            // 首先确保 下一条边 存在
            if (matrix[cur][j] != Integer.MAX_VALUE) {
                int tmp = process(j, end, matrix, visited);
                if (tmp != Integer.MAX_VALUE) {
                    pathSum = Math.min(pathSum, tmp + matrix[cur][j]);
                }
            }
        }
        visited[cur] = false;
        return pathSum;
    }


    // 解法2： dijkstra    邻接矩阵解法  加强堆写法
    public static int minDistance2(int n, int m, int[][] roads, int x, int y) {
        // 转成邻接矩阵
        // 生成之后，先设所有位置值为 无边，即系统最大值
        int[][] matrix = new int[n + 1][n + 1];
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                matrix[i][j] = Integer.MAX_VALUE;
            }
        }
        for (int[] road : roads) {
            int from = road[0];
            int to = road[1];
            int weight = road[2];
            matrix[from][to] = Math.min(matrix[from][to], weight);
            matrix[to][from] = Math.min(matrix[to][from], weight);
        }
        HashMap<Integer, Record> mapRecord = new HashMap<>();
        MyHeap heap = new MyHeap(n);
        // 起始的时候，到达每个顶点的初始值是 系统最大值
        addOrUpdateRecord(x, 0, mapRecord);
        heap.addOrUpdateOrIgnore(mapRecord.get(x));


        while (!heap.isEmpty()) {
            Record rec = heap.poll();
            int node = rec.toNode;
            int distance = rec.distance;
            if (node == y) {
                return distance;
            }
            // 直接更新到 mapRecord里面
            for (int j = 1; j < n + 1; j++) {
                // 一定要注意，要使用if语句，只有 当前选取节点的相邻边有效时，换句话说必须相邻边存在的时候（非系统最大值，表示无边），才可以做record的更新
                if (matrix[node][j] != Integer.MAX_VALUE) {
                    addOrUpdateRecord(j, distance + matrix[node][j], mapRecord);
                    heap.addOrUpdateOrIgnore(mapRecord.get(j));
                }
            }
        }
        return Integer.MAX_VALUE;
    }


    public static void addOrUpdateRecord(int node, int distance, HashMap<Integer, Record> mapRecord) {
        if (!mapRecord.containsKey(node)) { //add
            mapRecord.put(node, new Record(node, distance));
        } else { // update
            mapRecord.get(node).distance = Math.min(mapRecord.get(node).distance, distance);
        }
    }


    public static class Record {
        int toNode;
        int distance;

        public Record(int to, int d) {
            toNode = to;
            distance = d;
        }
    }

    public static class MyHeap {
        Record[] heap;
        HashMap<Record, Integer> indexMap;
        int heapSize;

        public MyHeap(int size) {
            heap = new Record[size];
            indexMap = new HashMap<>();
            heapSize = 0;
        }

        public boolean isEmpty() {
            return heapSize == 0;
        }

        public boolean isEntered(Record rec) {
            return indexMap.containsKey(rec);
        }

        public boolean inHeap(Record rec) {
            return isEntered(rec) && indexMap.get(rec) != -1;
        }

        public void heapInsert(int index) {
            while (heap[index].distance < heap[(index - 1) / 2].distance) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        public void heapify(int index) {
            int left = index * 2 + 1;
            while (left < heapSize) {
                // 千万不要忘了加 【.distance】
                int smallest = left + 1 < heapSize ? (heap[left + 1].distance < heap[left].distance ? left + 1 : left) : left;
                smallest = heap[index].distance < heap[smallest].distance ? index : smallest;
                if (smallest == index) {
                    return;
                }
                swap(index, smallest);
                index = smallest;
                left = index * 2 + 1;
            }
        }

        public void addOrUpdateOrIgnore(Record rec) {
            if (!isEntered(rec)) {
                offer(rec);
            }
            if (inHeap(rec)) {
                resign(rec);
            }
        }

        public void offer(Record rec) {
            heap[heapSize++] = rec;
            // !!! 插入元素后，不要忘记更新 indexMap
            indexMap.put(rec, heapSize - 1);
            heapInsert(heapSize - 1);
        }

        public Record poll() {
            Record ans = heap[0];
            // 对于要删除的元素，不要忘记更新indexMap 为 -1
            swap(0, --heapSize); // todo: --heapSize才正式意味着 ans节点被删除，接着才可以更新indexMap为-1
            heapify(0);
            // todo 【错误点】 注意，是这个时候才 置为-1
            indexMap.put(ans, -1);
            return ans;
        }

        public void resign(Record rec) {
            int index = indexMap.get(rec);
            heapInsert(index);
            heapify(index);
        }


        public void swap(int i, int j) {
            Record tmp = heap[i];
            heap[i] = heap[j];
            heap[j] = tmp;
            indexMap.put(heap[i], i);
            indexMap.put(heap[j], j);
        }
    }


    // 解法3： 笔试方法………………………… 使用系统提供小根堆 + boolean数组
    // boolean 数组用作 确定哪些节点已经被选择; 用 HashSet也可以，但是哈希表常数时间很大
    public static int minDistance3(int n, int m, int[][] roads, int x, int y) {
        // 第一步，先转 邻接矩阵
        int[][] matrix = new int[n + 1][n + 1];
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                matrix[i][j] = Integer.MAX_VALUE;
            }
        }
        for (int[] road : roads) {
            int from = road[0];
            int to = road[1];
            int weight = road[2];
            matrix[from][to] = Math.min(matrix[from][to], weight);
            matrix[to][from] = Math.min(matrix[to][from], weight);
        }

        // 设置小根堆，比较器 + boolean数组
        boolean[] visited = new boolean[n + 1];
        PriorityQueue<Record> heap = new PriorityQueue<>(new MyComparator());
        heap.add(new Record(x, 0));
        while (!heap.isEmpty()) {
            Record rec = heap.poll();
            int node = rec.toNode;
            int distance = rec.distance;
            if (node == y) {
                return distance;
            }
            if (visited[node]) {
                continue;
            }
            visited[node] = true;
            for (int j = 1; j < n + 1; j++) {
                // todo 必须边是存在的，才能考虑更新
                if (matrix[node][j] != Integer.MAX_VALUE) {
                    heap.add(new Record(j, matrix[node][j] + distance));
                }
            }
        }
        return Integer.MAX_VALUE;
    }

    public static class MyComparator implements Comparator<Record> {
        public int compare(Record o1, Record o2) {
            return o1.distance - o2.distance;
        }
    }


    // 为了测试
    // 城市1~n
    // 随机生成m条道路
    // 每一条路的距离，在1~v之间
    public static int[][] randomRoads(int n, int m, int v) {
        int[][] roads = new int[m][3];
        for (int i = 0; i < m; i++) {
            int from = (int) (Math.random() * n) + 1;
            int to = (int) (Math.random() * n) + 1;
            int distance = (int) (Math.random() * v) + 1;
            roads[i] = new int[]{from, to, distance};
        }
        return roads;
    }

    // 为了测试
    public static void main(String[] args) {
        // 城市数量n，下标从1开始，不从0开始
        int n = 4;
        // 边的数量m，m的值不能大于n * (n-1) / 2
        int m = 4;
        // 所的路有m条
        // [a,b,c]表示a和b之间有路，距离为3，根据题意，本题中的边都是无向边
        // 假设有两条路
        // [1,3,7]，这条路是从1到3，距离是7
        // [1,3,4]，这条路是从1到3，距离是4
        // 那么应该忽略[1,3,7]，因为[1,3,4]比它好
        int[][] roads = new int[m][3];
        roads[0] = new int[]{1, 2, 4};
        roads[1] = new int[]{1, 3, 1};
        roads[2] = new int[]{1, 4, 1};
        roads[3] = new int[]{2, 3, 1};
        // 求从x到y的最短距离是多少，x和y应该在[1,n]之间
        int x = 2;
        int y = 4;

        // 暴力方法的解
        System.out.println(minDistance1(n, m, roads, x, y));

        // Dijkstra的解
        System.out.println(minDistance2(n, m, roads, x, y));

        // 解法3
        System.out.println(minDistance3(n, m, roads, x, y));

        // 下面开始随机验证
        int cityMaxSize = 12;
        int pathMax = 30;
        int testTimes = 20000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            n = (int) (Math.random() * cityMaxSize) + 1;
            m = (int) (Math.random() * n * (n - 1) / 2) + 1;
            roads = randomRoads(n, m, pathMax);
            x = (int) (Math.random() * n) + 1;
            y = (int) (Math.random() * n) + 1;
            int ans1 = minDistance1(n, m, roads, x, y);
            int ans2 = minDistance2(n, m, roads, x, y);
            int ans3 = minDistance3(n, m, roads, x, y);
            if (ans1 != ans2 || ans1 != ans3 || ans2 != ans3) {
                System.out.println("出错了！");
            }
        }
        System.out.println("测试结束");
    }



    public static int minDistance(int n, int m, int[][] roads, int x, int y) {
        // 第一步生成邻接矩阵
        int[][] map = new int[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                map[i][j] = Integer.MAX_VALUE;
            }
        }
        for (int[] road : roads) {
            map[road[0]][road[1]] = Math.min(map[road[0]][road[1]], road[2]);
            map[road[1]][road[0]] = Math.min(map[road[1]][road[0]], road[2]);
        }
        boolean[] visited = new boolean[n + 1];
        return process(x, y, n, map, visited);
    }

    // 当前来到的城市是cur，最终目的地是aim，一共有1~n这些城市
    // 所有城市之间的距离都在map里
    // 之前已经走过了哪些城市都记录在了visited里面，请不要重复经过
    // 返回从cur到aim所有可能的路里，最小距离是多少
    public static int process(int cur, int aim, int n, int[][] map, boolean[] visited) {
        if (visited[cur]) {
            return Integer.MAX_VALUE;
        }
        if (cur == aim) {
            return 0;
        }
        visited[cur] = true;
        int ans = Integer.MAX_VALUE;
        for (int next = 1; next <= n; next++) {
            if (next != cur && map[cur][next] != Integer.MAX_VALUE) {
                int rest = process(next, aim, n, map, visited);
                if (rest != Integer.MAX_VALUE) {
                    ans = Math.min(ans, map[cur][next] + rest);
                }
            }
        }
        visited[cur] = false;
        return ans;
    }


}


