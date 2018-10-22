package org.lium.algorithm.linkedlist;

public class Stack {
    Node top;

    public Stack(Node top) {
        this.top = top;
    }

    public Stack() {
    }

    /**
     * 返回栈顶节点索引
     * @return
     */
    public Node peek() {
        if (top != null) {
            return top;
        }
        return null;
    }

    /**
     * 返回栈顶节点
     * 栈顶节点出栈
     * @return
     */
    public Node pop() {
        if (top == null) {
            return null;
        }
        // 将栈顶节点数据取出，形成新节点
        Node temp = new Node( top.getValue() );
        // 栈顶元素出栈
        top = top.getNext();
        // 返回栈顶元素
        return temp;
    }

    /**
     * 添加节点到栈顶
     * @param node
     */
    public void push( Node node ) {
        if ( node != null ) {
            node.setNext(top);
            top = node;
        }
    }

    public static void main(String[] args) {
        Node<String> node = new Node<String>("English");
        Stack stack = new Stack(node);
        stack.push( new Node<String>("Chinese") );
        System.out.println(stack.peek().getValue());
        System.out.println(stack.peek().getValue());
        System.out.println(stack.pop().getValue());
        System.out.println(stack.pop().getValue());
    }
}
