import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Randomized queue. A randomized queue is similar to a stack or queue, except that the item removed is chosen uniformly at random among items in the data structure.
 *
 * @Author Jonathan
 * @Date 2019/12/30
 **/
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int size;

    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        items =(Item[]) new Object[2];
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
        for (int i = 1; i <= 100; i++) {
            randomizedQueue.enqueue(i);
        }
        for (int i = 0; i < 10000; i++) {
            StdOut.println(randomizedQueue.sample());
        }


    }

    /**
     * is the randomized queue empty?
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * return the number of items on the randomized queue
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * add the item
     *
     * @param item
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        stretch();
        items[size++] = item;
    }

    /**
     * remove and return a random item
     *
     * @return
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randomIndex = randomIndex();
        Item item = items[randomIndex];
        items[randomIndex] = items[size - 1];
        items[size - 1] = null;
        size--;
        shrink();
        return item;
    }

    private int randomIndex() {
        return StdRandom.uniform(0, size);
    }

    /**
     * return a random item (but do not remove it)
     *
     * @return
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return items[randomIndex()];
    }

    /**
     * auto stretch array size by doubling it.
     */
    private void stretch() {
        if (size == items.length) {
            // double array length
            items = Arrays.copyOf(items, 2 * items.length);
        }
    }

    /**
     * shrinking array one half time
     */
    private void shrink() {
        if (size > 0 && size == items.length / 4) {
            items = Arrays.copyOf(items, items.length / 2);
        }
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomAccessIterator();
    }

    private class RandomAccessIterator implements Iterator<Item> {

        private final Item[] raItems;
        private int i;

        public RandomAccessIterator() {
            raItems = Arrays.copyOf(items, size);
            StdRandom.shuffle(raItems);
        }


        @Override
        public boolean hasNext() {
            return i < size;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return raItems[i++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
