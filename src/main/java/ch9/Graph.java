package ch9;

import ch3.LinkedList;

public class Graph {
    private final int vertices;
    private final LinkedList<Integer>[] adjacent;

    private int edges;

    @SuppressWarnings("unchecked")
    public Graph(int vertices) {
        this.vertices = vertices;
        this.adjacent = new LinkedList[vertices];

        for (int v = 0; v < vertices; v++) {
            adjacent[v] = new LinkedList<>();
        }
    }

    public void addEdge(int v, int w) {
        adjacent[v].add(w);
        adjacent[w].add(v);
        edges++;
    }

    public int getVertices() {
        return vertices;
    }

    public LinkedList<Integer> adj(int v) {
        assert  v >= 0 && v < vertices;
        return adjacent[v];
    }
}
