package dataStructures;

/**
 * SepChain Hash Table
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class SepChainHashTable<K,V> extends HashTable<K,V> {

    //Load factors
    static final float IDEAL_LOAD_FACTOR =0.75f;
    static final float MAX_LOAD_FACTOR =0.9f;

    // The array of Map with singly linked list.
    private Map<K,V>[] table;
    private int capacity;
    public SepChainHashTable( ){
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public SepChainHashTable(int capacity){
        super(capacity);
        this.capacity= HashTable.nextPrime((int) (capacity / IDEAL_LOAD_FACTOR));
        table = (Map<K, V>[]) new Map[this.capacity];
        for(int i=0;i<this.capacity;i++){
            table[i]= new MapSinglyList<>();
        }
        this.maxSize=(int)(this.capacity*MAX_LOAD_FACTOR);
    }

    // Returns the hash value of the specified key.
    protected int hash( K key ){
        return Math.abs( key.hashCode() ) % table.length;
    }
    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     *
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
    public V get(K key) {
        return table[hash(key)].get(key);
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
        if (isFull()) rehash();
        int index = hash(key);
        V oldValue = table[index].put(key, value);
        if (oldValue == null){
            currentSize++;
        }
        return oldValue;
    }


    private void rehash() {
        this.capacity = HashTable.nextPrime(this.capacity * 2);
        Map<K, V>[] oldTable = table;
        @SuppressWarnings("unchecked")
        Map<K, V>[] newTable = (Map<K, V>[]) new Map[this.capacity];
        for (int i = 0; i < this.capacity; i++) {
            newTable[i] = new MapSinglyList<>();
        }
        this.maxSize=(int)(this.capacity*MAX_LOAD_FACTOR);
        this.currentSize = 0;
        this.table = newTable;
        for (Map<K, V> kvMap : oldTable) {
            Iterator<Entry<K, V>> it = kvMap.iterator();
            while (it.hasNext()) {
                Entry<K, V> entry = it.next();
                put(entry.key(), entry.value());
            }
        }
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
        int index = hash(key);
        V oldValue = table[index].remove(key);
        if (oldValue != null) {
            currentSize--;
        }
        return oldValue;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    public Iterator<Entry<K, V>> iterator() {
        return new SepChainHashTableIterator<>(table);
    }


}
