package dataStructures;

public class RBSortedMap <K extends Comparable<K>,V> extends AdvancedBSTree<K,V>{
    public RBSortedMap() {
        super();
    }

    @Override
    public V put(K key, V value) {
        if(root==null){
            root = new RBNode<>(new Entry<>(key,value), null, null, null);
            ((RBNode<Entry<K,V>>)root).makeBlack();
            currentSize++;
            return null;
        }

        RBNode<Entry<K,V>> parent = null;
        RBNode<Entry<K,V>> current = (RBNode<Entry<K,V>>) root;
        while(current!=null){
            int comp = key.compareTo(current.getElement().key());
            if(comp==0){
                V oldValue = current.getElement().value();
                current.setElement(new Entry<>(key,value));
                return oldValue;
            }
            parent = current;
            if(comp<0){
                current = ((RBNode<Entry<K,V>>)current.getLeftChild());
            }else{
                current = ((RBNode<Entry<K,V>>)current.getRightChild());
            }
        }
        RBNode<Entry<K,V>> newNode = new RBNode<>(new Entry<>(key,value), parent, null, null);
        if(key.compareTo(parent.getElement().key()) < 0){
            parent.setLeftChild(newNode);
        }else {
            parent.setRightChild(newNode);
        }
        currentSize++;
        fixInsertion(newNode);
        return null;
    }

    private void fixInsertion(RBNode<Entry<K,V>> node){
        while (node!=null && node != root && RBNode.isRed((RBNode<Entry<K,V>>) node.getParent())){
            RBNode<Entry<K, V>> y = (RBNode<Entry<K,V>>) node.getParent();
            RBNode<Entry<K, V>> z = node.getGrandParent();
            if(z==null) break;
            RBNode<Entry<K, V>> s = node.getUncle();
            if(RBNode.isBlack(s)){
                RBNode<Entry<K, V>> b = (RBNode<Entry<K, V>>) restructure(node);
                b.makeBlack();
                if(b.getLeftChild() != null){
                    ((RBNode<Entry<K, V>>)b.getLeftChild()).makeRed();
                }
                if(b.getRightChild() != null){
                    ((RBNode<Entry<K, V>>)b.getRightChild()).makeRed();
                }
                break;
            }else {
                y.makeRed();
                s.makeBlack();
                if(z != root){
                    z.makeRed();
                }
                node = z;
            }
        }
        ((RBNode<Entry<K,V>>) root).makeBlack();
    }

    @Override
    public V remove(K key) {
        RBNode<Entry<K, V>> node = (RBNode<Entry<K, V>>) getNode((BTNode<Entry<K, V>>) root, key);
        if (node == null) return null;

        V oldValue = node.getElement().value();

        RBNode<Entry<K, V>> toDelete = node;
        if(node.getLeftChild()!=null && node.getRightChild()!=null){
            toDelete = (RBNode<Entry<K, V>>) (((BTNode<Entry<K, V>>) node.getRightChild()).furtherLeftElement());
        }
        boolean deletedIsBlack = toDelete.isBlack();
        RBNode<Entry<K, V>> replacement = (RBNode<Entry<K, V>>)
                (toDelete.getLeftChild() != null ? toDelete.getLeftChild() : toDelete.getRightChild());
        RBNode<Entry<K, V>> fixParent = (RBNode<Entry<K, V>>) removeNode(toDelete);
        currentSize--;

        if (deletedIsBlack){
            if(RBNode.isRed(replacement)){
                replacement.makeBlack();
            }else if (fixParent != null){
                fixDeletion(replacement, fixParent);
            }
        }
        return oldValue;
    }

    private void fixDeletion(RBNode<Entry<K, V>> p, RBNode<Entry<K, V>> parent){
        while (p != root && RBNode.isBlack(p) && parent != null){
            RBNode<Entry<K,V>> y;
            boolean isLeftChild = (p == parent.getLeftChild());
            if(isLeftChild){
                y = (RBNode<Entry<K,V>>) parent.getRightChild();
            } else {
                y = (RBNode<Entry<K,V>>) parent.getLeftChild();
            }

            if (RBNode.isBlack(y)){
                RBNode<Entry<K,V>> x = getRedChildOf(y);
                if (x != null){
                    boolean oldParentColor = parent.getColor();
                    RBNode<Entry<K,V>> b = (RBNode<Entry<K,V>>) restructure(x);
                    b.setColor(oldParentColor);
                    if(b.getLeftChild() != null){
                        ((RBNode<Entry<K, V>>)b.getLeftChild()).makeBlack();
                    }
                    if(b.getRightChild() != null){
                        ((RBNode<Entry<K, V>>)b.getRightChild()).makeBlack();
                    }
                    break;
                } else {
                    y.makeRed();
                    if (parent.isRed()){
                        parent.makeBlack();
                        break;
                    } else {
                        p = parent;
                        parent = (RBNode<Entry<K,V>>) p.getParent();
                    }
                }

            } else {
                RBNode<Entry<K, V>> z = parent;
                if(isLeftChild){
                    rotateLeft(z);
                }else {
                    rotateRight(z);
                }
                y.makeBlack();
                z.makeRed();
            }
        }
        if (p != null){
            p.makeBlack();
        }
    }

    private RBNode<Entry<K, V>> getRedChildOf(RBNode<Entry<K, V>> y) {
        if (y == null) return null;

        RBNode<Entry<K,V>> left = (RBNode<Entry<K,V>>) y.getLeftChild();
        RBNode<Entry<K,V>> right = (RBNode<Entry<K,V>>) y.getRightChild();

        boolean isLeftChild = y.isLeftChild();

        if (isLeftChild){
            if(RBNode.isRed(left)) return left;
            if(RBNode.isRed(right)) return right;
        } else {
            if(RBNode.isRed(right)) return right;
            if(RBNode.isRed(left)) return left;
        }
        return null;
    }

    public int getBlackHeight() {
        if (isEmpty()) return -1;
        return getBlackHeight((RBNode<Entry<K, V>>) root);
    }

    private int getBlackHeight(RBNode<Entry<K, V>> node) {
        if (node == null) return 1; // NIL nodes count as black
        int leftBlackHeight = getBlackHeight((RBNode<Entry<K, V>>) node.getLeftChild());
        return leftBlackHeight + (node.isBlack() ? 1 : 0);
    }
}
