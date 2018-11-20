/* *****************************************************************************
 *  Name: sunzg
 *  Date: 2018-11-20
 *  Description: randomized queue impl
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int n;
    private Item[] arr;
    private int first;
    private int last;

    public RandomizedQueue() {
        // construct an empty randomized queue
        arr = (Item[]) new Object[2];
        first = 0;
        last = 0;
    }

    public boolean isEmpty() {
        // is the randomized queue empty?
        return n == 0;
    }

    public int size() {
        // return the number of items on the randomized queue
        return n;
    }

    // resize the underlying array
    private void resize(int capacity) {
        assert capacity >= n;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = arr[(first + i) % arr.length];
        }
        arr = temp;
        first = 0;
        last = n;
    }

    public void enqueue(Item item) {
        // add the item
        if (item == null) {
            throw new IllegalArgumentException("null parameter");
        }
        if (n == arr.length) resize(2 * arr.length);   // double size of array if necessary
        arr[last++] = item;                        // add item
        if (last == arr.length) last = 0;          // wrap-around
        n++;
    }

    public Item dequeue() {
        // remove and return a random item
        if (isEmpty()) throw new NoSuchElementException("invalid operation");
        Item item = arr[first];
        arr[first] = null;                            // to avoid loitering
        n--;
        first++;
        if (first == arr.length) first = 0;           // wrap-around
        // shrink size of array if necessary
        if (n > 0 && n == arr.length / 4) resize(arr.length / 2);
        return item;
    }

    public Item sample() {
        // return a random item (but do not remove it)
        if (isEmpty()) throw new NoSuchElementException("invalid operation");
        int i = StdRandom.uniform(size());
        return arr[(i + first) % arr.length];
    }

    public Iterator<Item> iterator() {
        // return an independent iterator over items in random order
        return new ArrayIterator(size());
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ArrayIterator implements Iterator<Item> {
        private int[] order_arr;
        private int i = 0;

        public ArrayIterator(int n) {
            order_arr = new int[n];
            for (int index = 0; index < n; index++) {
                order_arr[index] = index;
            }
            StdRandom.shuffle(order_arr);
        }

        public boolean hasNext() {
            return i < order_arr.length;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = arr[(order_arr[i] + first) % arr.length];
            i++;
            return item;
        }
    }

    public static void main(String[] args) {
        // unit testing (optional)
    }
}
