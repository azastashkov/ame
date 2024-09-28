package ch6;

public abstract class AbstractBinaryTree<E> implements Traversable<E> {
    protected Node<E> root;

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
