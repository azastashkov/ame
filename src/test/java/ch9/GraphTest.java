package ch9;

import ch3.LinkedList;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GraphTest {
    private Graph graph;

    @Before
    public void setUp() {
        graph = new Graph(13);
        graph.addEdge(0, 5);
        graph.addEdge(4, 3);
        graph.addEdge(0, 1);
        graph.addEdge(9, 12);
        graph.addEdge(6, 4);
        graph.addEdge(5, 4);
        graph.addEdge(0, 2);
        graph.addEdge(11, 12);
        graph.addEdge(9, 10);
        graph.addEdge(0, 6);
        graph.addEdge(7, 8);
        graph.addEdge(9, 11);
        graph.addEdge(5, 3);
    }

    @Test
    public void testDepthFirstSearch() {
        DepthFirstSearch depthFirstSearch = new DepthFirstSearch(graph, 0);

        assertTrue(depthFirstSearch.isConnected(1));
        assertTrue(depthFirstSearch.isConnected(3));

        assertFalse(depthFirstSearch.isConnected(7));
        assertFalse(depthFirstSearch.isConnected(9));
    }

    @Test
    public void testDepthFirstPath() {
        DepthFirstPaths depthFirstPaths = new DepthFirstPaths(graph, 0);

        assertTrue(depthFirstPaths.hasPathTo(1));
        assertTrue(depthFirstPaths.hasPathTo(3));

        assertFalse(depthFirstPaths.hasPathTo(7));
        assertFalse(depthFirstPaths.hasPathTo(9));

        LinkedList<Integer> list1 = new LinkedList<>();
        list1.add(0);
        list1.add(5);
        list1.add(4);
        list1.add(3);
        assertEquals(list1, depthFirstPaths.pathTo(3));

        LinkedList<Integer> list2 = new LinkedList<>();
        list2.add(0);
        list2.add(5);
        list2.add(4);
        list2.add(6);
        assertEquals(list2, depthFirstPaths.pathTo(6));
    }

    @Test
    public void testBreadthFirstPath() {
        BreadthFirstPaths breadthFirstPaths = new BreadthFirstPaths(graph, 0);

        assertTrue(breadthFirstPaths.hasPathTo(1));
        assertTrue(breadthFirstPaths.hasPathTo(3));

        assertFalse(breadthFirstPaths.hasPathTo(7));
        assertFalse(breadthFirstPaths.hasPathTo(9));

        LinkedList<Integer> list1 = new LinkedList<>();
        list1.add(0);
        list1.add(5);
        list1.add(3);
        assertEquals(list1, breadthFirstPaths.pathTo(3));

        LinkedList<Integer> list2 = new LinkedList<>();
        list2.add(0);
        list2.add(6);
        assertEquals(list2, breadthFirstPaths.pathTo(6));
    }
}
