package ch4;

public class DynamicArrayStack<E extends Comparable<E>> extends FixedSizeArrayStack<E> {
    public DynamicArrayStack(int capacity) {
        this(capacity, 1 << 8);
    }

    @SuppressWarnings("unchecked")
    public DynamicArrayStack(int capacity, int initialCapacity) {
        super(capacity);
        array = (E[]) new Comparable[initialCapacity];
    }

    @Override
    protected void doPush(E value) {
        if (size() == array.length) {
            resize(Math.min(array.length << 1, capacity));
        }
        super.doPush(value);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected E doPop() {
        if (size() == capacity / 4) {
            resize(Math.max(1, top << 1));
        }
        return super.doPop();
    }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        E[] newArray = (E[]) new Comparable[newCapacity];
        System.arraycopy(array, 0, newArray, 0, top);
        array = newArray;
    }
}
