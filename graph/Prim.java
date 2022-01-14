package graph;
import java.util.Set;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.HashSet;

/**
 * 包含两种解法：
 *  (1) 转换为本题中 Graph的表达方式后的 Prim算法
 *  (2) 前提为图是连通图，输入为【邻接矩阵】的PRIM算法，求【最小路径和】
 *
 *
 *  循环里面都是 【选最小边 + 对新解锁的边处理 + 对新解锁的点处理 + 新解锁点的相邻边加入检查集合】
 */
public class Prim {

    // 针对本package中的Graph定义的 Prim算法
    public static Set<Edge> primMST(Graph graph) {
        // 解锁的边进入小根堆
        PriorityQueue<Edge> heap = new PriorityQueue<>(new MyComparator());
        // 哪些点被解锁出来了
        HashSet<Node> selectedNodes = new HashSet<>();
        // 依次挑选的的边在result里
        Set<Edge> ansSet = new HashSet<>();


        for (Node node : graph.nodes.values()) {   // 随便挑了一个点
            if (!selectedNodes.contains(node)) {
                // 开始 点 边 点 依次解锁
                selectedNodes.add(node);           // 该点被选中
                for (Edge edge : node.edges) {     // 初始化步骤。由一个点，解锁所有相连的边
                    heap.add(edge);
                }
                // 开始循环
                while (!heap.isEmpty()) {  // 循环的内容就是，选一个点解锁边进小根堆，选一个点解锁边进小根堆
                    Edge curEdge = heap.poll();         // 弹出解锁的边中，最小的边
                    if (!selectedNodes.contains(curEdge.to)) {  // 不含有的时候，就是新的点
                        ansSet.add(curEdge);            // 新点未被解锁，则选中该边。此时，将边add进ansSet
                        selectedNodes.add(curEdge.to);  //  解锁了该边，则该边的toNode也被解锁
                        for (Edge edge : curEdge.to.edges) { // 将新点的相邻边也添加进小根堆，POST操作
                            heap.add(edge);
                        }
                    }
                }
            }
        }
        return ansSet;
    }


    public static class MyComparator implements Comparator<Edge> {
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }


    // 邻接矩阵解法
    // 前提是 图是连通图
    //  graph[i][j]表示点i到点j的距离，如果是系统最大值代表无路
    // 循环里面都是 【选最小边 + 边处理 + 点处理 + 新解锁点的相邻边加入检查集合】
    public static int Prim(int[][] graph) {
        // Prim
        int size = graph.length;  // 图中节点的个数
        int[] distances = new int[size]; // !记录 已经解锁点集中的点 到 未解锁节点 j（!visit[j]） 的 最短边； 局部最小 -> 全局最小
        boolean[] visit = new boolean[size]; // 记录已经解锁的节点
        // 选定0号节点为初始解锁节点
        visit[0] = true;
        for (int j = 0; j < size; j++) {
            distances[j] = graph[0][j];
        }
        int sum = 0; // 用以记录【最小路径和】

        // 0号节点已经选中，还差(size - 1)个节点。每轮选中一个最小边及其对应顶点，所以 size-1 轮
        for (int i = 1; i < size; i++) {
            // 首先从 当前 解锁的边 中 选取最小的且邻接顶点并未解锁的
            int minValue = Integer.MAX_VALUE;
            int minIndex = -1;
            for (int j = 0; j < size; j++) {
                if (!visit[j] && minValue > distances[j]) { //注意这里只用小于号
                    minValue = distances[j];
                    minIndex = j;
                }
            }
            // 退出上述循环后，我们就确定了 【1】选中最小边的长度+对应的未解锁顶点 OR 【2】minIndex==-1,表明所有节点都访问过，结束大循环
            if (minIndex == -1) {
                return sum;
            }
            // 【选中边处理】确定最小边，则对其进行处理（这里我们是计算 最小路径和， 所以加法即可）
            sum += minValue;
            // 【边解锁点，选中点处理】此时 minIndex 就成为了 新解锁的顶点
            visit[minIndex] = true;
            // 【点处理过后，解锁新的边集合】 minIndex 相邻的边，得以解锁，计算当前 边集合 中的最小值，然后下一个循环，重新开始找小边，确定小边对应节点
            // 解锁新的边集合时，注意，【1】连接已经解锁的点的边不需要更新（已经属于一个集合了，再添加会形成环）
            // 【2】因为已经解锁的 点 都为一个集合，所以只需要看 未解锁点的距离。 而distances数组中，我们只需要记录 已经解锁点中 到未解锁点的 最小距离即可。
            // 因为 已经解锁点集合中的任何一个点 到 目标顶点G，如果有一条边被选中， 只会有一个解锁点集中的点 到 G被选取。
            // 续：（1）选取后， 其余各点到G的边全部失效（因为与G已经变成一个集合） （2）如果存在这条边，那么这条边一定是 所有解锁边集中最短的 =》 只能是 解锁点集中的点 到 G 最短的。结合（1）（2）我们只需要distances存储 已解锁点集中，距离未解锁点G的最短距离即可，每次有新的点解锁时，查看是否更新distances
            // 局部最小边 =》 全局最小边
            // 这一步相当于 将新解锁点 的 相邻边 全部解锁。然后下一循环会重复选出 全局最小（ 边-》点 ）
            for (int j = 0; j < size; j++) {
                if (!visit[j] && graph[minIndex][j] < distances[j]) {
                    distances[j] = graph[minIndex][j];
                }
            }
        }
        return sum;
    }

}
