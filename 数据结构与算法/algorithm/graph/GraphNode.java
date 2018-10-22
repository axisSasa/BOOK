package org.lium.algorithm.graph;

public class GraphNode {
    int val;
    GraphNode next;
    GraphNode[] neighbors;
    boolean isVisited;

    GraphNode(int val) {
        this.val = val;
    }

    GraphNode(int val, GraphNode[] neighbors) {
        this.val = val;
        this.neighbors = neighbors;
    }

    @Override
    public String toString() {
        return "value=" + val;
    }
}
