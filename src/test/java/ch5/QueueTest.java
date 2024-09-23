package ch5;

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
}
