package org.lium.algorithm.graph;


import java.util.*;

class UndirectedGraphNode {
      int label;
      ArrayList<UndirectedGraphNode> neighbors;
      UndirectedGraphNode(int x) { label = x; neighbors = new ArrayList<UndirectedGraphNode>(); }
  };

public class UndirectedGraphCopy {

    /**
     * 题目解析：给定一个无向图，图内节点包括节点值、节点的邻居；需要复制该图
     * 解题思路：
     * 图的遍历可添加一个辅助队列来左广度优先搜索
     * 再者，因为节点未给是否已经访问的属性，可以添加一个map来记录已经访问的节点，和复制的节点
     * 同时map帮助复制 邻居节点
     */
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if (node == null) {
            return null;
        }

        // 建立辅助队列
        LinkedList<UndirectedGraphNode> queue = new LinkedList<>();

        // 建立map，跟踪已经访问的节点
        Map<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<>();

        // 节点入队
        queue.offer(node);

        // 复制节点-只复制了label域，neighbors域是引用对象，直接复制无意义
        UndirectedGraphNode newNode = new UndirectedGraphNode(node.label);

        // 节点进入代表已经访问的map
        map.put(node, newNode);

        // 中断条件：队列为空
        while(!queue.isEmpty()) {
            // 出队
            UndirectedGraphNode temp = queue.poll();
            // 获取邻居，遍历邻居
            ArrayList<UndirectedGraphNode> neighbors = temp.neighbors;
            for(UndirectedGraphNode neighbor : neighbors) {
                // 声明当前邻居节点 的 复制节点
                UndirectedGraphNode copy;

                // 若邻居未访问过
                if (!map.containsKey(neighbor)) {
                    // 该邻居节点入队
                    queue.offer(neighbor);

                    // 复制该邻居节点
                    copy = new UndirectedGraphNode(neighbor.label);

                    // 记录进map
                    map.put(neighbor, copy);

                    // copy的节点应该 与 根节点对应的节点建立邻居联系
                    map.get(temp).neighbors.add(copy);
                } else {
                    // 邻居节点 之前被访问过
                    // 邻居节点的 复制节点 应该 与 根节点对应的节点建立邻居联系
                    // 获取邻居节点的复制节点
                    copy = map.get(neighbor);
                }

                // 无论当前邻居节点 是否被访问过
                // 它的复制节点 都应该跟 复制的根节点建立 邻居关系
                map.get(temp).neighbors.add(copy);
            }
        }

        // 返回 复制的图
        return newNode;
    }
}
