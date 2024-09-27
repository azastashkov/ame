package ch6;

public class LinkedListDeque<E> extends AbstractDeque<E> {
    private Node<E> head;
    private Node<E> tail;

    public LinkedListDeque(int capacity) {
        super(capacity);
    }

    @Override
    protected void doAddFirst(E element) {
        if (head == null) {
            head = new Node<>(element);
            tail = head;
        } else {
            Node<E> newHead = new Node<>(element);
            newHead.next = head;
            head.previous = newHead;
            head = newHead;
        }

        size++;
    }

    @Override
    protected void doAddLast(E element) {
        if (head == null) {
            head = new Node<>(element);
            tail = head;
        } else {
            tail.next = new Node<>(element);
            tail.previous = tail;
            tail = tail.next;
        }

        size++;
    }

    @Override
    protected E doRemoveFirst() {
        E element = head.item;
        head = head.next;

        if (head != null) {
            head.previous = null;
        } else {
            tail = null;
        }

        size--;

        return element;
    }

    @Override
    protected E doRemoveLast() {
        E element = tail.item;
        tail = tail.previous;

        if (tail != null) {
            tail.next = null;
        } else {
            head = null;
        }

        size--;

        return element;
    }

    public static class Node<E> {
        public final E item;
        public Node<E> next;
        public Node<E> previous;

        public Node(E item) {
            this.item = item;
        }
    }
}
