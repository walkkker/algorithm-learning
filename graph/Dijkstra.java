package graph;


import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.PriorityQueue;

/**方法1最优解：
 *      使用 加强堆 实现 Dijkestra算法
 *      时间复杂度 O(M * logN) 》 N为节点数量， M为边的总数量
 *
 * 方法2：
 *      仅使用 PriorityQueue实现，通过添加 visited HashSet 判断 节点是否已经被锁住（被访问过了）
 *      时间复杂度 为 O(M * log M) -》 N为节点数量， M为边的总数量
 */
public class Dijkstra {

    // 方法1
    // 返回一张哈希表 node，distance
    public static HashMap<Node, Integer> dijkstra(Node head, int size) {
        NodeHeap heap = new NodeHeap(size);
        HashMap<Node, NodeRecord> map = new HashMap<>();
        HashMap<Node, Integer> ans = new HashMap<>();
        map.put(head, new NodeRecord(head, 0));
        heap.offer(map.get(head));
        while (!heap.isEmpty()) {
            NodeRecord record = heap.poll();
            Node curNode = record.node;
            int distance = record.distance;
            ans.put(curNode, distance);
            for (Edge edge : curNode.edges) {
                Node toNode = edge.to;
                addOrUpdate(map, toNode, distance + edge.weight);
                heap.addOrUpdateOrIgnore(map.get(toNode));
            }
        }
        return ans;
    }

    public static void addOrUpdate(HashMap<Node, NodeRecord> map, Node node, int distance) {
        if (map.containsKey(node)) {
            // update
            NodeRecord record = map.get(node);
            record.distance = Math.min(record.distance, distance);
        } else {
            // add
            map.put(node, new NodeRecord(node, distance));
        }
    }


    public static class NodeRecord {
        Node node;
        int distance;

        public NodeRecord(Node n, int d) {
            node = n;
            distance = d;
        }
    }


    public static class NodeHeap {
        NodeRecord[] heap;
        HashMap<NodeRecord, Integer> indexMap;
        int heapSize;

        public NodeHeap(int size) {
            heap = new NodeRecord[size];
            indexMap = new HashMap<>();
            heapSize = 0;
        }

        public boolean isEmpty() {
            return heapSize == 0;
        }

        public boolean isEntered(NodeRecord node) {
            return indexMap.containsKey(node);
        }

        public boolean inHeap(NodeRecord node) {
            return isEntered(node) && indexMap.get(node) != -1;
        }

        // 因为该小根堆有以下要求： [1]入堆过的元素不再入堆 Ignore [2]未入堆过的元素入堆 add [3]在队中的元素更新 update
        public void addOrUpdateOrIgnore(NodeRecord node) {
            if (!isEntered(node)) {  // 新元素添加
                offer(node);
            }
            if (inHeap(node)) {
                resign(node);
            }
        }

        public void offer(NodeRecord node) {
            heap[heapSize++] = node;
            // 不要忘记调整indexMap
            indexMap.put(node, heapSize - 1);

            heapInsert(heapSize - 1);
        }

        public NodeRecord poll() {
            NodeRecord ans = heap[0];
            swap(0, --heapSize);
            heapify(0);
            // 弹出了 不要忘记调整 indexMap
            indexMap.put(ans, -1);
            return ans;
        }

        public void resign(NodeRecord node) {
            int index = indexMap.get(node);
            heapInsert(index);
            heapInsert(index);
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
                int smallest = left + 1 < heapSize ? (heap[left].distance < heap[left + 1].distance ? left : left + 1) : left;
                smallest = heap[smallest].distance < heap[index].distance ? smallest : index;
                if (smallest == index) {
                    return;
                }
                swap(smallest, index);
                index = smallest;
                left = index * 2 + 1;
            }
        }

        // 要记住 所有更新 节点位置 的操作 都要更新 反向索引表
        public void swap(int i, int j) {
            NodeRecord tmp = heap[i];
            heap[i] = heap[j];
            heap[j] = tmp;
            indexMap.put(heap[i], i);
            indexMap.put(heap[j], j);
        }
    }

    // 方法二
    // 仅仅使用 系统提供的堆实现dijkstra， 为了区别 已经确定的节点，所以使用了visited作为HashSet集合。
    public static HashMap<Node, Integer> dijkstraNew(Node head) {
        HashSet<Node> visited = new HashSet<>();
        PriorityQueue<NodeRecord> heap = new PriorityQueue<>(new MyComparator());
        HashMap<Node, Integer> ans = new HashMap<>();
        heap.add(new NodeRecord(head, 0));
        while (!heap.isEmpty()) {
            NodeRecord record = heap.poll();
            Node toNode = record.node;
            int distance = record.distance;
            if (visited.contains(toNode)) {     // 全局最小肯定也是局部最小
                continue;
            }
            ans.put(toNode, distance);          //弹出时才记录
            visited.add(toNode);
            for (Edge edge : toNode.edges) {
                heap.add(new NodeRecord(edge.to, distance + edge.weight));
            }
        }
        return ans;
    }


    public static class MyComparator implements Comparator<NodeRecord> {
        public int compare(NodeRecord o1, NodeRecord o2) {
            return o1.distance - o2.distance;
        }
    }



    // TEST
    public static class NodeHeap1 {
        private Node[] nodes; // 实际的堆结构
        // key 某一个node， value 上面堆中的位置
        private HashMap<Node, Integer> heapIndexMap;
        // key 某一个节点， value 从源节点出发到该节点的目前最小距离
        private HashMap<Node, Integer> distanceMap;
        private int size; // 堆上有多少个点

        public NodeHeap1(int size) {
            nodes = new Node[size];
            heapIndexMap = new HashMap<>();
            distanceMap = new HashMap<>();
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        // 有一个点叫node，现在发现了一个从源节点出发到达node的距离为distance
        // 判断要不要更新，如果需要的话，就更新
        public void addOrUpdateOrIgnore(Node node, int distance) {
            if (inHeap(node)) {
                distanceMap.put(node, Math.min(distanceMap.get(node), distance));
                insertHeapify(node, heapIndexMap.get(node));
            }
            if (!isEntered(node)) {
                nodes[size] = node;
                heapIndexMap.put(node, size);
                distanceMap.put(node, distance);
                insertHeapify(node, size++);
            }
        }

        public NodeRecord pop() {
            NodeRecord nodeRecord = new NodeRecord(nodes[0], distanceMap.get(nodes[0]));
            swap(0, size - 1);
            heapIndexMap.put(nodes[size - 1], -1);
            distanceMap.remove(nodes[size - 1]);
            // free C++同学还要把原本堆顶节点析构，对java同学不必
            nodes[size - 1] = null;
            heapify(0, --size);
            return nodeRecord;
        }

        private void insertHeapify(Node node, int index) {
            while (distanceMap.get(nodes[index]) < distanceMap.get(nodes[(index - 1) / 2])) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        private void heapify(int index, int size) {
            int left = index * 2 + 1;
            while (left < size) {
                int smallest = left + 1 < size && distanceMap.get(nodes[left + 1]) < distanceMap.get(nodes[left])
                        ? left + 1
                        : left;
                smallest = distanceMap.get(nodes[smallest]) < distanceMap.get(nodes[index]) ? smallest : index;
                if (smallest == index) {
                    break;
                }
                swap(smallest, index);
                index = smallest;
                left = index * 2 + 1;
            }
        }

        private boolean isEntered(Node node) {
            return heapIndexMap.containsKey(node);
        }

        private boolean inHeap(Node node) {
            return isEntered(node) && heapIndexMap.get(node) != -1;
        }

        private void swap(int index1, int index2) {
            heapIndexMap.put(nodes[index1], index2);
            heapIndexMap.put(nodes[index2], index1);
            Node tmp = nodes[index1];
            nodes[index1] = nodes[index2];
            nodes[index2] = tmp;
        }
    }

    // 改进后的dijkstra算法
    // 从head出发，所有head能到达的节点，生成到达每个节点的最小路径记录并返回
    public static HashMap<Node, Integer> dijkstra2(Node head, int size) {
        NodeHeap1 nodeHeap1 = new NodeHeap1(size);
        nodeHeap1.addOrUpdateOrIgnore(head, 0);
        HashMap<Node, Integer> result = new HashMap<>();
        while (!nodeHeap1.isEmpty()) {
            NodeRecord record = nodeHeap1.pop();
            Node cur = record.node;
            int distance = record.distance;
            for (Edge edge : cur.edges) {
                nodeHeap1.addOrUpdateOrIgnore(edge.to, edge.weight + distance);
            }
            result.put(cur, distance);
        }
        return result;
    }

    // O(N ^ 2) 暴力方法
    public static HashMap<Node, Integer> dijkstra3(Node from) {
        HashMap<Node, Integer> distanceMap = new HashMap<>();
        distanceMap.put(from, 0);
        // 打过对号的点
        HashSet<Node> selectedNodes = new HashSet<>();
        Node minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
        while (minNode != null) {
            //  原始点  ->  minNode(跳转点)   最小距离distance
            int distance = distanceMap.get(minNode);
            for (Edge edge : minNode.edges) {
                Node toNode = edge.to;
                if (!distanceMap.containsKey(toNode)) {
                    distanceMap.put(toNode, distance + edge.weight);
                } else { // toNode
                    distanceMap.put(edge.to, Math.min(distanceMap.get(toNode), distance + edge.weight));
                }
            }
            selectedNodes.add(minNode);
            minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
        }
        return distanceMap;
    }

    public static Node getMinDistanceAndUnselectedNode(HashMap<Node, Integer> distanceMap, HashSet<Node> touchedNodes) {
        Node minNode = null;
        int minDistance = Integer.MAX_VALUE;
        for (Entry<Node, Integer> entry : distanceMap.entrySet()) {
            Node node = entry.getKey();
            int distance = entry.getValue();
            if (!touchedNodes.contains(node) && distance < minDistance) {
                minNode = node;
                minDistance = distance;
            }
        }
        return minNode;
    }

    public static void main(String[] args) {
        int[][] matrix = {{2,3,5},
                {1,7,9},
                {5,4,6},
                {3,7,2},
                {3,2,3},
                {9,5,1},
                {2,5,7},
                {9,3,4}};
        Graph graph = GraphGenerator.generateGraph(matrix);
        int size = 9;
        Node head = graph.nodes.get(2); // 头节点
        HashMap<Node, Integer> ans1 = dijkstra(head, size);
        HashMap<Node, Integer> ans2 = dijkstra2(head, size);
        HashMap<Node, Integer> ans3 = dijkstra3(head);
        HashMap<Node, Integer> ans4 = dijkstraNew(head);

        printMap(ans1);
        System.out.println();
        printMap(ans2);
        System.out.println();
        printMap(ans3);
        System.out.println();
        printMap(ans4);
    }


    public static void printMap(HashMap<Node, Integer> map) {
        for (Entry<Node, Integer> entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey().val + ", Value: " + entry.getValue());
        }
     }
}
