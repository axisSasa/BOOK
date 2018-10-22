package org.lium.algorithm.permutation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PermutationsTwo {
    public static void main(String[] args) {
        PermutationsTwo permutationsTwo = new PermutationsTwo();
        int[] arr = new int[] {1, 1, 2};
        ArrayList<ArrayList<Integer>> lists = permutationsTwo.permuteUnique(arr);
        for (ArrayList<Integer> list : lists) {
            System.out.println(list.toString());
        }
    }

    /**
     * 题目解析：给定一个可能含有重复数字的数组，求其全排列
     * 解题解析：本题和Permutations区别只是，数字有重复，所以完全可以利用ermutations
     * 只需额外维护结果不重复即可- 在添加的时候可以使用set保持唯一性
     * @param num
     * @return
     */
    public ArrayList<ArrayList<Integer>> permuteUnique(int[] num) {
        // 建立数据结构维护返回值
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();

        // 添加第一个节点的全排列
        ArrayList<Integer> first = new ArrayList<>();
        first.add( num[0] );
        lists.add( first );

        // 遍历num剩余数字，添加进全排列
        for ( int i = 1; i < num.length; i++ ) {

            // 建立数据结构存储当前全排列
            Set<ArrayList<Integer>> curSet = new HashSet<>();

            // 取出先前全排列
            for (ArrayList<Integer> list : lists) {
                // 构建新的全排列
                for ( int j = 0; j <= list.size(); j++ ) {
                    ArrayList<Integer> copy = (ArrayList<Integer>) list.clone();
                    copy.add( j, num[i] );
                    curSet.add( copy );
                }
            }

            // 更新返回值
            lists = new ArrayList<>(curSet);
        }
        return lists;
    }
}
