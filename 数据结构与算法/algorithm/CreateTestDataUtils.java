package org.lium.algorithm;

import org.lium.algorithm.bst.TreeNode;

public class CreateTestDataUtils {
    /**
     * 填充数据
     * 层次遍历为：
     * 1
     * 23
     * 456
     * 6为3的右子节点
     * @return
     */
    public static TreeNode getBST() {
        // 填充根节点
        TreeNode treeNode = new TreeNode(1);
        // 填充左子树
        TreeNode treeNode1 = new TreeNode(2);
        treeNode.left = treeNode1;
        treeNode1.left = new TreeNode(4);
        treeNode1.right = new TreeNode(5);
        // 填充右子树
        TreeNode treeNode2 = new TreeNode(3);
        treeNode.right = treeNode2;
        treeNode2.right = new TreeNode(6);
        return treeNode;
    }

    public static TreeNode getBST2() {
        // 填充根节点
        TreeNode treeNode = new TreeNode(3);
        // 填充左子树
        TreeNode treeNode1 = new TreeNode(1);
        treeNode.left = treeNode1;
        treeNode1.right = new TreeNode(2);

        return treeNode;
    }
}
