package ch6;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BinarySearchTreeTest {
    @Test
    public void testBinarySearchTree() {
        Integer[] values = getBinarySearchTreeValues();
        BinarySearchTree<Integer> binarySearchTree = BinarySearchTree.of(values);
        testBinarySearchTreeAdt(binarySearchTree, values.length);
    }

    private void testBinarySearchTreeAdt(BinarySearchTree<Integer> binarySearchTree, int capacity) {
        NodeCollectorVisitorAction<Integer> preOrderCollector = new NodeCollectorVisitorAction<>(capacity);
        binarySearchTree.traverse(new PreOrderNodeVisitor<>(preOrderCollector));
        assertArrayEquals(new Integer[] { 4, 3, 1, 2, 6, 5, 7 }, preOrderCollector.getArray());

        NodeCollectorVisitorAction<Integer> inOrderCollector = new NodeCollectorVisitorAction<>(capacity);
        binarySearchTree.traverse(new InOrderNodeVisitor<>(inOrderCollector));
        assertArrayEquals(new Integer[] { 1, 2, 3, 4, 5, 6, 7 }, inOrderCollector.getArray());

        NodeCollectorVisitorAction<Integer> postOrderCollector = new NodeCollectorVisitorAction<>(capacity);
        binarySearchTree.traverse(new PostOrderNodeVisitor<>(postOrderCollector));
        assertArrayEquals(new Integer[] { 2, 1, 3, 5, 7, 6, 4 }, postOrderCollector.getArray());

        assertTrue(binarySearchTree.contains(5));

        assertEquals(1, (int) binarySearchTree.min());
        assertEquals(7, (int) binarySearchTree.max());

        binarySearchTree.delete(3);

        NodeCollectorVisitorAction<Integer> inOrderCollector2 = new NodeCollectorVisitorAction<>(capacity - 1);
        binarySearchTree.traverse(new InOrderNodeVisitor<>(inOrderCollector2));
        assertArrayEquals(new Integer[] { 1, 2, 4, 5, 6, 7 }, inOrderCollector2.getArray());
    }

    private Integer[] getBinarySearchTreeValues() {
        //        4
        //      /   \
        //     2     6
        //    / \   / \
        //   1   3 5   7
        return new Integer[] { 4, 3, 6, 1, 2, 5, 7 };
    }
}
