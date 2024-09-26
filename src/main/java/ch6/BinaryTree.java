package ch6;

import ch5.FixedSizeCircularArrayQueue;
import ch5.Queue;

public class BinaryTree<E> implements Traversable<E> {
    private Node<E> root;

    private BinaryTree() {
    }

    private BinaryTree(Node<E> root) {
        this.root = root;
    }

    public static <E> BinaryTree<E> of(E[] values) {
        if (values == null || values.length == 0 || values[0] == null) {
            return new BinaryTree<>();
        }

        Node<E> rootNode = new Node<>(values[0]);
        Queue<Node<E>> nodeQueue = new FixedSizeCircularArrayQueue<>(values.length);
        nodeQueue.enqueue(rootNode);

        int valPtr = 1;
        while (!nodeQueue.isEmpty()) {
            E leftVal = (valPtr < values.length) ? values[valPtr++] : null;
            E rightVal = (valPtr < values.length) ? values[valPtr++] : null;

            Node<E> node = nodeQueue.dequeue();

            if (leftVal != null) {
                Node<E> leftNode = new Node<>(leftVal);
                node.left = leftNode;
                nodeQueue.enqueue(leftNode);
            }

            if (rightVal != null) {
                Node<E> rightNode = new Node<>(rightVal);
                node.right = rightNode;
                nodeQueue.enqueue(rightNode);
            }
        }

        return new BinaryTree<>(rootNode);
    }

    public static <E> BinaryTree<E> of(E[] inOrder, E[] preOrder) {
        if (inOrder.length == 0 || inOrder.length != preOrder.length) {
            return new BinaryTree<>();
        }

        Node<E> rootNode = createNode(preOrder, 0, preOrder.length - 1,
                inOrder, 0, inOrder.length - 1);

        return new BinaryTree<>(rootNode);
    }

    private static <E> Node<E> createNode(E[] preOrder, int preStart, int preEnd, E[] inOrder, int inStart, int inEnd) {
        if (preStart > preEnd || inStart > inEnd) {
            return null;
        }

        E item = preOrder[preStart];
        Node<E> node = new Node<>(item);

        int offset = inStart;
        for (; offset < inEnd; offset++) {
            if (inOrder[offset] == item) {
                break;
            }
        }

        node.left = createNode(preOrder, preStart + 1, preStart + offset - inStart,
                inOrder, inStart, offset - 1);
        node.right = createNode(preOrder, preStart + offset - inStart + 1, preEnd,
                inOrder, offset + 1, inEnd);

        return node;
    }

    @Override
    public void traverse(Visitor<E> visitor) {
        if (root == null) {
            throw new IllegalStateException("Cannot traverse an empty tree");
        }

        root.traverse(visitor);
    }

    public Node<E> getRoot() {
        return root;
    }

    public static class Node<E> implements Traversable<E> {
        public E item;
        public Node<E> left;
        public Node<E> right;

        public Node(E item) {
            this.item = item;
        }

        @Override
        public void traverse(Visitor<E> visitor) {
            visitor.visit(this);
        }
    }
}
