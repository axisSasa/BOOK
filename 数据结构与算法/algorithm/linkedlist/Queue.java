package org.lium.algorithm.linkedlist;

public class Queue {
    private Node first;
    private Node last;

    /**
     * 入队
     * @param node
     */
    public void enqueue(Node node) {
        if (first == null) {
            first = node;
            last = first;
        } else {
            last.setNext(node);
            last = node;
        }
    }

    /**
     * 返回队首元素
     *
     * @return
     */
    public Node dequeue() {
        if (first == null) {
            return null;
        }
        Node temp = new Node(first.getValue());
        first = first.getNext();
        return temp;
    }

    public Node getFirst() {
        return first;
    }

    public void setFirst(Node first) {
        this.first = first;
    }

    public Node getLast() {
        return last;
    }

    public void setLast(Node last) {
        this.last = last;
    }

    public static void main(String[] args) {
        Node<String> node = new Node<String>("English");
        Queue queue = new Queue();
        queue.enqueue(node);
        queue.enqueue( new Node<String>("Chinese") );
        System.out.println(queue.dequeue().getValue());
        System.out.println(queue.dequeue().getValue());
    }
}
