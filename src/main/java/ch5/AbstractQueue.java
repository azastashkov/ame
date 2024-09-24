package ch5;

public abstract class AbstractQueue<E> implements Queue<E> {
    protected final int capacity;
    protected int size;

    public AbstractQueue(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void enqueue(E element) {
        checkUpperBound();
        doEnqueue(element);
    }

    @Override
    public E dequeue() {
        checkLowerBound();
        return doDequeue();
    }

    @Override
    public int size() {
        return size;
    }

    protected abstract void doEnqueue(E element);

    protected abstract E doDequeue();

    private void checkUpperBound() {
        if (size() == capacity) {
            throw new QueueOverflowException();
        }
    }

    private void checkLowerBound() {
        if (size() == 0) {
            throw new QueueUnderflowException();
        }
    }
}
