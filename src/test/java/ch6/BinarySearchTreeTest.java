package ch6;

import ch3.LinkedList;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BinarySearchTreeTest {
    @Test
    public void testBinarySearchTree() {
        Integer[] values = getFullBinarySearchTreeValues();
        BinarySearchTree<Integer> binarySearchTree = BinarySearchTree.of(values);
        testBinarySearchTreeAdt(binarySearchTree, values.length);
    }

    // 6.9.55
    // Given pointers to two nodes in a binary search tree, find the lowest common ancestor (LCA).
    // Assume that both values already exist in the tree.
    // LCA in BST: a < lca.item < b
    @Test
    public void findLowestCommonAncestor() {
        Integer[] values = getFullBinarySearchTreeValues();
        BinarySearchTree<Integer> binarySearchTree = BinarySearchTree.of(values);

        BstLcaVisitorAction<Integer> bstLcaVisitorAction1 = new BstLcaVisitorAction<>(2, 6);
        binarySearchTree.traverse(new PreOrderNodeVisitor<>(bstLcaVisitorAction1));
        assertEquals(4, (int) bstLcaVisitorAction1.getLca());

        BstLcaVisitorAction<Integer> bstLcaVisitorAction2 = new BstLcaVisitorAction<>(5, 7);
        binarySearchTree.traverse(new PreOrderNodeVisitor<>(bstLcaVisitorAction2));
        assertEquals(6, (int) bstLcaVisitorAction2.getLca());

        BstLcaVisitorAction<Integer> bstLcaVisitorAction3 = new BstLcaVisitorAction<>(1, 6);
        binarySearchTree.traverse(new PreOrderNodeVisitor<>(bstLcaVisitorAction3));
        assertEquals(4, (int) bstLcaVisitorAction3.getLca());
    }

    // 6.9.57
    // Give an algorithm to check whether the given binary tree is a BST or not
    @Test
    public void checkWhetherBinaryTreeIsBST() {
        Integer[] values = getFullBinarySearchTreeValues();
        BinarySearchTree<Integer> binarySearchTree = BinarySearchTree.of(values);

        NodeCollectorVisitorAction<Integer> inOrderCollector = new NodeCollectorVisitorAction<>(values.length);
        binarySearchTree.traverse(new InOrderNodeVisitor<>(inOrderCollector));
        assertArrayEquals(new Integer[] { 1, 2, 3, 4, 5, 6, 7 }, inOrderCollector.getArray());
    }

    // 6.9.61
    // Convert a sorted linked list to balanced binary search tree
    @Test
    public void convertSortedLinkedListToBalancedBinarySearchTree() {
        Integer[] values = getFullBinarySearchTreeValues();
        Arrays.sort(values);

        LinkedList<Integer> list = new LinkedList<>();
        Arrays.stream(values).forEach(list::add);

        BinarySearchTree<Integer> binarySearchTree = BinarySearchTree.of(list);

        NodeCollectorVisitorAction<Integer> inOrderCollector = new NodeCollectorVisitorAction<>(values.length);
        binarySearchTree.traverse(new InOrderNodeVisitor<>(inOrderCollector));
        assertArrayEquals(new Integer[] { 1, 2, 3, 4, 5, 6, 7 }, inOrderCollector.getArray());
    }

    // 6.9.65
    // Find the kth smallest element in binary search tree
    @Test
    public void findKthSmallestElementInBinarySearchTree() {
        Integer[] values = getFullBinarySearchTreeValues();
        BinarySearchTree<Integer> binarySearchTree = BinarySearchTree.of(values);

        NodeCollectorVisitorAction<Integer> inOrderCollector = new NodeCollectorVisitorAction<>(values.length);
        binarySearchTree.traverse(new InOrderNodeVisitor<>(inOrderCollector));

        final int k = 4;
        Object[] array = inOrderCollector.getArray();
        assertEquals(4, (int) array[k - 1]);
    }

    // 6.9.66
    // Floor and ceiling in binary search tree
    @Test
    public void findFloorAndCeilingInBinarySearchTree() {
        Integer[] values = getBinarySearchTreeValues();
        BinarySearchTree<Integer> binarySearchTree = BinarySearchTree.of(values);

        NodeCollectorVisitorAction<Integer> inOrderCollector = new NodeCollectorVisitorAction<>(values.length);
        binarySearchTree.traverse(new InOrderNodeVisitor<>(inOrderCollector));
        assertArrayEquals(new Integer[] { 1, 2, 3, 6, 10 }, inOrderCollector.getArray());

        assertEquals(3, (int) binarySearchTree.floor(5));
        assertEquals(6, (int) binarySearchTree.ceiling(5));
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
        //        2
        //      /   \
        //     1     6
        //          / \
        //         3   10
        return new Integer[] { 2, 1, 6, 3, 10 };
    }

    private Integer[] getFullBinarySearchTreeValues() {
        //        4
        //      /   \
        //     2     6
        //    / \   / \
        //   1   3 5   7
        return new Integer[] { 4, 3, 6, 1, 2, 5, 7 };
    }
}
