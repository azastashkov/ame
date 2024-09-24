package ch5;

public class FixedSizeCircularArrayQueue<E> extends AbstractQueue<E> {
    protected E[] array;
    protected int headIndex;
    protected int tailIndex;

    @SuppressWarnings("unchecked")
    public FixedSizeCircularArrayQueue(int capacity) {
        super(capacity);
        array = (E[]) new Object[capacity];
    }

    @Override
    protected void doEnqueue(E element) {
        array[tailIndex] = element;
        tailIndex = (tailIndex + 1) % array.length;
        size++;
    }

    @Override
    protected E doDequeue() {
        E element = array[headIndex];
        headIndex = (headIndex + 1) % array.length;
        size--;
        return element;
    }
}
