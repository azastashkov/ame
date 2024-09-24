package ch4;

import ch3.LinkedList;

public class LinkedListBasedStack<E> extends AbstractStack<E> {
    private final LinkedList<E> list = new LinkedList<>();

    public LinkedListBasedStack(int capacity) {
        super(capacity);
    }

    public int size() {
        return list.size();
    }

    @Override
    protected void doPush(E value) {
        list.addFirst(value);
    }

    @Override
    protected E doPop() {
        return list.removeFirst();
    }

    @Override
    protected E doPeek() {
        return list.get(0);
    }
}
