package graph;

import java.util.ArrayList;

public class Node {
    int val;
    int in;
    int out;
    ArrayList<Node> nexts;
    ArrayList<Edge> edges;

    public Node(int v) {
        val = v;
        in = 0;
        out = 0;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }


}
