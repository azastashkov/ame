package ch7;

import ch5.AbstractQueue;

import java.util.Comparator;

public class AbstractPriorityQueue<E> extends AbstractQueue<E> {
    private final Comparator<E> comparator;
    private final E[] array;

    @SuppressWarnings("unchecked")
    public AbstractPriorityQueue(int capacity, Comparator<E> comparator) {
        super(capacity);
        this.comparator = comparator;
        array = (E[]) new Object[capacity + 1];
    }

    @SuppressWarnings("unchecked")
    public AbstractPriorityQueue(E[] values, Comparator<E> comparator) {
        super(values.length);
        this.comparator = comparator;

        size = values.length;
        array = (E[]) new Object[values.length + 1];

        System.arraycopy(values, 0, array, 1, size);

        for (int k = size / 2; k >= 1; k--) {
            sink(k);
        }

        assert isMaxHeap();
    }

    @Override
    public E peek() {
        return array[1];
    }

    @Override
    protected void doEnqueue(E element) {
        array[++size] = element;
        swim(size);

        assert isMaxHeap();
    }

    @Override
    protected E doDequeue() {
        E max = array[1];

        swap(1, size--);
        sink(1);

        array[size + 1] = null;

        assert isMaxHeap();

        return max;
    }

    private boolean less(int i, int j) {
        return comparator.compare(array[i], array[j]) < 0;
    }

    private void swap(int i, int j) {
        E tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    private void sink(int k) {
        while (2 * k <= size) {
            int j = 2 * k;

            if (j < size && less(j, j + 1)) {
                j++;
            }

            if (!less(k, j)) {
                break;
            }

            swap(k, j);

            k = j;
        }
    }

    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            swap(k / 2, k);
            k = k / 2;
        }
    }

    private boolean isMaxHeap() {
        for (int i = 1; i <= size; i++) {
            if (array[i] == null) {
                return false;
            }
        }

        for (int i = size + 1; i < array.length; i++) {
            if (array[i] != null) {
                return false;
            }
        }

        if (array[0] != null) {
            return false;
        }

        return isMaxHeapOrdered(1);
    }

    private boolean isMaxHeapOrdered(int k) {
        if (k > size) {
            return true;
        }

        int left = 2 * k;
        int right = 2 * k + 1;

        if (left <= size && less(k, left)) {
            return false;
        }

        if (right <= size && less(k, right)) {
            return false;
        }

        return isMaxHeapOrdered(left) && isMaxHeapOrdered(right);
    }
}
