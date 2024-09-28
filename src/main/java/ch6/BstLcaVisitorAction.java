package ch6;

public class BstLcaVisitorAction<T extends Comparable<T>> implements VisitorAction<T> {
    private final T a;
    private final T b;

    public BstLcaVisitorAction(T a, T b) {
        this.a = a;
        this.b = b;
    }

    private T lca;

    @Override
    public void accept(AbstractBinaryTree.Node<T> node) {
        if (lca == null) {
            if (a.compareTo(node.item) < 0 && b.compareTo(node.item) > 0) {
                lca = node.item;
            }
        }
    }

    public T getLca() {
        return lca;
    }
}
