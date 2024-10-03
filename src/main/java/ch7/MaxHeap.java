package ch7;

import java.util.Comparator;

public class MaxHeap<E extends Comparable<E>> extends AbstractPriorityQueue<E> {
    public MaxHeap(int capacity) {
        super(capacity, Comparator.naturalOrder());
    }

    public MaxHeap(E[] values) {
        super(values, Comparator.naturalOrder());
    }
}
