package org.lium.algorithm.dp;

public class MaximumSubarray {
    public static void main(String[] args) {
        MaximumSubarray maximumSubarray = new MaximumSubarray();
        int[] arr = new int[]{-2,1,-3,4,-1,2,1,-5,4};
        int maxSum = maximumSubarray.maxSubArray(arr);
        System.out.println(maxSum);
    }

    /**
     * 题目解析：给定一个包含数字的数组，求里面的子数组中数字和最大的 子数组
     * 解题分析：
     * 定义：F(i) 代表 数组下标 0 - i 中的最大子数组
     * 递归公式:  i >=1时 F(i) = max{ F(i - 1) + A[i], A[i] }
     * i = 0 时 F(i) = A[i]
     * @param A
     * @return
     */
    public int maxSubArray(int[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }

        // 建立一维表 - 其实不用建立，可用数组A替代
        // int[] dp = new int[len];
        // 最大子数组和
        int maxSum = A[0];
        for (int i = 1; i < A.length; i++) {

            // 递推公式 得局部最优解
            A[i] = Math.max(A[i - 1] + A[i], A[i]);

            // 更新总的最优解
            if ( A[i] > maxSum ) {
                maxSum = A[i];
            }
        }
        return maxSum;
    }
}
