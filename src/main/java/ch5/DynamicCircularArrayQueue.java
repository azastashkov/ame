package ch5;

public class DynamicCircularArrayQueue<E> extends FixedSizeCircularArrayQueue<E> {
    public DynamicCircularArrayQueue(int capacity) {
        this(capacity, 1 << 8);
    }

    @SuppressWarnings("unchecked")
    public DynamicCircularArrayQueue(int capacity, int initialCapacity) {
        super(capacity);
        array = (E[]) new Object[initialCapacity];
    }

    @Override
    protected void doEnqueue(E element) {
        if (size == array.length) {
            expand();
        }
        super.doEnqueue(element);
    }

    @Override
    protected E doDequeue() {
        if (size == capacity / 4) {
            shrink();
        }
        return super.doDequeue();
    }

    private void expand() {
        resize(Math.min(array.length << 1, capacity));
    }

    private void shrink() {
        resize(Math.max(1, size << 1));
    }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        E[] newArray = (E[]) new Object[newCapacity];
        for (int i = headIndex; i <= tailIndex; i++) {
            newArray[i - headIndex] = array[i % array.length];
        }
        array = newArray;
        headIndex = 0;
        tailIndex = array.length - 1;
    }
}
