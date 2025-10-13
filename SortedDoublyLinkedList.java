package dataStructures;

import dataStructures.exceptions.*;


/**
 * Sorted Doubly linked list Implementation
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 * 
 */
public class SortedDoublyLinkedList<E> implements SortedList<E> {

    /**
     *  Node at the head of the list.
     */
    private DoublyListNode<E> head;
    /**
     * Node at the tail of the list.
     */
    private DoublyListNode<E> tail;
    /**
     * Number of elements in the list.
     */
    private int currentSize;
    /**
     * Comparator of elements.
     */
    private final Comparator<E> comparator;
    /**
     * Constructor of an empty sorted double linked list.
     * head and tail are initialized as null.
     * currentSize is initialized as 0.
     */
    public SortedDoublyLinkedList(Comparator<E> comparator) {
        this.head = null;
        this.tail = null;
        this.currentSize = 0;
        this.comparator = comparator;
    }

    /**
     * Returns true iff the list contains no elements.
     * @return true if list is empty
     */
    public boolean isEmpty() {
        return currentSize==0;
    }

    /**
     * Returns the number of elements in the list.
     * @return number of elements in the list
     */

    public int size() {
        return currentSize;
    }

    /**
     * Returns an iterator of the elements in the list (in proper sequence).
     * @return Iterator of the elements in the list
     */
    public Iterator<E> iterator() {
        return new DoublyIterator<>(head);
    }

    /**
     * Returns the first element of the list.
     * @return first element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getMin( ) {
        if (isEmpty()) throw new NoSuchElementException();
        return head.getElement();
    }

    /**
     * Returns the last element of the list.
     * @return last element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getMax( ) {
        if (isEmpty()) throw new NoSuchElementException();
        return tail.getElement();
    }
    /**
     * Returns the first occurrence of the element equals to the given element in the list.
     * @return element in the list or null
     */
    public E get(E element) {
        DoublyListNode<E> current = head;
        while (current != null) {
            int compare = comparator.compare(element, current.getElement());

            if (compare == 0) return current.getElement();
            else if (compare < 0) return null;
            current = current.getNext();
        }
        return null;
        //should be re-written very bad written
    }

    /**
     * Returns true iff the element exists in the list.
     *
     * @param element to be found
     * @return true iff the element exists in the list.
     */
    public boolean contains(E element) {
        return get(element) != null;
    }

    /**
     * Inserts the specified element at the list, according to the natural order.
     * If there is an equal element, the new element is inserted after it.
     * @param element to be inserted
     */
    public void add(E element) {
        DoublyListNode<E>  newNode = new DoublyListNode<>(element);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        }else if (comparator.compare(element, head.getElement()) < 0) {
            newNode.setNext(head);
            head.setNext(newNode);
            head = newNode;
        }else if (comparator.compare(element, tail.getElement()) > 0) {
            newNode.setPrevious(tail);
            tail.setPrevious(newNode);
            tail = newNode;
        }else{
            addInMiddle(element);
        }
        currentSize++;
    }

    /**
     * Removes and returns the first occurrence of the element equals to the given element in the list.
     * @return element removed from the list or null if !belongs(element)
     */
    public E remove(E element) {
        DoublyListNode<E> newNode = findNode(element);
        if (newNode == null) throw new NoSuchElementException();
        removeNode(newNode);
        currentSize--;
        return newNode.getElement();
    }


    private DoublyListNode<E> findNode(E element) {
        DoublyListNode<E> current = head;
        while (current != null) {
            int compare = comparator.compare(element, current.getElement());
            if (compare == 0) return current;
            else if (compare < 0) break;
            current = current.getNext();
        }
        return null;
    }

    private void addInMiddle(E element) {
        DoublyListNode<E> newNode = new DoublyListNode<>(element);
        DoublyListNode<E> current = head;
        while (comparator.compare(element, current.getElement()) >= 0){
            current = current.getNext();
        }
        DoublyListNode<E> previous = current.getPrevious();
        previous.setNext(newNode);
        newNode.setPrevious(previous);
        newNode.setNext(previous);
        current.setPrevious(newNode);
    }

    private void removeNode(DoublyListNode<E> node) {
        DoublyListNode<E> previous = node.getPrevious();
        DoublyListNode<E> next = node.getNext();
        if (previous != null) {
            previous.setNext(next);
        }else{
            head = next;
        }

        if (next != null) {
            next.setPrevious(previous);
        }else{
            tail = previous;
        }
    }
}
