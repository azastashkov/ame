package ch6;

public class NodeCollectorVisitorAction<T> implements VisitorAction<T> {
    private final T[] array;
    private int size;

    @SuppressWarnings("unchecked")
    public NodeCollectorVisitorAction(int capacity) {
        this.array = (T[]) new Object[capacity];
    }

    public T[] getArray() {
        return array;
    }

    public int getSize() {
        return size;
    }

    @Override
    public void accept(BinaryTree.Node<T> node) {
        array[size++] = node.item;
    }
}
