package graph;

public class Edge {
    int weight;
    Node from;
    Node to;

    public Edge(int w, Node f, Node t) {
        weight = w;
        from = f;
        to = t;
    }
}
