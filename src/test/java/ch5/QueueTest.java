package ch5;

import ch4.FixedSizeArrayStack;
import ch4.LinkedListBasedStack;
import ch4.Stack;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
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

    // 5.7.2
    // Implement a queue using two stacks
    @Test
    public void testTwoStacksBasedQueue() {
        testQueueAdt(new LinkedListBasedQueue<>(4));
    }

    // 5.7.5
    // Given a queue Q containing n elements, transfer these items on to a stack S (initially empty) so that
    // front element of Q appears at the top of the stack and the order of all other items is preserved.
    // Using enqueue and dequeue operations for the queue, and push and pop operations for the stack, outline
    // an efficient O(n) algorithm to accomplish the task using only a constant amount of additional storage.
    @Test
    public void transferQueueItemsOnStackPreservingOrder() {
        int capacity = 5;
        Queue<Integer> queue = new FixedSizeCircularArrayQueue<>(capacity);
        for (int i = 1; i <= capacity; i++) {
            queue.enqueue(i);
        }

        Stack<Integer> stack = new FixedSizeArrayStack<>(capacity);
        for (int i = 1; i <= capacity; i++) {
            stack.push(queue.dequeue());
        }

        for (int i = 1; i <= capacity; i++) {
            queue.enqueue(stack.pop());
        }

        for (int i = 1; i <= capacity; i++) {
            stack.push(queue.dequeue());
        }

        for (int i = 1; i <= capacity; i++) {
            assertEquals(i, (int) stack.pop());
        }
    }

    // 5.7.9
    // Given a queue of integers, rearrange the elements by interleaving the first half of the list with the second one
    @Test
    public void interleaveQueueItems() {
        Queue<Integer> queue = new FixedSizeCircularArrayQueue<>(10);
        for (int i = 11; i <= 20; i++) {
            queue.enqueue(i);
        }

        int half = queue.size() / 2;
        Queue<Integer> firstHalf = new FixedSizeCircularArrayQueue<>(half);
        for (int i = 0; i < half; i++) {
            firstHalf.enqueue(queue.dequeue());
        }

        for (int i = 0; i < half; i++) {
            queue.enqueue(firstHalf.dequeue());
            queue.enqueue(queue.dequeue());
        }

        int[] result = new int[queue.size()];
        for (int i = 0; i < 10; i++) {
            result[i] = queue.dequeue();
        }

        int[] expected = { 11, 16, 12, 17, 13, 18, 14, 19, 15, 20 };
        assertArrayEquals(expected, result);
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
