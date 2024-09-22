package ch4;

import ch3.LinkedList;
import org.junit.Test;

import java.util.EmptyStackException;

import static org.junit.Assert.assertArrayEquals;
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

    // 4.8.2
    // Convert an infix string to a postfix one
    @Test
    public void convertInfixToPostfix() {
        assertEquals("234*+", infixToPostfix("2+3*4"));
        assertEquals("ABC*+D+", infixToPostfix("A+B*C+D"));
        assertEquals("AB*CD+-E+", infixToPostfix("A*B-(C+D)+E"));
    }

    // 4.8.4
    // Evaluate a postfix expression
    @Test
    public void evaluatePostfixExpression() {
        assertEquals(14, evaluatePostfix("234*+"));
        assertEquals(2, evaluatePostfix("123*+5-"));
    }

    // 4.8.5
    // Evaluate an infix expression
    @Test
    public void evaluateInfixExpression() {
        assertEquals(14, evaluateInfix("2+3*4"));
        assertEquals(2, evaluateInfix("1+2*3-5"));
    }

    // 4.8.9
    // Check whether elements of a linked list formed from a string's characters is a palindrome
    @Test
    public void checkWhetherLinkedListFormedFromStringCharactersIsPalindrome() {
        assertTrue(isPalindrome("tenet"));
        assertTrue(isPalindrome("hannah"));
        assertTrue(isPalindrome("nun"));
        assertTrue(isPalindrome("noon"));
    }

    // 4.8.11
    // Given a stack, reverse its contents using only stack operations (push and pop)
    @Test
    public void reverseStackUsingOnlyPushAndPop() {
        int capacity = 5;
        Stack<Integer> stack = new LinkedListBasedStack<>(capacity);
        for (int i = 0; i < capacity; i++) {
            stack.push(i);
        }

        reverse(stack);

        for (int i = 0; i < capacity; i++) {
            assertEquals(i, (int) stack.pop());
        }
    }

    // 4.8.23
    // For a given array A, the span S[i] of A[i] is the maximum number of consecutive elements A[j]
    // immediately preceding A[i] and such that A[j] <= A[j+1]
    @Test
    public void findSpans() {
        int[] array = { 6, 3, 4, 5, 2 };

        int[] spans = findSpans(array);

        assertArrayEquals(new int[] { 1, 1, 2, 3, 1}, spans);
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

    private int precedence(char c) {
        switch (c) {
            case '*':
            case '/':
                return 2;

            case '+':
            case '-':
                return 1;

            default:
                return 0;
        }
    }

    private String infixToPostfix(String infix) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new LinkedListBasedStack<>(infix.length());

        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);

            if (Character.isAlphabetic(c) || Character.isDigit(c)) {
                postfix.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop());
                }
                stack.pop(); // '('
            } else { // operator
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                    postfix.append(stack.pop());
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            postfix.append(stack.pop());
        }

        return postfix.toString();
    }

    private int evaluate(char operator, Integer operand1, Integer operand2) {
        switch (operator) {
            case '+':
                return operand1 + operand2;

            case '-':
                return operand2 - operand1;

            case '*':
                return operand1 * operand2;

            case '/':
                return operand2 / operand1;
        }
        throw new IllegalArgumentException("Invalid operator: " + operator);
    }

    private int evaluatePostfix(String postfix) {
        Stack<Integer> stack = new LinkedListBasedStack<>(postfix.length());
        for (int i = 0; i < postfix.length(); i++) {
            char c = postfix.charAt(i);
            if (Character.isDigit(c)) {
                stack.push(Integer.parseInt(Character.toString(c)));
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                stack.push(evaluate(c, stack.pop(), stack.pop()));
            }
        }
        return stack.pop();
    }

    private int evaluateInfix(String infix) {
        Stack<Integer> operands = new LinkedListBasedStack<>(infix.length());
        Stack<Character> operators = new LinkedListBasedStack<>(infix.length());
        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);
            if (Character.isDigit(c)) {
                operands.push(Integer.parseInt(Character.toString(c)));
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    operands.push(evaluate(operators.pop(), operands.pop(), operands.pop()));
                }
                operators.pop();
            } else {
                while (!operators.isEmpty() && precedence(c) <= precedence(operators.peek())) {
                    operands.push(evaluate(operators.pop(), operands.pop(), operands.pop()));
                }
                operators.push(c);
            }
        }

        while (!operators.isEmpty()) {
            operands.push(evaluate(operators.pop(), operands.pop(), operands.pop()));
        }

        return operands.pop();
    }

    private boolean isPalindrome(String s) {
        LinkedList<Character> list = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            list.add(s.charAt(i));
        }

        int remainder = s.length() % 2;
        int mid = s.length() / 2 + remainder;
        Stack<Character> stack = new LinkedListBasedStack<>(mid);
        for (int i = 0; i < mid - remainder; i++) {
            stack.push(s.charAt(i));
        }

        for (int i = mid; i < s.length(); i++) {
            if (list.get(i) != stack.pop()) {
                return false;
            }
        }

        return true;
    }

    private <T extends Comparable<T>> void reverse(Stack<T> stack) {
        if (stack.isEmpty()) {
            return;
        }

        T tmp = stack.pop();
        reverse(stack);
        insertAtBottom(stack, tmp);
    }

    private <T extends Comparable<T>> void  insertAtBottom(Stack<T> stack, T data) {
        if (stack.isEmpty()) {
            stack.push(data);
            return;
        }

        T tmp = stack.pop();
        insertAtBottom(stack, data);
        stack.push(tmp);
    }

    private int[] findSpans(int[] array) {
        Stack<Integer> stack = new LinkedListBasedStack<>(array.length);
        int[] spans = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            while (!stack.isEmpty() && array[stack.peek()] <= array[i]) {
                stack.pop();
            }

            int p = -1;
            if (!stack.isEmpty()) {
                p = stack.peek();
            }

            spans[i] = i - p;
            stack.push(i);
        }

        return spans;
    }
}
