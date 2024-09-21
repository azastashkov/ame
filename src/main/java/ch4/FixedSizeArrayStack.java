package ch4;

public class FixedSizeArrayStack<E extends Comparable<E>> extends AbstractStack<E> {
    protected E[] array;
    protected int top;

    @SuppressWarnings("unchecked")
    public FixedSizeArrayStack(int capacity) {
        super(capacity);
        array = (E[]) new Comparable[capacity];
    }

    @Override
    public int size() {
        return top;
    }

    @Override
    protected void doPush(E value) {
        array[top++] = value;
    }

    @Override
    protected E doPop() {
        E value = array[top - 1];
        array[--top] = null;
        return value;
    }

    @Override
    protected E doPeek() {
        return array[top - 1];
    }
}
