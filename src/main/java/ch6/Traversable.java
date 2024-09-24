package ch6;

public interface Traversable<E> {
    void traverse(Visitor<E> visitor);
}
