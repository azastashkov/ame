package ch6;

public interface Deque<E> {
    void addFirst(E element);
    void addLast(E element);
    E removeFirst();
    E removeLast();
    int size();
    default boolean isEmpty() {
        return size() == 0;
    }
}
