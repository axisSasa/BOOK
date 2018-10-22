package org.lium.algorithm.permutation;

import java.util.ArrayList;

public class Permutations {
    public static void main(String[] args) {
        Permutations permutations = new Permutations();
        int[] arr = new int[]{1, 2, 3};
        ArrayList<ArrayList<Integer>> lists = permutations.permute(arr);
        for (ArrayList<Integer> list : lists) {
            System.out.println(list.toString());
        }
    }

    /**
     * 题目解析：给定一个不包含重复数字的数组，求其全排列
     * 解题思路：网上给了两种方案：求解子问题，再子问题的基础上加下一个字符插入
     * or 交换数字位置 递归 再交换回来
     * 第二种我不能理解其正确性，所以选择第一种思路解题
     * 第一种可建立辅助队列，利用BFS更新队列，最后返回结果
     * @param num
     * @return
     */
    public ArrayList<ArrayList<Integer>> permute(int[] num) {
        // 判空
        if ( num == null || num.length == 0 ) {
            return null;
        }
        // 建立数据结构保存返回结果
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();

        // 将第一个数字放入第一个list
        ArrayList<Integer> first = new ArrayList<>();
        first.add( num[0] );
        lists.add( first );

        // 遍历入参数组
        for ( int i = 1; i < num.length; i++ ) {

            // 遍历所有排列,在每个不同位置分别插入，形成新排列
            // lists会变化，所以必须取出长度
            int listsLen = lists.size();
            for (int k = 0; k < listsLen; k++) {

                // 先在lists中删除已存在排列list
                // lists会变化，每次删除第一个
                ArrayList<Integer> list = lists.remove(0);

                // 在list的索引为0到len的位置插入新的数字 得到新排列
                int listLen = list.size();
                for ( int j = 0; j <= listLen; j++ ) {
                    ArrayList<Integer> copy = (ArrayList<Integer>) list.clone();
                    copy.add( j, num[i]);
                    lists.add( copy );
                }
            }
        }

        // 返回全排列
        return lists;
    }
}
