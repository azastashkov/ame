package ch4;

public interface Stack<E extends Comparable<E>> {
    void push(E value);
    E pop();
    E peek();
    int size();
    default boolean isEmpty() {
        return size() == 0;
    }
}
