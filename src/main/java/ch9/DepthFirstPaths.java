package ch9;

import ch3.LinkedList;

public class DepthFirstPaths {
    private final int s;
    private boolean[] marked;
    private int[] edgeTo;

    public DepthFirstPaths(Graph graph, int sourceVertex) {
        int nVertices = graph.getVertices();
        this.marked = new boolean[nVertices];
        this.edgeTo = new int[nVertices];
        this.s = sourceVertex;

        dfs(graph, s);
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public LinkedList<Integer> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }

        LinkedList<Integer> path = new LinkedList<>();
        for (int w = v; w != s; w = edgeTo[w]) {
            path.addFirst(w);
        }
        path.addFirst(s);

        return path;
    }

    private void dfs(Graph graph, int v) {
        marked[v] = true;

        LinkedList<Integer> adj = graph.adj(v);
        for (int i = 0; i < adj.size(); i++) {
            int w = adj.get(i);
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(graph, w);
            }
        }
    }
}
