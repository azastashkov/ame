package ch9;

import ch3.LinkedList;

public class DepthFirstSearch {
    private final boolean[] connected;
    private int connectedCount;

    public DepthFirstSearch(Graph graph, int sourceVertex) {
        this.connected = new boolean[graph.getVertices()];
        dfs(graph, sourceVertex);
    }

    public boolean isConnected(int v) {
        return connected[v];
    }

    private void dfs(Graph graph, int v) {
        connected[v] = true;
        connectedCount++;

        LinkedList<Integer> adj = graph.adj(v);
        for (int i = 0; i < adj.size(); i++) {
            if (!connected[adj.get(i)]) {
                dfs(graph, adj.get(i));
            }
        }
    }
}
