package org.lium.algorithm.treeheap;

public class SortedArrayTwoBBST {
    /**
     * 题目分析：升序数组 转 平衡二叉搜索树
     * 二叉搜索树：节点的所有左子树的节点 < 节点 < 节点的所有右子树节点
     * 平衡二叉搜索树：左右两个子树的高度差的绝对值不超过1；左右子树也为平衡二叉树
     * 常用算法：红黑树 AVL树等
     * 解题思路：
     * 数组中间任意节点 满足 搜索二叉树条件 的根节点
     * 取居中的点，则左右子树 数量一致，平衡性更高
     */
    public TreeNode sortedArrayToBST(int[] num) {
        int len = num.length;
        if (len == 0) {
            return null;
        }
        return sortedArrayToBST(num, 0, len - 1);
    }

    public static TreeNode sortedArrayToBST(int[] num, int startIndex, int endIndex) {
        // 处理异常
        if ( startIndex > endIndex ) {
            return null;
        }
        // 取中间节点
        // 若和为偶数【两索引间有奇数个数】，则mid恰为startIndex, endIndex 中间节点
        // 若和为奇数【两索引间有偶数个数】，则mid为startIndex, endIndex 中间偏左节点
        int mid = ( startIndex + endIndex ) / 2;

        // 构造根节点
        TreeNode root = new TreeNode( num[mid] );

        // 递归处理 左右子节点
        root.left = sortedArrayToBST( num, startIndex, mid - 1 );
        root.right = sortedArrayToBST( num, mid + 1, endIndex );

        // 返回根节点
        return root;
    }
}
