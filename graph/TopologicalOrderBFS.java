package graph;

import java.util.*;

// https://www.lintcode.com/problem/topological-sorting
public class TopologicalOrderBFS {

    public static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }


    public ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        // 先对 给定的节点，进行入度统计，统计方式如下
        HashMap<DirectedGraphNode, Integer> inMap = new HashMap<>();
        Queue<DirectedGraphNode> zeroInQueue = new LinkedList<>();
        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        // 1. 遍历点集LIST， 将每个节点先加入到 统计入度的哈希表 中
        for (DirectedGraphNode node : graph) {
            inMap.put(node, 0);
        }
        // 2. 根据邻接表 计算每个节点的入度
        for (DirectedGraphNode node : graph) {
            for (DirectedGraphNode nei : node.neighbors) {
                inMap.put(nei, inMap.get(nei) + 1);
            }
        }
        // 结束入度统计
        // 遍历 集合，找到入度为0的节点
        for (DirectedGraphNode node : inMap.keySet()) {
            if (inMap.get(node) == 0) {
                zeroInQueue.add(node);
            }
        }

        // 此时，queue初始化完毕，里面存放的是 inDegree==0 的节点
        // init完毕，开始循环
        while (!zeroInQueue.isEmpty()) {
            DirectedGraphNode cur = zeroInQueue.poll();
            ans.add(cur);    // 加入答案
            // 令其 邻接节点的 入度 -1
            for (DirectedGraphNode nei : cur.neighbors) {
                inMap.put(nei, inMap.get(nei) - 1);
                if (inMap.get(nei) == 0) {
                    zeroInQueue.add(nei);
                }
            }
        }
        return ans;
    }
}