package dataStructures;
/**
 * Closed Hash Table
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class ClosedHashTable<K,V> extends HashTable<K,V> {

    //Load factors
    static final float IDEAL_LOAD_FACTOR =0.5f;
    static final float MAX_LOAD_FACTOR =0.8f;
    static final int NOT_FOUND=-1;

    // removed cell
    static final Entry<?,?> REMOVED_CELL = new Entry<>(null,null);

    // The array of entries.
    private Entry<K,V>[] table;

    /**
     * Constructors
     */

    public ClosedHashTable( ){
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ClosedHashTable( int capacity ){
        super(capacity);
        int arraySize = HashTable.nextPrime((int) (capacity / IDEAL_LOAD_FACTOR));
        // Compiler gives a warning.
        table = (Entry<K,V>[]) new Entry[arraySize];
        for ( int i = 0; i < arraySize; i++ )
            table[i] = null;
        maxSize = (int)(arraySize * MAX_LOAD_FACTOR);
    }

    //Methods for handling collisions.
    // Returns the hash value of the specified key.
    int hash( K key, int i ){
        return Math.abs( key.hashCode() + i) % table.length;
    }
    /**
     * Linear Proving
     * @param key to search
     * @return the index of the table, where is the entry with the specified key, or null
     */
    int searchLinearProving(K key) {
        for (int i = 0; i < table.length; i++) {
            int index = hash(key, i);
            if (table[index] == null) {
                return NOT_FOUND;
            }else if(table[index] != REMOVED_CELL){
                K keyInPos = table[index].key();
                if (keyInPos.equals(key)) {
                    return index;
                }
            }
        }
        return NOT_FOUND;
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
            int index = searchLinearProving(key);
            if (index == NOT_FOUND) return null;
            return table[index].value();
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
    @Override
    public V put(K key, V value) {
        if (isFull())
            rehash();
        int index = searchLinearProving(key);
        if (index != NOT_FOUND) {
            V oldValue = table[index].value();
            table[index] = new Entry<>(key, value);
            return oldValue;
        }
        for ( int i = 0; i < table.length; i++ ) {
            int findIndex = hash(key, i);
            if (table[findIndex] == null || table[findIndex] ==  REMOVED_CELL) {
                table[findIndex] = new Entry<>(key, value);
                currentSize++;
                return null;
            }
        }
        return null;
    }

     @SuppressWarnings("unchecked")
     private void rehash(){
        int cap = HashTable.nextPrime((table.length * 2));
        Entry<K,V>[] oldTable = table;
        table = (Entry<K,V>[]) new Entry[cap];
        this.maxSize = (int)(cap * MAX_LOAD_FACTOR);
        this.currentSize = 0;
         for (Entry<K, V> oldEntry : oldTable) {
             if (oldEntry != null && oldEntry != REMOVED_CELL) {
                 put(oldEntry.key(), oldEntry.value());
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
    @SuppressWarnings("unchecked")
    @Override
    public V remove(K key) {
        int index =  searchLinearProving(key);
        if (index == NOT_FOUND) return null;
        V oldValue = table[index].value();
        table[index] = (Entry <K,V>) REMOVED_CELL;
        currentSize--;
        return oldValue;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new FilterIterator<>(
                new ArrayIterator<>(table, table.length),
                m -> m != null && m != REMOVED_CELL
        );
    }

}
