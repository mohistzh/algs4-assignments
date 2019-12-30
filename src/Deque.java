import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Dequeue.
 * A double-ended queue or deque (pronounced “deck”) is a generalization of a stack and a queue that supports
 * adding and removing items from either the front or the back of the data structure.
 * <p>
 * Performance requirements.
 * Your deque implementation must support each deque operation (including construction) in constant worst-case time.
 * A deque containing n items must use at most 48n + 192 bytes of memory. Additionally, your iterator implementation must support each operation (including construction)
 * in constant worst-case time.
 *
 * @Author Jonathan
 * @Date 2019/12/30
 **/
public class Deque<Item> implements Iterable<Item> {

    private Node<Item> head, tail;
    private int size;

    /**
     * construct an empty deque
     */
    public Deque() {
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        deque.addFirst("C");
        deque.addFirst("B");
        deque.addFirst("A");
        deque.addLast("D");
        deque.addLast("E");
        StdOut.print("Origin items: ");
        Iterator<String> iterator1 = deque.iterator();
        while (iterator1.hasNext()) {
            StdOut.print(iterator1.next() + " ");
        }
        StdOut.print("\nRemoved the first item: ");
        deque.removeFirst();
        Iterator<String> iterator2 = deque.iterator();
        while (iterator2.hasNext()) {
            StdOut.print(iterator2.next() + " ");
        }

        StdOut.print("\nRemoved the last item: ");
        deque.removeLast();
        Iterator<String> iterator3 = deque.iterator();
        while (iterator3.hasNext()) {
            StdOut.print(iterator3.next() + " ");
        }

    }

    /**
     * is the deque empty?
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * return the number of items on the deque
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * add the item to the front
     *
     * @param item
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node<Item> currentHead = head;
        Node<Item> newNode = new Node<>(null, item, currentHead);
        head = newNode;

        if (currentHead == null) {
            tail = newNode;
        } else {
            currentHead.prev = newNode;
        }
        size++;
    }

    /**
     * add the item to the back
     *
     * @param item
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node<Item> currentTail = tail;
        Node<Item> newNode = new Node<>(currentTail, item, null);
        tail = newNode;

        if (currentTail == null) {
            head = newNode;
        } else {
            currentTail.next = newNode;
        }
        size++;
    }

    /**
     * remove and return the item from the front
     *
     * @return
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        final Node<Item> currentHead = head;
        final Item element = currentHead.val;
        final Node<Item> next = currentHead.next;
        currentHead.val = null;
        currentHead.next = null;

        head = next;
        if (next == null) {
            tail = null;
        } else {
            next.prev = null;
        }
        size--;
        return element;
    }

    /**
     * remove and return the item from the back
     *
     * @return
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        final Node<Item> currentTail = tail;
        final Item element = currentTail.val;
        final Node<Item> prev = currentTail.prev;
        currentTail.val = null;
        currentTail.prev = null;
        tail = prev;
        if (prev == null) {
            head = null;
        } else {
            prev.next = null;
        }
        size--;
        return element;
    }

    @Override
    public Iterator<Item> iterator() {
        return new NaturalOrderIterator();
    }

    private class Node<Item> {
        private Node<Item> next;
        private Node<Item> prev;
        private Item val;

        Node(Node<Item> prev, Item val, Node<Item> next) {
            this.next = next;
            this.prev = prev;
            this.val = val;
        }
    }

    private class NaturalOrderIterator implements Iterator<Item> {
        private Node<Item> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.val;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
