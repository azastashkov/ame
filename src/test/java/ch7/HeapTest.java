package ch7;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
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

    // 7.7.21
    // Find max N integers from int stream
    @Test
    public void findMaxNIntegersFromIntStream() {
        final int n = 10;
        final int batchSize = 990;
        final int streamSize = 100000;
        final int heapCapacity = 1000;

        MinHeap<Integer> minHeap = new MinHeap<>(heapCapacity);

        for (int i = 0; i < n; i++) {
            minHeap.enqueue(i);
        }

        assertEquals(n, minHeap.size());

        for (int i = n; i < streamSize; i += batchSize) {
            int k = Math.min(i + batchSize, streamSize);

            for (int j = i; j < k; j++) {
                minHeap.enqueue(j);
            }

            for (int j = i; j < k; j++) {
                minHeap.dequeue();
            }

            assertEquals(n, minHeap.size());
        }

        assertEquals(n, minHeap.size());

        for (int i = streamSize - n; i < streamSize; i++) {
            assertEquals(i, (int) minHeap.dequeue());
        }
    }

    // 7.7.25
    // Given 2 arrays A and B each with n elements, find largest n pairs (A[i], B[j])
    @Test
    public void findLargestNPairsOfTwoArrays() {
        final int n = 100;

        MinHeap<Integer> minHeap1 = new MinHeap<>(n);
        MinHeap<Integer> minHeap2 = new MinHeap<>(n);

        for (int i = 0, j = n; i < n; i++, j++) {
            minHeap1.enqueue(i);
            minHeap2.enqueue(j);
        }

        for (int i = 0, j = n; i < n; i++, j++) {
            assertEquals(i, (int) minHeap1.dequeue());
            assertEquals(j, (int) minHeap2.dequeue());
        }
    }

    // 7.7.28
    // Maximum sum in sliding window
    @Test
    public void findMaximumSumInSlidingWindow() {
        final int k = 3;
        final Integer[] array = new Integer[] { 1, 3, -1, -3, 5, 3, 6, 7 };
        final Integer[] result = new Integer[array.length - k + 1];

        MaxHeap<IndexedValue<Integer>> maxHeap = new MaxHeap<>(array.length);

        for (int i = 0; i < k; i++) {
            maxHeap.enqueue(new IndexedValue<>(array[i], i));
        }

        result[0] = maxHeap.peek().getValue();

        for (int i = k; i < array.length; i++) {
            maxHeap.enqueue(new IndexedValue<>(array[i], i));

            while (maxHeap.peek().getIndex() <= i - k) {
                maxHeap.dequeue();
            }

            result[i - k + 1] = maxHeap.peek().getValue();
        }

        assertArrayEquals(new Integer[] {3, 3, 5, 5, 6, 7 }, result);
    }
}
