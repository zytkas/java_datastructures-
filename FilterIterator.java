package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Iterator Abstract Data Type with Filter
 * Includes description of general methods for one way iterator.
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
public class FilterIterator<E> implements Iterator<E> {

    Iterator<E> iterator;

    Predicate<E> predicate;

    E next;

    /**
     *
     * @param list to be iterated
     * @param criterion filter
     */
    public FilterIterator(Iterator<E> list, Predicate<E> criterion) {
        this.iterator = list;
        this.predicate = criterion;
        this.next = null;
        advanced();
    }

    /**
     * Returns true if next would return an element
     *
     * @return true iff the iteration has more elements
     */
    public boolean hasNext() {
        return next != null;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public E next() {
        if (!hasNext()) throw new NoSuchElementException();
        E element = next;
        advanced();
        return element;
    }

    /**
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     */
    public void rewind() {
       iterator.rewind();
       advanced();
    }

    private void advanced() {
        next = null;
        while (iterator.hasNext()) {
            E it = iterator.next();
            if (predicate.check(it)){
                next = it;
                return;
            }
        }
    }
}
