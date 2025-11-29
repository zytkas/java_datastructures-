package dataStructures;

import dataStructures.exceptions.EmptyMapException;
/**
 * Binary Search Tree Sorted Map
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class BSTSortedMap<K extends Comparable<K>,V> extends BTree<Map.Entry<K,V>> implements SortedMap<K,V>{

    /**
     * Constructor
     */
    public BSTSortedMap(){
        super();
    }
    /**
     * Returns the entry with the smallest key in the dictionary.
     *
     * @return
     * @throws EmptyMapException
     */
    @Override
    public Entry<K, V> minEntry() {
        if (isEmpty())
            throw new EmptyMapException();
        return furtherLeftElement().getElement();
    }

    /**
     * Returns the entry with the largest key in the dictionary.
     *
     * @return
     * @throws EmptyMapException
     */
    @Override
    public Entry<K, V> maxEntry() {
        if (isEmpty())
            throw new EmptyMapException();
        return furtherRightElement().getElement();
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
        Node<Entry<K,V>> node=getNode((BTNode<Entry<K,V>>)root,key);
        if (node!=null)
            return node.getElement().value();
        return null;
    }

    protected BTNode<Entry<K,V>> getNode(BTNode<Entry<K,V>> node, K key) {
        if (node == null) return null;
        int comp = key.compareTo(node.getElement().key());
        if (comp == 0) return node;
        else if(comp < 0) {
            return getNode((BTNode<Entry<K,V>>) node.getLeftChild(), key);
        }else{
            return getNode((BTNode<Entry<K,V>>) node.getRightChild(), key);
        }
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
        if (isEmpty()) {
            root = new BTNode<>(new Entry<>(key, value));
            currentSize++;
            return null;
        }

        BTNode<Entry<K,V>> node = (BTNode<Entry<K,V>>)root;
        while (true) {
            int comp = key.compareTo(node.getElement().key());
            if (comp == 0) {
                V oldValue = node.getElement().value();
                node.setElement(new Entry<>(key, oldValue));
                return oldValue;
            }else if (comp < 0) {
                if (node.getLeftChild() == null) {
                    BTNode<Entry<K,V>> newNode = new BTNode<>(new Entry<>(key, value), node);
                    node.setLeftChild(newNode);
                    currentSize++;
                    return null;
                }
                node = (BTNode<Entry<K,V>>) node.getLeftChild();
            }else {
                if (node.getRightChild() == null) {
                    BTNode<Entry<K,V>> newNode = new BTNode<>(new Entry<>(key, value), node);
                    node.setRightChild(newNode);
                    currentSize++;
                    return null;
                }
                node = (BTNode<Entry<K,V>>) node.getRightChild();
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
    @Override
    public V remove(K key) {
        BTNode<Entry<K, V>> node = getNode((BTNode<Entry<K, V>>) root, key);
        if (node == null) {
            return null;
        }

        V oldValue = node.getElement().value();
        removeNode(node);
        currentSize--;
        return oldValue;
    }

    protected BTNode<Entry<K, V>> removeNode(BTNode<Entry<K, V>> node) {
        BTNode<Entry<K,V>> parent = (BTNode<Entry<K,V>>) node.getParent();
        //case of leaf
        if (node.isLeaf()) {
            if (parent == null) {
                root = null;
            }else if (node ==  parent.getRightChild()) {
                parent.setRightChild(null);
            }else {
                parent.setLeftChild(null);
            }
            return parent;
        }
        //case of 1 child
        if (node.getLeftChild() == null || node.getRightChild() == null) {
            BTNode<Entry<K, V>> child = (BTNode<Entry<K, V>>)
                    (node.getLeftChild() != null ? node.getLeftChild() : node.getRightChild());

            if (parent == null) {
                root = child;
                child.setParent(null);
            } else if (node == parent.getLeftChild()) {
                parent.setLeftChild(child);
                child.setParent(parent);
            } else {
                parent.setRightChild(child);
                child.setParent(parent);
            }
            return parent;
        }

        //last case when 2 nodes, I guess
        BTNode<Entry<K, V>> successor = ((BTNode<Entry<K, V>>) node.getRightChild()).furtherLeftElement();
        node.setElement(successor.getElement());
        return removeNode(successor);
    }
    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new InOrderIterator<>((BTNode<Entry<K,V>>) root);
    }

    /**
     * Returns an iterator of the values in the dictionary.
     *
     * @return iterator of the values in the dictionary
     */
    @Override
@SuppressWarnings({"unchecked","rawtypes"})
    public Iterator<V> values() {
        return new ValuesIterator(iterator());
    }

    /**
     * Returns an iterator of the keys in the dictionary.
     *
     * @return iterator of the keys in the dictionary
     */
    @Override
@SuppressWarnings({"unchecked","rawtypes"})
    public Iterator<K> keys() {
        return new KeysIterator(iterator());
    }
}
