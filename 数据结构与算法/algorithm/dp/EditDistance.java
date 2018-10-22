package org.lium.algorithm.dp;

public class EditDistance {
    public static void main(String[] args) {
        EditDistance editDistance = new EditDistance();
        int min = editDistance.minDistance("a", "b");
        System.out.println(min);
    }
    public int minDistance(String word1, String word2) {
        // 获取字符串长度 以便建立二维表
        int len1 = word1.length();
        int len2 = word2.length();

        // 建立dp表
        int[][] dp = new int[len1 + 1][len2 + 1];

        // 初始化表

        // 第一行为 空字符串 到 对应字符串 所需 转换步数
        //    # a
        // # 0 1
        // b 1 1
        for (int i = 0; i <= len2; i++) {
            dp[0][i] = i;
        }
        // 第一列为 空字符串 到 对应字符串 所需 转换步数
        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }

        // 比较 字符 填充dp表其余部分
        // 跳过第一行第一列：因为已经初始化填充了
        for (int i = 1; i <= len1; i++) { // word1
            for (int j = 1; j <= len2; j++) { // word2
                // i 索引对应值== j 索引对应值，F(i, j ) = F(i - 1, j - 1)
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // 其他情况
                    int temp = Math.min(dp[i - 1][j] , dp[i][j - 1]);
                    dp[i][j] = Math.min(temp, dp[i - 1][j - 1]) + 1;
                }
            }
        }

        return dp[len1][len2];
    }
}
