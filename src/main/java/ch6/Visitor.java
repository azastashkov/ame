package ch6;

public interface Visitor<E> {
    void visit(AbstractBinaryTree.Node<E> node);
}
