package ch6;

public class PostOrderNodeVisitor<E> extends AbstractVisitor<E> {
    public PostOrderNodeVisitor(VisitorAction<E> action) {
        super(action);
    }

    @Override
    public void visit(BinaryTree.Node<E> node) {
        if (node != null) {
            visit(node.left);
            visit(node.right);
            action.accept(node);
        }
    }
}