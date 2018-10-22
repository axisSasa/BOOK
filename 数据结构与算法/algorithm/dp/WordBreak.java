package org.lium.algorithm.dp;

import java.util.HashSet;
import java.util.Set;

public class WordBreak {
    public static void main(String[] args) {
        WordBreak wordBreak = new WordBreak();
        Set<String> dict = new HashSet<>();
        dict.add("leet");
        dict.add("code");
        boolean result = wordBreak.wordBreak("leetcode", dict);
        System.out.println(result);
    }
    /**
     * 题目解析：给定一个字符串s,给定一个包含许多单词的字典dict
     * 将s用空白符分割成1个或多个子串，且这些子串需全部在dict出现
     * 判断能否存在 这种分割
     * 解题解析：DP
     *  子问题：若s能被合法分割， 则s的子串也能被分割
     *   建立一维表：dp[i] == true 代表 s.substring(0, i + 1) 为可合法分割子串
     *   若dp[i] == true  且 s.substring(i + 1) in dict， 则s能被合法分割
     */
    public boolean wordBreak(String s, Set<String> dict) {
        // 获取字符串长度，方便使用
        int len = s.length();

        // 建立一维表dp
        boolean[] dp = new boolean[len + 1];
        // s前面什么都没有
        // 可理解为s前面是可分割的
        dp[0] = true;

        // 循环处理子问题
        // i = 1 代表处理第一个字符
        for (int i = 1; i <= len; i++) {

            // 具体子问题
            // 针对前i个字符 判断能否被分割
            for (int j = i - 1; j >= 0; j--) {

                // 取s[j, i - j] 子串
                // j取1？有意义，subStr代表s的第一个字符到第i个字符
                String subStr = s.substring(j, i);

                // 递推公式判断
                // 若前j个字符可分割，且s[j, i + 1)在dict中， 则s[i]能被合法分割
                // dp[j] 只会被计算一次，子问题只被解决一次
                if ( dp[j] && dict.contains(subStr) ) {
                    dp[i] = true;
                }
            }
        }

        // 返回结果
        return dp[len];
    }
}
