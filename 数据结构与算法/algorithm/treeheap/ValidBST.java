package org.lium.algorithm.treeheap;

import java.util.*;
/**
 * Definition for binary tree
 */
 class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }
class NodeBoundary {
    TreeNode node;
    TreeNode parent;
    // 左右边界，为null是表明不用考虑该边界
    Integer left;
    Integer right;
    NodeBoundary(TreeNode node, Integer left, Integer right) {
        this.node = node;
        this.left = left;
        this.right = right;
    }
}
public class ValidBST {
    /**
     * 验证二叉搜索树，只需验证每个节点满足 左子树所有节点 < 根 < 右子树所有节点
     * 迭代：可采用辅助队列 帮助层次遍历树 - 并记录每个节点的边界-取值范围
     *  取值范围：右子节点 ： 左边界：同当前节点 ； 右边界：为当前节点取值
     *  右子节点： 左边界： 为当前节点取值；右边界：同当前节点
     *
     *  其他思路：二分查找树 中序遍历 是递增的：中序遍历时保存前驱节点，和当前节点比较
     */
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }

        // 建立辅助队列
        LinkedList<NodeBoundary> queue = new LinkedList<>();
        // 根节点入队
        queue.offer(new NodeBoundary(root, Integer.MIN_VALUE, Integer.MAX_VALUE));

        // 迭代 遍历 树
        // 终止条件：队列为空，即迭代完成
        while( !queue.isEmpty() ) {
            // 出队一个节点
            NodeBoundary current = queue.poll();
            TreeNode cNode = current.node;
            // 验证该节点是否合法
            if (cNode.val >= current.right || cNode.val <= current.left) {
                return false;
            }

            // 入队其左子节点 及边界
            // 左边界：同当前节点
            // 右边界：为当前节点取值
            if (cNode.left != null) {
                queue.offer( new NodeBoundary(cNode.left, current.left, cNode.val) );
            }
            // 入队其右子节点 及边界
            // 左边界： 为当前节点取值
            // 右边界：同当前节点
            if (cNode.right != null) {
                queue.offer( new NodeBoundary(cNode.right, cNode.val, current.right) );
            }
        }
        // 没有异常
        return true;
    }
}
