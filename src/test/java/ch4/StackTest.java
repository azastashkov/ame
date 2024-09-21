package ch4;

import org.junit.Test;

import java.util.EmptyStackException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class StackTest {
    @Test
    public void testLinkedListBasedStack() {
        testStackAdt(new LinkedListBasedStack<>(4));
    }

    @Test
    public void testFixedSizeArrayStack() {
        testStackAdt(new FixedSizeArrayStack<>(4));
    }

    @Test
    public void testDynamicArrayStack() {
        testStackAdt(new DynamicArrayStack<>(4));
    }

    // 4.8.1
    // Use a stack to check balancing of parentheses
    @Test
    public void checkBalancingOfParentheses() {
        assertTrue(isParenthesesBalanced("(A+B)+(C-D)"));
        assertFalse(isParenthesesBalanced("((A+B)+(C-D)"));
        assertTrue(isParenthesesBalanced("((A+B)+(C-D))"));
        assertTrue(isParenthesesBalanced("((A+(B+C))-D)"));
    }

    private boolean isParenthesesBalanced(String expression) {
        Stack<Character> stack = new LinkedListBasedStack<>(expression.length());
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                Character top = stack.pop();
                if (top != '(') {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    private void testStackAdt(Stack<Integer> stack) {
        for (int i = 1; i <= 4; i++) {
            stack.push(i);
        }

        assertEquals(4, stack.size());

        for (int i = 4; i >= 1; i--) {
            assertEquals(i, (int) stack.pop());
        }

        assertEquals(0, stack.size());

        assertThrows(EmptyStackException.class, stack::pop);

        assertThrows(StackOverflowException.class, () -> {
            for (int i = 0; i < 5; i++) {
                stack.push(i);
            }
        });
    }
}
