/* *****************************************************************************
 *  Name: sunzg
 *  Date: 2018-11-20
 *  Description: queue with list impl
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int n;
    private Node first;
    private Node last;

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }


    public Deque() {
        // construct an empty deque; link list;
        first = null;
        last = null;
        n = 0;
    }

    public boolean isEmpty() {
        // is the deque empty?
        return first == null;
    }

    public int size() {
        // return the number of items on the deque
        return n;
    }

    public void addFirst(Item item) {
        // add the item to the front
        if (item == null) {
            throw new IllegalArgumentException("null parameter");
        }
        Node t = first;
        first = new Node();
        first.item = item;
        first.next = t;
        first.prev = null;
        if (isEmpty()) {
            last = t;
        }
        else {
            t.prev = first;
        }
        n++;
    }

    public void addLast(Item item) {
        // add the item to the end
        if (item == null) {
            throw new IllegalArgumentException("null parameter");
        }
        Node t = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = t;
        t.next = last;
        if (n == 0) {
            first = t;
        }
        n++;
    }

    public Item removeFirst() {
        // remove and return the item from the front
        if (isEmpty()) throw new NoSuchElementException("invalid operation");
        Item item = first.item;
        first = first.next;
        first.prev = null;
        n--;
        if (first == null) {
            last = null;
        }
        return item;
    }

    public Item removeLast() {
        // remove and return the item from the end
        if (isEmpty()) throw new NoSuchElementException("invalid operation");
        Item item = last.item;
        last = last.prev;
        last.next = null;
        n--;
        if (last == null) {
            first = null;
        }
        return item;
    }

    public Iterator<Item> iterator() {
        // return an iterator over items in order from front to end
        return new ListIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        // unit testing (optional)
    }
}
