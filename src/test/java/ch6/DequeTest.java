package ch6;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class DequeTest {
    @Test
    public void testDeque() {
        testDequeAdt(new LinkedListDeque<>(4));
    }

    private void testDequeAdt(Deque<Integer> deque) {
        for (int i = 1; i <= 4; i++) {
            deque.addLast(i);
        }

        assertEquals(4, deque.size());

        for (int i = 1; i <= 4; i++) {
            assertEquals(i, (int) deque.removeFirst());
        }

        assertEquals(0, deque.size());

        for (int i = 1; i <= 4; i++) {
            deque.addFirst(i);
        }

        assertEquals(4, deque.size());

        for (int i = 4; i >= 1; i--) {
            assertEquals(i, (int) deque.removeFirst());
        }

        assertEquals(0, deque.size());

        for (int i = 1; i <= 4; i++) {
            deque.addFirst(i);
        }

        assertEquals(4, deque.size());

        for (int i = 1; i <= 4; i++) {
            assertEquals(i, (int) deque.removeLast());
        }

        assertEquals(0, deque.size());

        assertThrows(DequeUnderflowException.class, deque::removeFirst);

        assertThrows(DequeOverflowException.class, () -> {
            for (int i = 0; i < 5; i++) {
                deque.addLast(i);
            }
        });
    }
}
