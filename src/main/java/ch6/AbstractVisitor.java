package ch6;

public abstract class AbstractVisitor<E> implements Visitor<E> {
    protected final VisitorAction<E> action;

    public AbstractVisitor(VisitorAction<E> action) {
        this.action = action;
    }
}
