package ch7;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HeapTest {
    @Test
    public void testMaxHeap() {
        MaxHeap<Integer> heap = new MaxHeap<>(new Integer[] { 7, 5, 6, 1, 4, 2, 3 });
        assertEquals(7, (int) heap.dequeue());
    }

    @Test
    public void testMinHeap() {
        MinHeap<Integer> heap = new MinHeap<>(new Integer[] { 7, 5, 6, 1, 4, 2, 3 });
        assertEquals(1, (int) heap.dequeue());
    }
}
