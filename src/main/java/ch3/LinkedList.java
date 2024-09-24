package ch3;

public class LinkedList<E extends Comparable<E>> {
    private Node<E> head;
    private int size;

    public void addFirst(E element) {
        head = new Node<>(element, head);
        size++;
    }

    public E removeFirst() {
        if (head != null) {
            Node<E> formerHead = head;
            head = head.next;
            formerHead.next = null;
            size--;
            return formerHead.item;
        }
        return null;
    }

    public int size() {
        return size;
    }

    public void add(E element) {
        Node<E> tail = new Node<>(element);
        if (head == null) {
            head = tail;
            size++;
            return;
        }

        Node<E> previous = head, next = head.next;
        while (next != null) {
            previous = next;
            next = next.next;
        }

        previous.next = tail;
        size++;
    }

    public E get(int index) {
        checkBounds(index);

        return getNode(index).item;
    }

    public E lastOf(int index) {
        checkBounds(index);

        int lastOfPosition = size - index - 1;
        return getNode(lastOfPosition).item;
    }

    public void insert(E element, int index) {
        checkBounds(index);

        Node<E> previous = head;
        Node<E> next = head.next;
        int i = 0;
        while (next != null) {
            if (++i == index) {
                break;
            }

            previous = next;
            next = next.next;
        }

        previous.next = new Node<>(element, next);
        size++;
    }

    public void addAndLink(E element, int nextIndex) {
        checkBounds(nextIndex);

        Node<E> next = getNode(nextIndex);
        Node<E> tail = getNode(size - 1);
        tail.next = new Node<>(element, next);
    }

    public boolean hasCycle() {
        Node<E> slow = head;
        Node<E> fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }

        return false;
    }

    public int indexOfCycleStart() {
        Node<E> slow = head;
        Node<E> fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                slow = head;
                break;
            }
        }

        int index = 0;
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
            index++;
            if (slow == fast) {
                return index;
            }
        }

        return -1;
    }

    public boolean isPalindrome() {
        Node<E> node = getNode(size / 2);
        Node<E> copy = new Node<>(node.item);
        Node<E> copyHead = copy;
        while (node.next != null) {
            copy.next = new Node<>(node.next.item);
            node = node.next;
            copy = copy.next;
        }

        copy = reverse(copyHead);

        node = head;
        while (copy.next != null) {
            if (!copy.item.equals(node.item)) {
                return false;
            }
            copy = copy.next;
            node = node.next;
        }

        return true;
    }

    public void reverseInBlocks(int k) {
        if (k >= size) {
            return;
        }

        Node<E> current = head, prevTail = null, prevCurrent = head;
        while (current != null) {
            int count = k;
            Node<E> tail = null;
            while (current != null && count > 0) {
                Node<E> next = current.next;
                current.next = tail;
                tail = current;
                current = next;
                count--;
            }

            if (prevTail != null) {
                prevTail.next = tail;
            } else {
                head = tail;
            }

            prevTail = prevCurrent;
            prevCurrent = current;
        }
    }

    public void rotate(int k) {
        if (size == k) {
            return;
        }

        int newHeadIndex = size - k - 1;
        Node<E> current = head;
        int i = 0;
        while (current != null && i++ < newHeadIndex) {
            current = current.next;
        }

        if (current == null) {
            return;
        }

        Node<E> newHead = current.next;
        current.next = null;

        current = newHead;
        while (current.next != null) {
            current = current.next;
        }

        current.next = head;
        head = newHead;
    }

    public void partition(E pivotItem) {
        Node<E> first = null, second = null, newHead = null, pivot = null;
        Node<E> current = head;
        while (current != null) {
            if (current.item.compareTo(pivotItem) < 0) {
                if (first == null) {
                    first = new Node<>(current.item);
                    newHead = first;
                } else {
                    first.next = new Node<>(current.item);
                    first = first.next;
                }
            } else {
                if (second == null) {
                    second = new Node<>(current.item);
                    pivot = second;
                } else {
                    second.next = new Node<>(current.item);
                    second = second.next;
                }
            }
            current = current.next;
        }

        if (newHead != null) {
            first.next = pivot;
            head = newHead;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (head != null) {
            Node<E> node = head;
            while (node != null) {
                sb.append(node.item).append(", ");
                node = node.next;
            }
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append("]");
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        LinkedList<E> other = (LinkedList<E>) obj;
        if (size != other.size) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            if (!get(i).equals(other.get(i))) {
                return false;
            }
        }

        return true;
    }

    public void reverse() {
        head = reverse(head);
    }

    private Node<E> reverse(Node<E> node) {
        if (node == null) {
            return null;
        }
        Node<E> current = node, previous = null;
        while (current != null) {
            Node<E> next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }
        return previous;
    }

    private Node<E> getNode(int index) {
        Node<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void checkBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    public static class Node<E> {
        public final E item;
        public Node<E> next;

        public Node(E item) {
            this.item = item;
        }

        public Node(E item, Node<E> next) {
            this.item = item;
            this.next = next;
        }
    }
}
