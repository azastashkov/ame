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

    public static <E> BinaryTree<E> ofPreInTraversals(E[] preOrder, E[] inOrder) {
        if (inOrder.length == 0 || inOrder.length != preOrder.length) {
            return new BinaryTree<>();
        }

        Node<E> rootNode = createPreInNode(preOrder, 0, preOrder.length - 1,
                inOrder, 0, inOrder.length - 1);

        return new BinaryTree<>(rootNode);
    }

    public static <E> BinaryTree<E> ofPostInTraversals(E[] postOrder, E[] inOrder) {
        if (inOrder.length == 0 || inOrder.length != postOrder.length) {
            return new BinaryTree<>();
        }

        Node<E> rootNode = createPostInNode(postOrder, 0,postOrder.length - 1,
                inOrder, 0, inOrder.length - 1);

        return new BinaryTree<>(rootNode);
    }

    private static <E> Node<E> createPreInNode(E[] preOrder, int preStart, int preEnd, E[] inOrder, int inStart, int inEnd) {
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

        node.left = createPreInNode(preOrder, preStart + 1, preStart + offset - inStart,
                inOrder, inStart, offset - 1);
        node.right = createPreInNode(preOrder, preStart + offset - inStart + 1, preEnd,
                inOrder, offset + 1, inEnd);

        return node;
    }

    private static <E> Node<E> createPostInNode(E[] postOrder, int postStart, int postEnd, E[] inOrder, int inStart, int inEnd) {
        if (inStart > inEnd) {
            return null;
        }

        E item = postOrder[postEnd];
        Node<E> node = new Node<>(item);

        if (inStart == inEnd) {
            return node;
        }

        int offset = inStart;
        for (; offset < inEnd; offset++) {
            if (inOrder[offset] == item) {
                break;
            }
        }

        node.left = createPostInNode(postOrder, postStart, postStart - inStart + offset - 1,
                inOrder, inStart, offset - 1);
        node.right = createPostInNode(postOrder, postEnd - inEnd + offset, postEnd - 1,
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
        public Node<E> nextSibling;

        public Node(E item) {
            this.item = item;
        }

        @Override
        public void traverse(Visitor<E> visitor) {
            visitor.visit(this);
        }
    }
}
