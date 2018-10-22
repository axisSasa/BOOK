package org.lium.algorithm.linkedlist;

public class Node<E> {
    private E value;
    private Node next;

    public Node(E value, Node next) {
        this.value = value;
        this.next = next;
    }

    public Node(E value) {
        this.value = value;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
