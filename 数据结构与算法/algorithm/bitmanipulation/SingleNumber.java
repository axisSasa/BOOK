package org.lium.algorithm.bitmanipulation;

public class SingleNumber {
    public static void main(String[] args) {
        SingleNumber singleNumber = new SingleNumber();
        int[] arr = new int[]{2, 2, 4, 5, 6, 5, 6};
        int single = singleNumber.singleNumber(arr);
        System.out.println(single);
    }
    /**
     * 题目解析：给定一个整数数组，数组中只有一个数字只出现了一次，其他数字都出现了两次，找出现一次的数字
     * 解题分析：不使用额外内存，显然使用位操作最佳
     * 按位异或：每位不同得1；相同得0 =》 两个相同的数异或一定为0
     * =》所有数异或后，相同的数都异或为0了 =》题目变为了 一个数 和0 异或
     * =》 一个数 和0 异或 等于这个数本身
     * =》所以此题只需把所有数一起和0 异或就好了
     */
    public int singleNumber(int[] A) {
        int result = 0;
        for (int a : A) {
            result ^= a;
        }
        return result;
    }
}
