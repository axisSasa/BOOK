package org.lium.algorithm.treeheap;

import java.util.*;

public class InorderPostorderMakeBT {

    public static void main(String[] args) {
//        int[] inorder = new int[2];
//        int[] postorder = new int[2];
        int[] inorder = {2, 1};
        int[] postorder = {1, 2};
        TreeNode temp = buildTree(inorder, postorder);
    }

    /**
     * 题目解读：
     * 根据中序遍历 & 后序遍历的结果，还原 二叉树
     * 假设 树中无 重复节点
     * -------------------
     * 解题思路：
     * 后序遍历获取根节点，将中序遍历分为左右子树
     * 左子树的长度为Ｎ
     * ＊后序遍历前Ｎ个节点为左子树后序遍历结果，获取其最后一个节点为根节点
     * ＊迭代循环
     */
    public static TreeNode buildTree(int[] inorder, int[] postorder) {
        // 左右子树 不用新建数组，用索引代表
        // 数组先转换为List，方便使用
        return buildTree(intArray2IntegerList(inorder),
                intArray2IntegerList(postorder));
    }

    public static TreeNode buildTree(List<Integer> inorder,
                                     List<Integer> postorder) {
        // 获取输入数组长度，方便使用
        int len = inorder.size();

        // 两数组长度不等，返回null
        // 数组为空，返回null
        if (len != postorder.size() || len == 0) {
            return null;
        }

        // 从后序遍历数组获取根节点值
        Integer rootValue = postorder.get(len - 1);
        // 建立根节点
        TreeNode root = new TreeNode(rootValue);

        // 确定根节点在中序遍历中的位置
        Integer rootIndex = inorder.indexOf(rootValue);

        // 分割现有数组， 形成左右子节点用的新数组
        // 左子节点使用的中序遍历数组
        List<Integer> leftInorder = inorder.subList(0, rootIndex);
        // 右子节点使用的中序遍历数组
        List<Integer> rightInorder = inorder.subList(rootIndex + 1, len);
        // 左子节点使用的后序遍历数组
        List<Integer> leftPostorder = postorder.subList(0, rootIndex);
        // 右子节点使用的后序遍历数组
        List<Integer> rightPostorder = postorder.subList(rootIndex, len - 1);

        // 递归构造 左右子节点
        root.left = buildTree(leftInorder, leftPostorder);
        root.right = buildTree(rightInorder, rightPostorder);

        // 返回根节点
        return root;
    }

    /**
     * 返回索引
     */
    public static int indexOfIntArray(int[] arr, int value) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 基本数据类型不能直接用asList转List
     *
     * @param arr
     * @return
     */
    public static List<Integer> intArray2IntegerList(int[] arr) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            list.add(arr[i]);
        }
        return list;
    }
}