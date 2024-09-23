package ch5;

import ch4.LinkedListBasedStack;
import ch4.Stack;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class QueueTest {
    @Test
    public void testFixedSizeCircularArrayQueue() {
        testQueueAdt(new FixedSizeCircularArrayQueue<>(4));
    }

    @Test
    public void testDynamicCircularArrayQueue() {
        testQueueAdt(new DynamicCircularArrayQueue<>(4));
    }

    @Test
    public void testLinkedListBasedQueue() {
        testQueueAdt(new LinkedListBasedQueue<>(4));
    }

    // 5.7.1
    // Reverse a queue
    @Test
    public void reverseQueue() {
        int capacity = 5;
        Queue<Integer> queue = new LinkedListBasedQueue<>(capacity);
        for (int i = 1; i <= capacity; i++ ) {
            queue.enqueue(i);
        }

        assertEquals(capacity, queue.size());

        reverse(queue);

        for (int i = capacity; i >= 1; i--) {
            assertEquals(i, (int) queue.dequeue());
        }

        assertEquals(0, queue.size());
    }

    private void testQueueAdt(Queue<Integer> queue) {
        for (int i = 1; i <= 4; i++) {
            queue.enqueue(i);
        }

        assertEquals(4, queue.size());

        for (int i = 1; i <= 4; i++) {
            assertEquals(i, (int) queue.dequeue());
        }

        assertEquals(0, queue.size());

        assertThrows(QueueUnderflowException.class, queue::dequeue);

        assertThrows(QueueOverflowException.class, () -> {
            for (int i = 0; i < 5; i++) {
                queue.enqueue(i);
            }
        });
    }

    private <T extends Comparable<T>> void reverse(Queue<T> queue) {
        int size = queue.size();
        Stack<T> stack = new LinkedListBasedStack<>(size);
        for (int i = 0; i < size; i++) {
            stack.push(queue.dequeue());
        }

        for (int i = 0; i < size; i++) {
            queue.enqueue(stack.pop());
        }
    }
}
