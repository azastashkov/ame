package ch6;

import org.junit.Test;

import java.util.Arrays;
import java.util.Objects;

import static org.junit.Assert.assertArrayEquals;

public class BinaryTreeTest {
    @Test
    public void testBinaryTree() {
        Integer[] values = getBinaryTreeValues();
        BinaryTree<Integer> binaryTree = BinaryTree.of(values);
        testBinaryTreeAdt(binaryTree, Arrays.stream(values).filter(Objects::nonNull).toArray().length);
    }

    private <E> void testBinaryTreeAdt(BinaryTree<E> binaryTree, int capacity) {
        NodeCollectorVisitorAction<E> preOrderCollector = new NodeCollectorVisitorAction<>(capacity);
        binaryTree.traverse(new PreOrderNodeVisitor<>(preOrderCollector));
        assertArrayEquals(new Integer[] { 1, 2, 3, 4, 6, 5 }, preOrderCollector.getArray());

        NodeCollectorVisitorAction<E> inOrderCollector = new NodeCollectorVisitorAction<>(capacity);
        binaryTree.traverse(new InOrderNodeVisitor<>(inOrderCollector));
        assertArrayEquals(new Integer[] { 2, 1, 4, 6, 3, 5 }, inOrderCollector.getArray());

        NodeCollectorVisitorAction<E> postOrderCollector = new NodeCollectorVisitorAction<>(capacity);
        binaryTree.traverse(new PostOrderNodeVisitor<>(postOrderCollector));
        assertArrayEquals(new Integer[] { 2, 6, 4, 5, 3, 1 }, postOrderCollector.getArray());
    }

    private Integer[] getBinaryTreeValues() {
        //        1
        //       / \
        //      2   3
        //         / \
        //        4   5
        //         \
        //          6
        return new Integer[] { 1, 2, 3, null, null, 4, 5, null, 6 };
    }
}
