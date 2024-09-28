package ch6;

import ch3.LinkedList;

import java.util.Arrays;

public class BinarySearchTree<E extends Comparable<E>> extends AbstractBinaryTree<E> {
    private BinarySearchTree() {
    }

    private BinarySearchTree(Node<E> root) {
        this.root = root;
    }

    public static <E extends Comparable<E>> BinarySearchTree<E> of(E[] values) {
        BinarySearchTree<E> binarySearchTree = new BinarySearchTree<>();

        Arrays.stream(values).forEach(binarySearchTree::add);

        return binarySearchTree;
    }

    public static <E extends Comparable<E>> BinarySearchTree<E> of(LinkedList<E> list) {
        Node<E> root = construct(list, 0, list.size() - 1);
        return new BinarySearchTree<>(root);
    }

    public boolean contains(E item) {
        return find(root, item) != null;
    }

    public E min() {
        Node<E> min = findMin(root);

        if (min == null) {
            return null;
        }

        return min.item;
    }

    public E max() {
        Node<E> max = findMax(root);

        if (max == null) {
            return null;
        }

        return max.item;
    }

    public void add(E item) {
        root = insert(root, item);
    }

    public void delete(E item) {
        root = delete(root, item);
    }

    private AbstractBinaryTree.Node<E> find(AbstractBinaryTree.Node<E> root, E item) {
        if (root == null) {
            return null;
        }

        if (item.compareTo(root.item) < 0) {
            return find(root.left, item);
        } else if (item.compareTo(root.item) > 0) {
            return find(root.right, item);
        }

        return root;
    }

    private AbstractBinaryTree.Node<E> findMin(AbstractBinaryTree.Node<E> root) {
        if (root == null) {
            return null;
        }

        if (root.left == null) {
            return root;
        }

        return findMin(root.left);
    }

    private AbstractBinaryTree.Node<E> findMax(AbstractBinaryTree.Node<E> root) {
        if (root == null) {
            return null;
        }

        if (root.right == null) {
            return root;
        }

        return findMax(root.right);
    }

    private AbstractBinaryTree.Node<E> insert(AbstractBinaryTree.Node<E> root, E item) {
        if (root == null) {
            root = new Node<>(item);
        } else {
            if (item.compareTo(root.item) < 0) {
                root.left = insert(root.left, item);
            } else if (item.compareTo(root.item) > 0) {
                root.right = insert(root.right, item);
            }
        }
        return root;
    }

    private AbstractBinaryTree.Node<E> delete(AbstractBinaryTree.Node<E> root, E item) {
        if (root == null) {
            return null;
        }

        if (item.compareTo(root.item) < 0) {
            root.left = delete(root.left, item);
        } else if (item.compareTo(root.item) > 0) {
            root.right = delete(root.right, item);
        } else {
            if (root.left != null && root.right != null) {
                root.item = findMax(root.left).item;
                root.left = delete(root.left, root.item);
            } else {
                if (root.left == null) {
                    root = root.right;
                }

                if (root.right == null) {
                    root = root.left;
                }
            }
        }

        return root;
    }

    private static <E extends Comparable<E>> Node<E> construct(LinkedList<E> list, int start, int end) {
        if (start > end) {
            return null;
        }

        int mid = (start + end) / 2;

        Node<E> left = construct(list, start, mid - 1);
        Node<E> root = new Node<>(list.get(mid));
        root.left = left;
        root.right = construct(list, mid + 1, end);

        return root;
    }
}
