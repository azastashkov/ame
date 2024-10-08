package ch5;

import ch3.LinkedList.Node;

public class LinkedListBasedQueue<E> extends AbstractQueue<E> {
    private Node<E> head;
    private Node<E> tail;

    public LinkedListBasedQueue(int capacity) {
        super(capacity);
    }

    @Override
    protected void doEnqueue(E element) {
        if (head == null) {
            head = new Node<>(element);
            tail = head;
        } else {
            tail.next = new Node<>(element);
            tail = tail.next;
        }

        size++;
    }

    @Override
    protected E doDequeue() {
        E element = head.item;
        head = head.next;

        if (size-- == 0) {
            tail = null;
        }

        return element;
    }

    @Override
    public E peek() {
        if (head == null) {
            return null;
        }

        return head.item;
    }
}
