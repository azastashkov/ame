package ch6;

import ch4.FixedSizeArrayStack;
import ch4.Stack;
import ch5.FixedSizeCircularArrayQueue;
import ch5.Queue;
import org.junit.Test;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class BinaryTreeTest {
    @Test
    public void testBinaryTree() {
        Integer[] values = getBinaryTreeValues();
        BinaryTree<Integer> binaryTree = BinaryTree.of(values);
        testBinaryTreeAdt(binaryTree, filterNullValues(values).length);
    }

    // 6.4.1
    // Find maximum item in binary tree
    @Test
    public void findMaximumItemInBinaryTree() {
        Integer[] values = getBinaryTreeValues();
        BinaryTree<Integer> binaryTree = BinaryTree.of(values);

        MaximumItemVisitorAction<Integer> maximumItemVisitorAction = new MaximumItemVisitorAction<>();
        binaryTree.traverse(new InOrderNodeVisitor<>(maximumItemVisitorAction));

        assertEquals(6, (int) maximumItemVisitorAction.getMax());
    }

    // 6.4.3
    // Find an item in binary tree
    @Test
    public void findItemInBinaryTree() {
        Integer[] values = getBinaryTreeValues();
        BinaryTree<Integer> binaryTree = BinaryTree.of(values);

        final int searchedItem = 4;
        AtomicInteger result = new AtomicInteger();

        binaryTree.traverse(root -> {
            Queue<BinaryTree.Node<Integer>> queue = new FixedSizeCircularArrayQueue<>(filterNullValues(values).length);
            queue.enqueue(root);

            while (!queue.isEmpty()) {
                BinaryTree.Node<Integer> node = queue.dequeue();

                if (node.item != null && node.item.equals(searchedItem)) {
                    result.set(node.item);
                    return;
                }

                if (node.left != null) {
                    queue.enqueue(node.left);
                }

                if (node.right != null) {
                    queue.enqueue(node.right);
                }
            }
        });

        assertEquals(searchedItem, result.get());
    }

    // 6.4.5
    // Insert an item in binary tree
    @Test
    public void insertItemInBinaryTree() {
        Integer[] values = getBinaryTreeValues();
        BinaryTree<Integer> binaryTree = BinaryTree.of(values);

        final int insertedItem = 7;
        final int initialItemsCount = filterNullValues(values).length;

        binaryTree.traverse(root -> {
            Queue<BinaryTree.Node<Integer>> queue = new FixedSizeCircularArrayQueue(initialItemsCount);
            queue.enqueue(root);

            while (!queue.isEmpty()) {
                BinaryTree.Node<Integer> node = queue.dequeue();

                if (node.left != null) {
                    queue.enqueue(node.left);
                } else {
                    node.left = new BinaryTree.Node<>(insertedItem);
                    return;
                }

                if (node.right != null) {
                    queue.enqueue(node.right);
                } else {
                    node.right = new BinaryTree.Node<>(insertedItem);
                    return;
                }
            }
        });

        NodeCollectorVisitorAction<Integer> preOrderCollector
                = new NodeCollectorVisitorAction<>(initialItemsCount + 1);
        binaryTree.traverse(new PreOrderNodeVisitor<>(preOrderCollector));
        assertArrayEquals(new Integer[] { 1, 2, 7, 3, 4, 6, 5 }, preOrderCollector.getArray());
    }

    // 6.4.6
    // Find the size of binary tree
    @Test
    public void findSizeOfBinaryTree() {
        Integer[] values = getBinaryTreeValues();
        BinaryTree<Integer> binaryTree = BinaryTree.of(values);

        final int expectedSize = filterNullValues(values).length;

        NodeCollectorVisitorAction<Integer> preOrderCollector
                = new NodeCollectorVisitorAction<>(expectedSize);
        binaryTree.traverse(new PreOrderNodeVisitor<>(preOrderCollector));
        assertEquals(expectedSize, preOrderCollector.getSize());
    }

    // 6.4.9
    // Print binary tree in reverse order
    @Test
    public void printBinaryTreeInReverseOrder() {
        Integer[] values = getFullBinaryTreeValues();
        BinaryTree<Integer> binaryTree = BinaryTree.of(values);

        Stack<Integer> stack = new FixedSizeArrayStack<>(values.length);

        binaryTree.traverse(root -> {
            Queue<BinaryTree.Node<Integer>> queue = new FixedSizeCircularArrayQueue<>(values.length);
            queue.enqueue(root);

            while (!queue.isEmpty()) {
                BinaryTree.Node<Integer> node = queue.dequeue();

                if (node.left != null) {
                    queue.enqueue(node.left);
                }

                if (node.right != null) {
                    queue.enqueue(node.right);
                }

                stack.push(node.item);
            }
        });

        Integer[] expected = { 7, 6, 5, 4, 3, 2, 1 };

        int i = 0;
        while (!stack.isEmpty()) {
            assertEquals(expected[i++], stack.pop());
        }
    }

    // 6.4.10
    // Find the height of binary tree
    @Test
    public void findHeightOfBinaryTree() {
        Integer[] values = getBinaryTreeValues();
        BinaryTree<Integer> binaryTree = BinaryTree.of(values);

        final int expectedHeight = 4;
        final AtomicInteger result = new AtomicInteger();

        Visitor<Integer> visitor = new Visitor<Integer>() {
            @Override
            public void visit(BinaryTree.Node<Integer> node) {
                result.set(height(node));
            }

            private int height(BinaryTree.Node<Integer> node) {
                if (node == null) {
                    return 0;
                }

                return Math.max(height(node.left), height(node.right)) + 1;
            }
        };

        binaryTree.traverse(visitor);

        assertEquals(expectedHeight, result.get());
    }

    // 6.4.11
    // Find minimum depth of binary tree
    @Test
    public void findMinimumDepthOfBinaryTree() {
        Integer[] values = getBinaryTreeValues();
        BinaryTree<Integer> binaryTree = BinaryTree.of(values);

        final int expectedDepth = 2;
        final AtomicInteger result = new AtomicInteger();

        Visitor<Integer> visitor = new Visitor<Integer>() {
            @Override
            public void visit(BinaryTree.Node<Integer> node) {
                result.set(minimumDepth(node));
            }

            private int minimumDepth(BinaryTree.Node<Integer> node) {
                if (node == null) {
                    return 0;
                }

                if (node.left == null && node.right == null) {
                    return 1;
                }

                return Math.min(minimumDepth(node.left), minimumDepth(node.right)) + 1;
            }
        };

        binaryTree.traverse(visitor);

        assertEquals(expectedDepth, result.get());
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

    private Integer[] getFullBinaryTreeValues() {
        //        1
        //      /   \
        //     2     3
        //    / \   / \
        //   4   5 6   7
        return new Integer[]{1, 2, 3, 4, 5, 6, 7};
    }

    private Integer[] filterNullValues(Integer[] values) {
        return Arrays.stream(values).filter(Objects::nonNull).toArray(Integer[]::new);
    }
}
