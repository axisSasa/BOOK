package org.lium.algorithm.treeheap;

import java.util.*;

class WordNode{
    String word;
    int numSteps;

    public WordNode(String word, int numSteps){
        this.word = word;
        this.numSteps = numSteps;
    }
}
public class WordLadder {
    public static void main(String[] args) {
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("hot");
        hashSet.add("dog");
        hashSet.add("dot");
        int a = ladderLength("hot", "dog", hashSet);
    }
    public static int ladderLength(String start, String end, HashSet<String> dict) {
        // 将 start从dict中删除
        // 将end添加到dict中
        if (dict.contains(start)) {
            dict.remove(start);
        }
        if (!dict.contains(end)) {
            dict.add(end);
        }

        // 队列 维护中间过程的 字符串
        LinkedList<WordNode> queue = new LinkedList<>();
        // start 入队
        queue.add( new WordNode(start, 1) );

        // 遍历队列
        while( !queue.isEmpty() ) {
            // 出队
            WordNode topNode = queue.remove();
            // 匹配返回
            if (end.equals(topNode.word)) {
                return topNode.numSteps;
            }

            // 遍历
            char[] arr = topNode.word.toCharArray();
            for (int i = 0; i < arr.length; i++) {
                // 每次只改变一个字符
                char temp = arr[i];
                for(char j = 'a'; j <= 'z'; j++) {
                    // 没变化，继续执行无意义
                    if( j == temp ) {
                        continue;
                    }
                    arr[i] = j;
                    String newStart = new String(arr);
                    // 构造的字符串在dict中，加入队列
                    // 并在dict中删除该字符串，避免重复加入队列
                    if (newStart.equals("dog")) {
                        System.out.println("dog");
                    }
                    if (dict.contains(newStart)) {
                        queue.add( new WordNode(newStart, topNode.numSteps + 1) );
                        dict.remove(newStart);
                    }
                }
                arr[i] = temp;
            }
        }


        // 没有找到路径，返回0
        return 0;
    }
}
