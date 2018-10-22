package org.lium.algorithm.permutation;

import java.util.ArrayList;
import java.util.List;

public class PermutationSequence {
    public static void main(String[] args) {
        PermutationSequence obj = new PermutationSequence();
        String str = obj.getPermutation( 4, 13 );
        System.out.println(str);
    }

    /**
     * 题目解析：给定整数n和k，返回在[1, 2,3, ... , n]形成的n!个全排列中第k大的全排列
     * 解题思路：1 <= k <= n!  假设n的前 i个数有 i! < k < (i + 1)!, 则前面（n - i  - 1）个数需按顺序排列
     * k = k % ( n - i)!
     * 若k 为0，则后面取最大数，每次取最大
     * 否则继续迭代
     * @param n
     * @param k
     * @return
     */
    public String getPermutation(int n, int k) {
        // 建立i 和 i!的映射关系
        // factorial保存 [1, 1, 2, 4, 6, ... , n!]
        int[] factorial = new int[n + 1];
        factorial[0] = 1;// 0的阶乘为1

        // 保存1，2，3，。。n
        for ( int i = 1; i <= n; i++ ) {
            factorial[i] = factorial[i - 1] * i;
        }

        // 将n转化为[1,2,3,4, ..., n]的list，
        List<Integer> nums = new ArrayList<>();
        // 添加 -1 ，只是为了让索引和值对应起来，方便理解
        nums.add(-1);
        for ( int i = 1; i <= n; i++ ) {
            nums.add(i);
        }

        // k为第k个
        // sb保存返回值
        StringBuilder sb = new StringBuilder();
        for ( int i = n - 1; i >= 0; i-- ) {
            // 声明下一个要删除的数字的索引
            int idx;

            // 如果K 已经为0，则说明前面除尽了，后面只需添加最大数即可
            if ( k == 0 ) {
                idx = nums.size() - 1;
            } else {
                // idx * factorial[i] < k <= (idx + 1) * factorial[i]
                // 所以取第idx个，idx从0计数
                // idx为0 表明：0 <= k < factorial[i]
                idx = k / factorial[i];
                // 每确定一位，更新k值
                k = k % factorial[i];

                // 除尽 idx = idx
                // 除不尽 idx = idx + 1;
                if (k != 0) {
                    idx += 1;
                }
            }

            // 每确定一位，删除之
            sb.append( nums.get( idx ) );
            nums.remove( idx );
        }

        return sb.toString();
    }
}
