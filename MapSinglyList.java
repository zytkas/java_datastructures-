package dataStructures;
/**
 * Map with a singly linked list with head and size
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
class MapSinglyList<K,V> implements Map<K, V> {


    private SinglyListNode<Entry<K,V>> head;

    private int size;

    public MapSinglyList() {
        this.size = 0;
        this.head = null;
    }

    /**
     * Returns true iff the dictionary contains no entries.
     *
     * @return true if dictionary is empty
     */
  
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of entries in the dictionary.
     *
     * @return number of elements in the dictionary
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     *
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V get(K key) {
        SinglyListNode<Entry<K,V>> newNode = findKey(key);
        return (newNode == null) ? null : newNode.getElement().value();
    }
    

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * replaces its value by the specified value and returns the old value;
     * otherwise, inserts the entry (key, value) and returns null.
     *
     * @param key   with which the specified value is to be associated
     * @param value to be associated with the specified key
     * @return previous value associated with key,
     * or null if the dictionary does not have an entry with that key
     */
    
    public V put(K key, V value) {
        SinglyListNode<Entry<K,V>> newNode = findKey(key);
        V oldValue = null;
        if(newNode == null){
            newNode = new SinglyListNode<>(new Entry<>(key,value), head);
            head = newNode;
            size++;
        }else{
            oldValue = newNode.getElement().value();
            newNode.setElement(new Entry<>(key,value));
        }
        return oldValue;
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * removes it from the dictionary and returns its value;
     * otherwise, returns null.
     *
     * @param key whose entry is to be removed from the map
     * @return previous value associated with key,
     * or null if the dictionary does not an entry with that key
     */
    public V remove(K key) {
        if(isEmpty()) return null;
        if(head.getElement().key().equals(key)){
            V oldValue = head.getElement().value();
            head = head.getNext();
            size--;
            return oldValue;
        }
        SinglyListNode<Entry<K,V>> prevNode = findPrevNode(key);
        if(prevNode == null) return null;
        SinglyListNode<Entry<K,V>> curNode = prevNode.getNext();
        V oldValue = curNode.getElement().value();
        prevNode.setNext(curNode.getNext());
        size--;
        return oldValue;
    }

    private SinglyListNode<Entry<K,V>> findPrevNode(K key){
        SinglyListNode<Entry<K,V>> prevNode = head;
        while (prevNode.getNext() != null){
            if(prevNode.getNext().getElement().key().equals(key)){
                return prevNode;
            }
            prevNode = prevNode.getNext();
        }
        return null;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    public Iterator<Entry<K, V>> iterator() {
        return new SinglyIterator<>(head);
    }

    /**
     * Returns an iterator of the values in the dictionary.
     *
     * @return iterator of the values in the dictionary
     */
@SuppressWarnings({"unchecked","rawtypes"})
    public Iterator<V> values() {
        return new ValuesIterator(iterator());
    }

    /**
     * Returns an iterator of the keys in the dictionary.
     *
     * @return iterator of the keys in the dictionary
     */
@SuppressWarnings({"unchecked","rawtypes"})
    public Iterator<K> keys() {
        return new KeysIterator(iterator());
    }

    private SinglyListNode<Entry<K,V>> findKey(K key){
        SinglyListNode<Entry<K,V>> newNode = head;
        while (newNode != null){
            if(newNode.getElement().key().equals(key)){
                return newNode;
            }
            newNode = newNode.getNext();
        }
        return null;
    }
}
