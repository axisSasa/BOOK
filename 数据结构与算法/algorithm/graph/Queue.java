package org.lium.algorithm.graph;

public class Queue {
    GraphNode first, last;

    /**
     * 入队
     * @param node
     */
    public void enqueue(GraphNode node) {
        if (first == null) {
            first = node;
            last = first;
        } else {
            last.next = node;
            last = last.next;
        }
    }

    /**
     * 出队
     * @return
     */
    public GraphNode dequeue() {
        if (first == null) {
            return null;
        }
        GraphNode temp = new GraphNode(first.val, first.neighbors);
        first = first.next;

        // 返回队首节点
        return temp;
    }
}
