package ch6;

import java.util.function.Consumer;

public interface VisitorAction<E> extends Consumer<BinaryTree.Node<E>> {
    @Override
    default VisitorAction<E> andThen(Consumer<? super BinaryTree.Node<E>> after) {
        return Consumer.super.andThen(after)::accept;
    }
}
