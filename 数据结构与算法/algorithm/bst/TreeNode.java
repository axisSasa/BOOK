package org.lium.algorithm.bst;

import org.lium.algorithm.CreateTestDataUtils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 二叉搜索树节点
 */
public class TreeNode {
    public int val; // 数据域
    public TreeNode left; // 左子树根节点
    public TreeNode right; // 右子树根节点

    public TreeNode() {
    }

    public TreeNode(int val) {
        this.val = val;
    }

    public static void main(String[] args) {
        long temp =preorderTraversal2(CreateTestDataUtils.getBST2());
    }

    /**
     * 前序遍历 - 递归实现
     * @param root
     */
    public static long preorderTraversalRec(TreeNode root) {
        if (root == null) {
            return 0;
        }
        long result = 0;
        System.out.println(root.val);
        result = root.val;
        // 函数返回的结果可能是>1位的数
        // 提取位数
        long resultL = preorderTraversalRec(root.left);
        // 返回0则result不进位
        // 返回结果是n位数，result * 10的n次方
        int len = (0 == resultL) ? 0 : (resultL + "").length();
        result = result * (int) Math.pow(10, len) + resultL;

        long resultR = preorderTraversalRec(root.right);
        len = (0 == resultR) ? 0 : (resultR + "").length();
        result = result * (int) Math.pow(10, len) + resultR;
        return result;
    }

    /**
     * 前序遍历 - 非递归 - 辅助栈实现
     * @param root
     */
    public static long preorderTraversal2(TreeNode root) {
        if (root == null) {
            return -1;
        }
        long result = 0;
        // 建立辅助栈
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cursor = root;
        while ( cursor != null || !stack.isEmpty() ) {
            // 遍历 根节点 - 左子节点 入栈
            while (cursor != null) {
                stack.push(cursor);
                System.out.println(cursor.val);
                result = result * 10 + cursor.val;
                // 递归 左子树的左子节点
                cursor = cursor.left;
            }
            // 遍历栈，取出左子节点，并让光标指向左子节点的右子节点
            if ( !stack.isEmpty() ) {
                cursor = stack.pop().right;
            }
        }
        return result;
    }

    /**
     * 后序遍历-非递归实现-双栈法
     * @param root
     * @return
     */
    public static String postorderTraversal(TreeNode root) {
        if (root == null) {
            return "";
        }
        String result = "";
        Stack<TreeNode> stack = new Stack<>();
        Stack<TreeNode> stackResult = new Stack<>(); //保存遍历结果
        stack.add(root);
        while ( !stack.isEmpty() ) {
            TreeNode temp = stack.pop();
            // 存入节点
            stackResult.push(temp);
            // 左右子节点依次入栈
            if (temp.left != null) {
                stack.push(temp.left);
            }
            if (temp.right != null) {
                stack.push(temp.right);
            }
        }
        if (!stackResult.isEmpty()) {
            int value = stackResult.pop().val;
            System.out.println(value);
            result += value;
        }
        return result;
    }

    /**
     * 后序遍历 -递归实现
     * @param root
     */
    public static void postorderTraversalRec(TreeNode root){
        if (root == null) {
            return;
        }
        postorderTraversalRec(root.left);
        postorderTraversalRec(root.right);
        System.out.print(root.val + "->");
    }

    /**
     * 层次遍历
     * @param root
     */
    public static String levelTraversal(TreeNode root) {
        String result = "";
        if (root == null) {
            return result;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while ( !queue.isEmpty() ) {
            TreeNode temp = queue.poll();
            System.out.println(temp.val);
            result += temp.val;
            if ( temp.left != null ) {
                queue.add(temp.left);
            }
            if ( temp.right != null ) {
                queue.add(temp.right);
            }
        }
        return result;
    }

    /**
     * 填充数据
     * 层次遍历为：
     * 1
     * 23
     * 456
     * 6为3的右子节点
     * @return
     */
    public static TreeNode getData() {
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
}
