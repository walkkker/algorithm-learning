package graph;

import java.util.*;

// 推荐的 DFS 方法，【深度】
//https://www.lintcode.com/problem/topological-sorting

import java.util.ArrayList;

public class TopologicalOrderDFS2 {


    public static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }

    public ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        // write your code here
        HashMap<DirectedGraphNode, Record> map = new HashMap<>();
        for (DirectedGraphNode node : graph) {
            process(node, map);
        }
        Record[] recordArr = new Record[map.size()];
        int i = 0;
        for (Record record : map.values()) {
            recordArr[i++] = record;
        }
        Arrays.sort(recordArr, new MyComparator());
        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        for (i = 0; i < recordArr.length; i++) {
            ans.add(recordArr[i].node);
        }
        return ans;
    }

    public int process(DirectedGraphNode head, HashMap<DirectedGraphNode, Record> map) {
        if (map.containsKey(head)) {
            return map.get(head).depth;
        }

        if (head == null) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        for (DirectedGraphNode next : head.neighbors) {
            max = Math.max(max, process(next, map));
        }
        map.put(head, new Record(head, ++max));
        return max;
    }

    public class Record {
        DirectedGraphNode node;
        int depth;

        public Record(DirectedGraphNode node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }

    public class MyComparator implements Comparator<Record> {
        public int compare(Record o1, Record o2) {
            return o2.depth - o1.depth;
        }
    }

}
