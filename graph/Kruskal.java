package graph;

import java.util.HashMap;
import java.util.Set;
import java.util.Collection;
import java.util.Stack;
import java.util.*;

public class Kruskal {

    public static Set<Edge> kruskalMST(Graph graph) {
        UnionFind uf = new UnionFind(graph.nodes.values());
        // 遍历边集，将每一条边放入小根堆
        PriorityQueue<Edge> heap = new PriorityQueue<>(new MyComparator());
        for (Edge edge : graph.edges) {   // M条边
            heap.add(edge);               // O(log M)
        }

        HashSet<Edge> result = new HashSet<>();
        while (!heap.isEmpty()) {         // M 条边
            Edge curEdge = heap.poll();   // O(log M)
            if (uf.isSameSet(curEdge.from, curEdge.to)) {   // O(1)
                continue;
            } else {
                result.add(curEdge);
                uf.union(curEdge.from, curEdge.to);
            }
        }
        return result;
    }

    public static class MyComparator implements Comparator<Edge> {
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    public static class UnionFind {
        HashMap<Node, Node> parentMap;
        HashMap<Node, Integer> sizeMap;

        public UnionFind(Collection<Node> nodes) {
            parentMap = new HashMap<>();
            sizeMap = new HashMap<>();
            for (Node node : nodes) {
                parentMap.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public Node find(Node cur) {
            Stack<Node> stack = new Stack<>();
            while (cur != parentMap.get(cur)) {
                stack.push(cur);
                cur = parentMap.get(cur);
            }
            while (!stack.isEmpty()) {
                parentMap.put(stack.pop(), cur);
            }
            return cur;
        }

        public boolean isSameSet(Node a, Node b) {
            return find(a) == find(b);
        }

        public void union(Node aNode, Node bNode) {
            Node aHead = find(aNode);
            Node bHead = find(bNode);
            if (aHead == bHead) {
                return;
            } else {
                int aSize = sizeMap.get(aHead);
                int bSize = sizeMap.get(bHead);
                Node big = aSize > bSize ? aHead : bHead;
                Node small = big == aHead ? bHead : aHead;
                parentMap.put(small, big);
                sizeMap.put(big, sizeMap.get(big) + sizeMap.get(small));
                sizeMap.remove(small);
            }
        }
    }


}
