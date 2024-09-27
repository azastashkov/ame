package ch6;

import ch3.LinkedList;
import ch4.FixedSizeArrayStack;
import ch4.Stack;
import ch5.FixedSizeCircularArrayQueue;
import ch5.Queue;
import org.junit.Test;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    // 6.4.14
    // Find the deepest node of a binary tree
    @Test
    public void findDeepestNodeOfBinaryTree() {
        Integer[] values = getBinaryTreeValues();
        BinaryTree<Integer> binaryTree = BinaryTree.of(values);

        final int expectedItem = 6;
        final AtomicInteger result = new AtomicInteger();

        binaryTree.traverse(root -> {
            Queue<BinaryTree.Node<Integer>> queue = new FixedSizeCircularArrayQueue<>(values.length);
            queue.enqueue(root);

            while (!queue.isEmpty()) {
                BinaryTree.Node<Integer> node = queue.dequeue();
                result.set(node.item);

                if (node.left != null) {
                    queue.enqueue(node.left);
                }

                if (node.right != null) {
                    queue.enqueue(node.right);
                }
            }
        });

        assertEquals(expectedItem, result.get());
    }

    // 6.4.15
    // Delete an item from binary tree
    @Test
    public void deleteItemFromBinaryTree() {
        Integer[] values = getBinaryTreeValues();
        BinaryTree<Integer> binaryTree = BinaryTree.of(values);

        final int deletedItem = 4;
        final int nodesInTree = filterNullValues(values).length;

        Visitor<Integer> visitor = new Visitor<Integer>() {
            @Override
            public void visit(BinaryTree.Node<Integer> root) {
                Queue<BinaryTree.Node<Integer>> queue = new FixedSizeCircularArrayQueue<>(nodesInTree);
                queue.enqueue(root);
                BinaryTree.Node<Integer> deepest = root, deleted = null;

                while (!queue.isEmpty()) {
                    deepest = queue.dequeue();

                    if (deepest.item.equals(deletedItem)) {
                        deleted = deepest;
                    }

                    if (deepest.left != null) {
                        queue.enqueue(deepest.left);
                    }

                    if (deepest.right != null) {
                        queue.enqueue(deepest.right);
                    }
                }

                if (deleted != null) {
                    deleted.item = deepest.item;
                    deleteDeepest(root, deepest);
                }
            }

            private void deleteDeepest(BinaryTree.Node<Integer> root, BinaryTree.Node<Integer> deepest) {
                if (root == null) {
                    return;
                }

                Queue<BinaryTree.Node<Integer>> queue = new FixedSizeCircularArrayQueue<>(nodesInTree);
                queue.enqueue(root);

                while (!queue.isEmpty()) {
                    BinaryTree.Node<Integer> node = queue.dequeue();

                    if (node.left != null) {
                        if (node.left == deepest) {
                            node.left = null;
                            return;
                        } else {
                            queue.enqueue(node.left);
                        }
                    }

                    if (node.right != null) {
                        if (node.right == deepest) {
                            node.right = null;
                            return;
                        } else {
                            queue.enqueue(node.right);
                        }
                    }
                }
            }
        };

        binaryTree.traverse(visitor);

        NodeCollectorVisitorAction<Integer> preOrderCollector = new NodeCollectorVisitorAction<>(5);
        binaryTree.traverse(new PreOrderNodeVisitor<>(preOrderCollector));
        assertArrayEquals(new Integer[] { 1, 2, 3, 6, 5 }, preOrderCollector.getArray());
    }

    // 6.4.19
    // Check that two binary trees are structurally identical
    @Test
    public void checkThatTwoBinaryTreesAreStructurallyIdentical() {
        Integer[] treeValues1 = { 1, 2, 3, null, null, 4, 5, null, 6 };
        Integer[] treeValues2 = { 7, 8, 9, null, null, 10, 11, null, 12 };

        BinaryTree<Integer> binaryTree1 = BinaryTree.of(treeValues1);
        BinaryTree<Integer> binaryTree2 = BinaryTree.of(treeValues2);

        assertTrue(structurallyIdentical(binaryTree1.getRoot(), binaryTree2.getRoot()));
    }

    // 6.4.21
    // Find the width of binary tree
    @Test
    public void findWidthOfBinaryTree() {
        Integer[] values = getFullBinaryTreeValues();
        BinaryTree<Integer> binaryTree = BinaryTree.of(values);

        final int expectedWidth = 4;
        final AtomicInteger maxWidth = new AtomicInteger();

        binaryTree.traverse(root -> {
            Queue<BinaryTree.Node<Integer>> queue = new FixedSizeCircularArrayQueue<>(values.length);
            queue.enqueue(root);

            int levelNodesCount;
            while (!queue.isEmpty()) {
                levelNodesCount = queue.size();
                maxWidth.set(Math.max(maxWidth.get(), levelNodesCount));

                while (levelNodesCount-- > 0) {
                    BinaryTree.Node<Integer> node = queue.dequeue();

                    if (node.left != null) {
                        queue.enqueue(node.left);
                    }

                    if (node.right != null) {
                        queue.enqueue(node.right);
                    }
                }
            }
        });

        assertEquals(expectedWidth, maxWidth.get());
    }

    // 6.4.23
    // Given a binary tree, find all its root-to-leaf paths
    @Test
    public void findAllRootToLeafPathsInBinaryTree() {
        Integer[] values = getBinaryTreeValues();
        BinaryTree<Integer> binaryTree = BinaryTree.of(values);

        final int nodesInTree = filterNullValues(values).length;
        final LinkedList<Integer[]> paths = new LinkedList<>();

        Visitor<Integer> visitor = new Visitor<Integer>() {
            private final Integer[] rootToNodePath = new Integer[nodesInTree];

            @Override
            public void visit(BinaryTree.Node<Integer> root) {
                findPaths(root, 0);
            }

            private void findPaths(BinaryTree.Node<Integer> node, int depth) {
                if (node == null) {
                    return;
                }

                rootToNodePath[depth++] = node.item;

                if (isLeaf(node)) {
                    Integer[] path = new Integer[depth];
                    System.arraycopy(rootToNodePath, 0, path, 0, depth);
                    paths.add(path);
                } else {
                    findPaths(node.left, depth);
                    findPaths(node.right, depth);
                }
            }
        };

        binaryTree.traverse(visitor);

        Integer[] path1 = paths.removeFirst();
        assertArrayEquals(new Integer[] { 1, 2 }, path1);

        Integer[] path2 = paths.removeFirst();
        assertArrayEquals(new Integer[] { 1, 3, 4, 6 }, path2);

        Integer[] path3 = paths.removeFirst();
        assertArrayEquals(new Integer[] { 1, 3, 5 }, path3);
    }

    // 6.4.24
    // Check whether there's a path in binary tree with the given sum
    @Test
    public void checkExistenceOfPathWithGivenSum() {
        Integer[] values = getBinaryTreeValues();
        BinaryTree<Integer> binaryTree = BinaryTree.of(values);

        final int sum = 14;
        final AtomicBoolean result = new AtomicBoolean();

        Visitor<Integer> visitor = new Visitor<Integer>() {
            @Override
            public void visit(BinaryTree.Node<Integer> root) {
                if (root == null) {
                    return;
                }

                result.set(hasPath(root, sum));
            }

            private boolean hasPath(BinaryTree.Node<Integer> node, int sum) {
                if (node == null) {
                    return false;
                }

                if (isLeaf(node) && node.item == sum) {
                    return true;
                }

                int remainder = sum - node.item;
                return hasPath(node.left, remainder) || hasPath(node.right, remainder);
            }
        };

        binaryTree.traverse(visitor);

        assertTrue(result.get());
    }

    // 6.4.27
    // Convert a binary tree to its mirror
    //        1                1
    //       / \              / \
    //      2   3            3   2
    //         / \    ->    / \
    //        4   5        5   4
    //         \              /
    //          6            6
    @Test
    public void convertBinaryTreeToItsMirror() {
        Integer[] values = getBinaryTreeValues();
        BinaryTree<Integer> binaryTree = BinaryTree.of(values);

        final int expectedSize = filterNullValues(values).length;

        binaryTree.traverse(root -> {
            Stack<BinaryTree.Node<Integer>> stack = new FixedSizeArrayStack<>(expectedSize);
            stack.push(root);

            while (!stack.isEmpty()) {
                BinaryTree.Node<Integer> node = stack.pop();

                if (node.left != null) {
                    stack.push(node.left);
                }

                if (node.right != null) {
                    stack.push(node.right);
                }

                BinaryTree.Node<Integer> tmp = node.left;
                node.left = node.right;
                node.right = tmp;
            }
        });

        NodeCollectorVisitorAction<Integer> preOrderCollector = new NodeCollectorVisitorAction<>(expectedSize);
        binaryTree.traverse(new PreOrderNodeVisitor<>(preOrderCollector));
        assertArrayEquals(new Integer[] { 1, 3, 5, 4, 6, 2 }, preOrderCollector.getArray());
    }

    // 6.4.29
    // Construct a binary tree from given pre-order and in-order traversals
    @Test
    public void constructBinaryTreeFromGivenPreOrderAndInOrderTraversals() {
        Character[] preOrder = { 'A', 'B', 'D', 'E', 'C', 'F' };
        Character[] inOrder = { 'D', 'B', 'E', 'A', 'F', 'C' };

        BinaryTree<Character> binaryTree = BinaryTree.ofPreInTraversals(preOrder, inOrder);

        NodeCollectorVisitorAction<Character> preOrderCollector = new NodeCollectorVisitorAction<>(preOrder.length);
        binaryTree.traverse(new PreOrderNodeVisitor<>(preOrderCollector));
        assertArrayEquals(preOrder, preOrderCollector.getArray());

        NodeCollectorVisitorAction<Character> inOrderCollector = new NodeCollectorVisitorAction<>(inOrder.length);
        binaryTree.traverse(new InOrderNodeVisitor<>(inOrderCollector));
        assertArrayEquals(inOrder, inOrderCollector.getArray());
    }

    // 6.4.30
    // Construct a binary tree from given post-order and in-order traversals
    @Test
    public void constructBinaryTreeFromGivenPostOrderAndInOrderTraversals() {
        Character[] postOrder = { 'D', 'E', 'B', 'F', 'C', 'A' };
        Character[] inOrder = { 'D', 'B', 'E', 'A', 'F', 'C' };

        BinaryTree<Character> binaryTree = BinaryTree.ofPostInTraversals(postOrder, inOrder);

        NodeCollectorVisitorAction<Character> postOrderCollector = new NodeCollectorVisitorAction<>(postOrder.length);
        binaryTree.traverse(new PostOrderNodeVisitor<>(postOrderCollector));

        System.out.println(Arrays.toString(postOrderCollector.getArray()));

        assertArrayEquals(postOrder, postOrderCollector.getArray());

        NodeCollectorVisitorAction<Character> inOrderCollector = new NodeCollectorVisitorAction<>(inOrder.length);
        binaryTree.traverse(new InOrderNodeVisitor<>(inOrderCollector));
        assertArrayEquals(inOrder, inOrderCollector.getArray());
    }

    // 6.4.33
    // Find the lowest common ancestor of two nodes in binary tree
    @Test
    public void findLowestCommonAncestor() {
        Integer[] values = getBinaryTreeValues();
        BinaryTree<Integer> binaryTree = BinaryTree.of(values);

        final AtomicInteger result = new AtomicInteger();

        Visitor<Integer> visitor = new Visitor<Integer>() {
            @Override
            public void visit(BinaryTree.Node<Integer> root) {
                BinaryTree.Node<Integer> lca = findLowestCommonAncestor(root, 6, 5);
                result.set(lca.item);
            }

            private BinaryTree.Node<Integer> findLowestCommonAncestor(BinaryTree.Node<Integer> node,
                                                                      Integer item1, Integer item2) {
                if (node == null) {
                    return null;
                }

                if (node.item.equals(item1) || node.item.equals(item2)) {
                    return node;
                }

                BinaryTree.Node<Integer> leftLca = findLowestCommonAncestor(node.left, item1, item2);
                BinaryTree.Node<Integer> rightLca = findLowestCommonAncestor(node.right, item1, item2);

                if (leftLca != null && rightLca != null) {
                    return node;
                }

                return leftLca != null ? leftLca : rightLca;
            }
        };

        binaryTree.traverse(visitor);

        assertEquals(3, result.get());
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
        return new Integer[] { 1, 2, 3, 4, 5, 6, 7 };
    }

    private Integer[] filterNullValues(Integer[] values) {
        return Arrays.stream(values).filter(Objects::nonNull).toArray(Integer[]::new);
    }

    private boolean structurallyIdentical(BinaryTree.Node<Integer> root1, BinaryTree.Node<Integer> root2) {
        if (root1 == null && root2 == null) {
            return true;
        }

        if (root1 == null || root2 == null) {
            return false;
        }

        return structurallyIdentical(root1.left, root2.left) && structurallyIdentical(root1.right, root2.right);
    }

    private boolean isLeaf(BinaryTree.Node<Integer> node) {
        return node.left == null && node.right == null;
    }
}
