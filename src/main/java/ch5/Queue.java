package ch5;

public interface Queue<E> {
    void enqueue(E element);
    E dequeue();
    int size();
    default boolean isEmpty() {
        return size() == 0;
    }
}
