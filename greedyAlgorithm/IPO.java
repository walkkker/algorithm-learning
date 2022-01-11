package greedyAlgorithm;

import java.util.Comparator;
import java.util.PriorityQueue;

public class IPO {
    /*
    从给定项目中选择 最多 k 个不同项目的列表，以 最大化最终资本 ，并输出最终可获得的最多资本。
    w是初始资本。
     */
    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        PriorityQueue<Program> minCostQ = new PriorityQueue<>(new MinCost());
        PriorityQueue<Program> maxProfitQ = new PriorityQueue<>(new MaxProfit());
        for (int i = 0; i < capital.length; i++) {
            minCostQ.add(new Program(capital[i], profits[i]));
        }
        while (k-- > 0) {
            while (!minCostQ.isEmpty() && minCostQ.peek().c <= w) {
                maxProfitQ.offer(minCostQ.poll());
            }
            // 防止 当前 maxProfitQ为空，说明没有可以 购买的 项目
            if (maxProfitQ.isEmpty()) {
                return w;
            }
            w += maxProfitQ.poll().p;
        }
        return w;
    }

    public class Program {
        int c;
        int p;

        public Program(int c, int p) {
            this.c = c;
            this.p = p;
        }
    }

    public class MinCost implements Comparator<Program> {
        public int compare(Program o1, Program o2) {
            return o1.c - o2.c;
        }
    }

    public class MaxProfit implements Comparator<Program> {
        public int compare(Program o1, Program o2) {
            return o2.p - o1.p;
        }
    }


}
