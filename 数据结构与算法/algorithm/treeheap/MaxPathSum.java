package org.lium.algorithm.treeheap;


    public class MaxPathSum {
        // 定义最大路径节点和
        int max = Integer.MIN_VALUE;

        public static void main(String[] args) {
            TreeNode node = new TreeNode(-3);
            MaxPathSum maxPathSum = new MaxPathSum();
            int temp = maxPathSum.maxPathSum(node);
            System.out.println(temp);
        }


        /**
         * 题目解析：给定二叉树，找到树中任意两点使得两点之间路径上的大节点和 最大
         * 解题思路：以根节点分割，求左子树的最大和 及 右子树最大 和
         */
        public  int maxPathSum(TreeNode root) {
            // 处理空树
            if (root == null) {
                return 0;
            }

            // 求当前节点最大值
            maxSum(root);

            // 返回最大值
            return max;
        }

        /**
         * 取得某节点 的最大路径 和
         */
        public int maxSum(TreeNode root) {
            // 处理空节点
            if (root == null) {
                return 0;
            }

            // 当前节点为根节点形成的path的最大和
            int temp = root.val;
            // 当前节点值
            int rootVal = root.val;

            // 左子树最少为0-不要左子树
            // 右子树最少为0-不要右子树
            int leftMax = 0;
            int rightMax = 0;

            // 左/右子树最好路径 加入当前节点
            if (root.left != null) {
                leftMax = maxSum(root.left);
                if (leftMax > 0) {
                    temp += leftMax;
                }
            }
            if (root.right != null) {
                rightMax = maxSum(root.right);
                if (rightMax > 0) {
                    temp += rightMax;
                }
            }

            // 当前节点构成的最大路径和  更新 最大值
            if (temp > max) {
                max = temp;
            }

            // 返回 当前节点 及其之下的 所有子树 能返回的最好路径和
            // 左右子树和都 < 0， 返回 根
            // 根 + 左子树 最大； 返回 根 + 左子树
            // 根 + 右子树 最大； 返回 根 + 右子树
            return Math.max( rootVal, Math.max(rootVal + leftMax, rootVal + rightMax) );
        }
    }