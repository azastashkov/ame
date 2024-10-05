package ch7;

public class IndexedValue<E extends Comparable<E>> implements Comparable<IndexedValue<E>> {
    private final E value;
    private final int index;

    public IndexedValue(E value, int index) {
        this.value = value;
        this.index = index;
    }

    public E getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public int compareTo(IndexedValue<E> iv) {
        return value.compareTo(iv.value);
    }
}
