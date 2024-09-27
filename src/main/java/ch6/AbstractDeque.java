package ch6;

public abstract class AbstractDeque<E> implements Deque<E> {
    protected final int capacity;
    protected int size;

    public AbstractDeque(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void addFirst(E element) {
        checkUpperBound();
        doAddFirst(element);
    }

    @Override
    public void addLast(E element) {
        checkUpperBound();
        doAddLast(element);
    }

    @Override
    public E removeFirst() {
        checkLowerBound();
        return doRemoveFirst();
    }

    @Override
    public E removeLast() {
        checkLowerBound();
        return doRemoveLast();
    }

    @Override
    public int size() {
        return size;
    }

    protected abstract void doAddFirst(E element);

    protected abstract void doAddLast(E element);

    protected abstract E doRemoveFirst();

    protected abstract E doRemoveLast();

    private void checkUpperBound() {
        if (size() == capacity) {
            throw new DequeOverflowException();
        }
    }

    private void checkLowerBound() {
        if (size() == 0) {
            throw new DequeUnderflowException();
        }
    }
}
