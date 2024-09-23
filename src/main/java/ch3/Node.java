package ch3;

public class Node <E> {
    public final E element;
    public Node<E> next;

    public Node(E element) {
        this.element = element;
    }

    public Node(E element, Node<E> next) {
        this.element = element;
        this.next = next;
    }
}
