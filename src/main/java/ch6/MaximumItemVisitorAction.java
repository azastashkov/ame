package ch6;

public class MaximumItemVisitorAction<T extends Comparable<T>> implements VisitorAction<T> {
    private T max;

    @Override
    public void accept(BinaryTree.Node<T> node) {
        if (max == null) {
            max = node.item;
        } else {
            if (max.compareTo(node.item) < 0) {
                max = node.item;
            }
        }
    }

    public T getMax() {
        return max;
    }
}
