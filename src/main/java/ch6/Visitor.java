package ch6;

public interface Visitor<E> {
    void visit(BinaryTree.Node<E> node);
}
