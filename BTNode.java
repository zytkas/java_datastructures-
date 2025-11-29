package dataStructures;

/**
 * Binary Tree Node
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */
class BTNode<E> implements Node<E> {
    // Element stored in the node.
    private E element;

    // (Pointer to) the father.
    private Node<E> parent;

    // (Pointer to) the left child.
    private Node<E> leftChild;

    // (Pointer to) the right child.
    private Node<E> rightChild;

    /**
     * Constructor
     * @param elem
     */
    BTNode(E elem){
        this(elem,null,null,null);
    }

    /**
     * Constructor
     * @param elem
     * @param parent
     */
    BTNode(E elem, BTNode<E> parent) {
        this(elem,parent,null,null);
    }
    /**
     * Constructor
     * @param elem
     * @param parent
     * @param leftChild
     * @param rightChild
     */
    BTNode(E elem, BTNode<E> parent, BTNode<E> leftChild, BTNode<E> rightChild){
        this.element = elem;
        this.parent = parent;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    /**
     *  Returns the element of the node
     * @return
     */
    public E getElement() {
        return element;
    }
    /**
     * Returns the left son of node
     * @return
     */
    public Node<E> getLeftChild(){
        return leftChild;
    }
    /**
     * Returns the right son of node
     * @return
     */
    public Node<E> getRightChild(){
        return rightChild;
    }
    /**
     * Returns the parent of node
     * @return
     */
    public Node<E> getParent(){
        return parent;
    }

    /**
     * Returns true if node n does not have any children.
     * @return
     */
    boolean isLeaf() {
        return getLeftChild()== null && getRightChild()==null;
    }

    /**
     * Update the element
     * @param elem
     */
    public void setElement(E elem) {
        element=elem;
    }

    /**
     * Update the left child
     * @param node
     */
    public void setLeftChild(Node<E> node) {
        leftChild=node;
    }

    /**
     * Update the right child
     * @param node
     */
    public void setRightChild(Node<E> node) {
        rightChild=node;
    }

    /**
     * Update the parent
     * @param node
     */
    public void setParent(Node<E> node) {
        parent=node;
    }

    /**
     * Returns true if is the root
     */
    boolean isRoot() {
        return getParent()==null;
    }

    /**
     * Returns the height of the subtree rooted at this node.
     */

    public int getHeight() {
        if (isLeaf()) return 0;
        int leftH = ((BTNode<E>) leftChild).getHeight();
        int rightH = ((BTNode<E>) rightChild).getHeight();
        return 1 + Math.max(leftH, rightH);
    }

    /**
     *
     * @return
     */
    BTNode<E> furtherLeftElement() {
    BTNode<E> root = this;
    while (root.getLeftChild()!=null) {
        root = ((BTNode<E>) root.getLeftChild());
    }
	return root;
    }

   /**
     *
     * @return
     */
    BTNode<E> furtherRightElement() {
    BTNode<E> root = this;
    while (root.getRightChild()!=null) {
        root = ((BTNode<E>) root.getRightChild());
    }
	return root;
    }
    }
