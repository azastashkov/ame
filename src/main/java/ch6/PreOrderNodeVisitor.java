package ch6;

public class PreOrderNodeVisitor<E> extends AbstractVisitor<E> {
    public PreOrderNodeVisitor(VisitorAction<E> action) {
        super(action);
    }

    @Override
    public void visit(BinaryTree.Node<E> node) {
        if (node != null) {
            action.accept(node);
            visit(node.left);
            visit(node.right);
        }
    }
}
