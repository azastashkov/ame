package ch5;

import ch4.LinkedListBasedStack;
import ch4.Stack;

public class TwoStacksBasedQueue<E> extends AbstractQueue<E> {
    private Stack<E> stack1;
    private Stack<E> stack2;

    public TwoStacksBasedQueue(int capacity) {
        super(capacity);
        stack1 = new LinkedListBasedStack<>(capacity);
        stack2 = new LinkedListBasedStack<>(capacity);
    }

    @Override
    protected void doEnqueue(E element) {
        stack1.push(element);
        size++;
    }

    @Override
    protected E doDequeue() {
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        E element = stack2.pop();
        size--;
        return element;
    }
}
