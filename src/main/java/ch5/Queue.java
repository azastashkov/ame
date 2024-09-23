package ch5;

public interface Queue<E extends Comparable<E>> {
    void enqueue(E element);
    E dequeue();
    int size();
    default boolean isEmpty() {
        return size() == 0;
    }
}
