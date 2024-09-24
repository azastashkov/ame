package ch6;

public class InOrderNodeVisitor<E> extends AbstractVisitor<E> {
    public InOrderNodeVisitor(VisitorAction<E> action) {
        super(action);
    }

    @Override
    public void visit(BinaryTree.Node<E> node) {
        if (node != null) {
            visit(node.left);
            action.accept(node);
            visit(node.right);
        }
    }
}
