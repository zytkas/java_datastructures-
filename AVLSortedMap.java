package dataStructures;
/**
 * AVL Tree Sorted Map
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class AVLSortedMap <K extends Comparable<K>,V> extends AdvancedBSTree<K,V>{
    /**
     * 
     * @param key
     * @param value
     * @return
     */
    public V put(K key, V value) {
        if(root == null){
            root = new AVLNode<>(new Entry<>(key,value), null, null, null);
            currentSize++;
            return null;
        }

        AVLNode<Entry<K, V>> parent = null;
        AVLNode<Entry<K, V>> current = (AVLNode<Entry<K, V>>) root;

        while(current != null){
            int comp = key.compareTo(current.getElement().key());
            if (comp == 0) {
               V old = current.getElement().value();
               current.setElement(new Entry<>(key,value));
               return old;
            }
            parent = current;
            if (comp < 0) {
                current = (AVLNode<Entry<K, V>>) current.getLeftChild();
            } else  {
                current = (AVLNode<Entry<K, V>>) current.getRightChild();
            }
        }
        AVLNode<Entry<K, V>> newNode = new AVLNode<>(new Entry<>(key, value), parent, null, null);
        if (key.compareTo(parent.getElement().key()) < 0) {
            parent.setLeftChild(newNode);
        } else {
            parent.setRightChild(newNode);
        }
        currentSize++;
        rebalance(newNode);
        return null;

    }

    /**
     *
     * @param key whose entry is to be removed from the map
     * @return
     */
    public V remove(K key) {
        BTNode<Entry<K, V>> nodeToRemove = getNode((BTNode<Entry<K, V>>) root, key);
        if (nodeToRemove == null) return null;
        V oldValue = nodeToRemove.getElement().value();
        BTNode<Entry<K, V>> startRebalanceNode = removeNode(nodeToRemove);
        currentSize--;
        if (startRebalanceNode != null) {
            rebalance((AVLNode<Entry<K, V>>) startRebalanceNode);
        }
        return oldValue;
    }

    protected void rebalance(AVLNode<Entry<K, V>> zPos) {
        if (zPos.isLeaf()) {
            zPos.updateHeight();
        }

        while (zPos != null) {
            zPos.updateHeight();

            if (!zPos.isBalanced()) {

                AVLNode<Entry<K, V>> yPos = zPos.tallerChild();
                AVLNode<Entry<K, V>> xPos = yPos.tallerChild();

                zPos = (AVLNode<Entry<K, V>>) restructure(xPos);

                if (zPos.getLeftChild() != null)
                    ((AVLNode<Entry<K, V>>) zPos.getLeftChild()).updateHeight();

                if (zPos.getRightChild() != null)
                    ((AVLNode<Entry<K, V>>) zPos.getRightChild()).updateHeight();

                zPos.updateHeight();
            }

            zPos = (AVLNode<Entry<K, V>>) zPos.getParent();
        }
    }
}
