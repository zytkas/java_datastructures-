/**
 * SepChain Hash Table Iterator
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
package dataStructures;
import dataStructures.exceptions.NoSuchElementException;
class SepChainHashTableIterator<K,V> implements Iterator<Map.Entry<K,V>> {

    private Map<K,V>[] table;
    private int currentTable;
    private Iterator<Map.Entry<K,V>> tableIt;

    SepChainHashTableIterator(Map<K,V>[] table) {
        this.table = table;
       rewind();
    }

    /**
     * Returns true if next would return an element
     * rather than throwing an exception.
     *
     * @return true iff the iteration has more elements
     */
    public boolean hasNext() {
        if(tableIt != null && tableIt.hasNext()){
            return true;
        }
        while(currentTable + 1 < table.length){
            currentTable++;
            if(!table[currentTable].isEmpty()){
                tableIt = table[currentTable].iterator();
                return true;
            }
        }
        return false;
    }


    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public Map.Entry<K,V> next() {
        if(!hasNext()) throw new NoSuchElementException();
        return tableIt.next();
    }

    /**
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     */
    public void rewind() {
        currentTable = -1;
        tableIt = null;
        hasNext();
    }
}

