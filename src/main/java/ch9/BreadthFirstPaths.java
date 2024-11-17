package ch9;

import ch3.LinkedList;
import ch5.LinkedListBasedQueue;
import ch5.Queue;

public class BreadthFirstPaths {
    private final int s;
    private boolean[] marked;
    private int[] edgeTo;

    public BreadthFirstPaths(Graph graph, int sourceVertex) {
        int nVertices = graph.getVertices();
        marked = new boolean[nVertices];
        edgeTo = new int[nVertices];
        this.s = sourceVertex;

        bfs(graph, s);
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

    private void bfs(Graph graph, int s) {
        Queue<Integer> queue = new LinkedListBasedQueue<>(graph.getVertices());
        marked[s] = true;
        queue.enqueue(s);

        while (!queue.isEmpty()) {
            int v = queue.dequeue();

            LinkedList<Integer> adj = graph.adj(v);
            for (int i = 0; i < adj.size(); i++) {
                int w = adj.get(i);
                if (!marked[w]) {
                    edgeTo[w] = v;
                    marked[w] = true;
                    queue.enqueue(w);
                }
            }
        }
    }
}
