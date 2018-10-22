package org.lium.algorithm.treeheap;


public class BalancedBinaryTree {
    /**
     * 题目解析：给定二叉树，确定其是否是平衡二叉树
     * 解题思路：判断每个节点 的 左右子树是否为 平衡二叉树
     * 平衡二叉树判断：子树高度差不大于1
     * 空子树：为平衡二叉树
     * 先判断左右子树的平衡性
     * 在判断根节点的平衡性
     * 最后返回根节点的平衡性
     * 树高度代表平衡性
     */
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        // 不等于-1， 返回 true
        // 等于-1 返回 false
        if ( getHeight(root) == -1) {
            return false;
        }
        return true;
    }

    /**
     * 获取树高度
     * -1 代表不平衡
     * 正数代表高度
     */
    public int getHeight(TreeNode root) {
        // 空子树 高度为0
        if (root == null) {
            return 0;
        }

        // 获取左右子树最大高度
        int leftHeight = getHeight(root.left);
        int rightHeight = getHeight(root.right);

        // 如果左右子树有不平衡， 则根节点的树 也不平衡
        if (leftHeight == -1 || rightHeight == -1) {
            return -1;
        }

        // 高度差 大于1， 返回 -1，代表不平衡
        int diff = leftHeight - rightHeight;
        if ( diff < -1 || diff > 1 ) {
            return -1;
        }

        // 下面是根节点代表的树是平衡二叉树
        // 返回树最大高度
        return Math.max(leftHeight, rightHeight) + 1;
    }
}