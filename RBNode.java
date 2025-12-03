package dataStructures;

class RBNode<E> extends BTNode<E> {
    public static boolean RED = true;
    public static boolean BLACK = false;
    protected boolean color;

    public RBNode(E elem) {
        super(elem);
        this.color = RED;
    }

    public RBNode(E elem, RBNode<E> parent) {
        super(elem, parent, null, null);
        this.color = RED;
    }

    public RBNode(E elem, RBNode<E> parent, BTNode<E> next, RBNode<E> prev) {
        super(elem, parent, next, prev);
        this.color = RED;
    }

    public boolean isColor() {
        return color;
    }
    public void setColor(boolean color) {
        this.color = color;
    }
    public boolean isRed() {
        return color == RED;
    }
    public boolean isBlack() {
        return color == BLACK;
    }
    public void makeBlack() {
        this.color = BLACK;
    }
    public void makeRed() {
        this.color = RED;
    }

    public RBNode<E> getSibling() {
        if (isRoot()) return null;
        RBNode<E> parent = (RBNode<E>)this.getParent();
        if(this == parent.getLeftChild()){
            return (RBNode<E>)parent.getRightChild();
        }else{
            return (RBNode<E>)parent.getLeftChild();
        }
    }

    public RBNode<E> getUncle(){
        if(isRoot()) return null;
        RBNode<E> parent = (RBNode<E>)this.getParent();
        return parent.getSibling();
    }

    public RBNode<E> getGrandParent(){
        if(isRoot()) return null;
        RBNode<E> parent = (RBNode<E>)this.getParent();
        if(parent == null) return null;
        return (RBNode<E>) parent.getParent();
    }

    public boolean isRightChild(){
        if(isRoot()) return false;
        return this == ((RBNode<E>)getParent()).getRightChild();
    }

    public boolean isLeftChild(){
        if(isRoot()) return false;
        return this == ((RBNode<E>)getParent()).getLeftChild();
    }

    public boolean getColor() {
        return color;
    }
    public static <E> boolean isBlack(RBNode<E> node) {
        return node == null || node.isBlack();
    }

    public static <E> boolean isRed(RBNode<E> node) {
        return node != null && node.isRed();
    }
}