package ch7;

import java.util.Comparator;

public class MinHeap<E extends Comparable<E>> extends AbstractPriorityQueue<E> {
    public MinHeap(int capacity) {
        super(capacity, Comparator.reverseOrder());
    }

    public MinHeap(E[] values) {
        super(values, Comparator.reverseOrder());
    }
}
