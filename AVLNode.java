package dataStructures;
/**
 * AVL Tree Node
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */
class AVLNode<E> extends BTNode<E> {
    // Height of the node
    protected int height;

    public AVLNode(E elem) {
        super(elem);
        height = 0;
    }

    public AVLNode(E element, AVLNode<E> parent,
                   AVLNode<E> left, AVLNode<E> right) {
        super(element, parent, left, right);
        updateHeight();
    }

    public AVLNode(E element, AVLNode<E> parent) {
        super(element, parent, null, null);
        height = 0;
    }

    private int height(AVLNode<E> no) {
        if (no == null) return -1;
        return no.getHeight();
    }


    public int getHeight() {
        return height;
    }

    /**
     * Update the left child and height
     *
     * @param node
     */
    public void setLeftChild(AVLNode<E> node) {
        super.setLeftChild(node);
        updateHeight();
    }

    /**
     * Update the right child and height
     *
     * @param node
     */
    public void setRightChild(AVLNode<E> node) {
        super.setRightChild(node);
        updateHeight();
    }

    public void updateHeight() {
        int leftH = height((AVLNode<E>) getLeftChild());
        int rightH = height((AVLNode<E>) getRightChild());
        height = 1 + Math.max(leftH, rightH);
    }

    public int getBalance() {
        int leftH = height((AVLNode<E>) getLeftChild());
        int rightH = height((AVLNode<E>) getRightChild());
        return leftH - rightH;
    }

    public boolean isBalanced() {
        int diff = getBalance();
        return diff == 0 || diff == -1 || diff == 1;
    }

    public AVLNode<E> tallerChild(){
        int leftH = (getLeftChild() != null) ? ((AVLNode<E>) getLeftChild()).getHeight() : 0;
        int rightH = (getRightChild() != null) ? ((AVLNode<E>) getRightChild()).getHeight() : 0;

        if(leftH > rightH){
            return (AVLNode<E>) getLeftChild();
        }else if(leftH < rightH){
            return (AVLNode<E>) getRightChild();
        } else {
            if(isRoot()) return (AVLNode<E>) getLeftChild();
            BTNode<E> parent = (BTNode<E>) getParent();
            if (this == parent.getLeftChild()) {
                return (AVLNode<E>) getLeftChild();
            } else {
                return (AVLNode<E>) getRightChild();
            }
        }
    }
}