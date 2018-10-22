package org.lium.algorithm.dp;

public class LongestPalindromeSubstring {
    public static void main(String[] args) {
        LongestPalindromeSubstring obj = new LongestPalindromeSubstring();
        String palindromeSubstring = obj.longestPalindrome("dfdabcba");
        System.out.println(palindromeSubstring);
    }
    /**
     * 简单处理，可以根据回文串的定义，对每个节点做处理
     * bab : i从 1开始拓展 j = i - 1; k = i + 1; s[j ]== s[k] 时 j-- ;k++;
     * bb:  i从0 开始拓展 j = i - 1;k = i + 1; [j ]== s[k] 时 j-- ;k++;
     */
    /**
     * 题目分析：给定字符串S,且s最大长度为1000，找s中最长的回文字符串
     * 回文字符串：abcba,即：从左往右遍历 = 从右往左遍历
     * 解题分析：
     *  i, j为字符串的下标索引
     * dp[i] [j]代表字符串 从 i 到 j 是不是回文字符串
     * 若 dp[i + 1] [j - 1]  == 1 && s[i]== s[j]，有 dp[i] [j] == 1
     * 举例 bcb 是回文的 a bdc a也是回文的
     * abcba详解：
     *     a b c b a
     *     0 1 2 3 4
     *  0 1 1 0 0 1
     *  1 0 1 1 0 0
     *  2 0 0 1 1 0
     *  3 0 0 0 1 0
     *  4 0 0 0 0 1
     **/
    public String longestPalindrome(String s) {
        if (s == null || s.length() <= 1) {
            return s;
        }

        // 获取字符串长度
        int len = s.length();

        // 创建二维数组保存 是否为回文
        // true为 回文； false为非回文
        boolean[][] dp = new boolean[len][len];

        // 遍历字符串填充dp表，查找最大长度的回文字符串
        int longestLen = 1;
        String result =  null;
        for (int num = 0; num < len; num++) {// 子字符串字符串数量
            // 注意防止数组访问越界
            for (int i = 0; i < len - num; i++) {// 子字符串 起始索引
                // 子字符串 结束索引
                int j = i + num;

                // num= 0 时，i,j为同一字符串，天然回文
                // num= 1 时，ij中间无字符串, 天然回文
                // num= 2 时，ij中间只有一个字符串, 天然回文
                // 根据递归推导：i,j中间为回文，且 s[i] == s[j],则dp[i] [j] == 1
                // j - i <= 2 在前，利用短路防止数组访问越界
                if ( s.charAt(i) == s.charAt(j) && (num <= 2  || dp[i + 1] [j - 1]) ) {
                    // 当前ij构成回文字符串
                    dp[i][j] = true;

                    // 更新最长回文字符串长度
                    // 更新最长回文字符串
                    if ( num + 1 > longestLen ) {
                        longestLen =  num + 1;
                        result = s.substring(i, j + 1);
                    }
                }
            }
        }

        // 返回最长回文字符串
        return result;
    }
}
