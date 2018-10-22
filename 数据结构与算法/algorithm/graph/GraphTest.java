package org.lium.algorithm.graph;

public class GraphTest {
    public static void main(String[] args) {
        // 建立图
        GraphNode n1 = new GraphNode(1);
        GraphNode n2 = new GraphNode(2);
        GraphNode n3 = new GraphNode(3);
        GraphNode n4 = new GraphNode(4);
        GraphNode n5 = new GraphNode(5);

        n1.neighbors = new GraphNode[]{n2,n3,n5};
        n2.neighbors = new GraphNode[]{n1,n4};
        n3.neighbors = new GraphNode[]{n1,n4,n5};
        n4.neighbors = new GraphNode[]{n2,n3,n5};
        n5.neighbors = new GraphNode[]{n1,n3,n4};

        GraphTest graphTest = new GraphTest();
        boolean isFind = graphTest.breathFirstSearch(n1, 9);
    }
    /**
     * 广度优先搜索 查找节点
     * @param root
     */
    public boolean breathFirstSearch(GraphNode root, int x) {
        if (root.val == x) {
            System.out.println("find" + x+ " in root");
            return true;
        }

        // 建立辅助队列
        Queue queue = new Queue();
        // 根节点入队
        root.isVisited = true;
        queue.enqueue(root);

        // 中断条件：队列为空
        while (queue.first != null) {
            // 出队队首节点
            GraphNode temp = queue.dequeue();

            // 取出邻居节点数组
            GraphNode[] neighbors = temp.neighbors;

            // 遍历邻居节点
            // 若邻居节点未被访问过，则入队
            for (GraphNode node : neighbors) {
                // 忽略 已经访问过的节点
                if (node.isVisited) {
                    continue;
                }

                // 处理未访问过的节点
                // 找到查找值，返回true
                if (node.val == x) {
                    System.out.println("图中含有给定值" + x);
                    return true;
                }
                // 不是查找值，标记为已访问，并入队
                node.isVisited = true;
                queue.enqueue(node);
            }
        }

        // 图中并未找到目标值
        System.out.println("途中无节点值为" + x);
        return false;
    }
}
