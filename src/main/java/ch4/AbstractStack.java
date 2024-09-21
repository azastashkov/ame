package ch4;

import java.util.EmptyStackException;

public abstract class AbstractStack<E extends Comparable<E>> implements Stack<E> {
    protected final int capacity;

    public AbstractStack(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void push(E value) {
        checkUpperBound();
        doPush(value);
    }

    @Override
    public E pop() {
        checkLowerBound();
        return doPop();
    }

    protected abstract void doPush(E value);

    protected abstract E doPop();

    private void checkUpperBound() {
        if (size() == capacity) {
            throw new StackOverflowException();
        }
    }

    private void checkLowerBound() {
        if (size() == 0) {
            throw new EmptyStackException();
        }
    }
}
