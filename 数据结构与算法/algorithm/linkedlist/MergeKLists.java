package org.lium.algorithm.linkedlist;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class MergeKLists {
    public static void main(String[] args) {
        ArrayList<ListNode> lists = new ArrayList<>();

        lists.add(new ListNode(8));
        lists.add(new ListNode(9));
        lists.add(new ListNode(2));

        ListNode listNode = mergeKLists(lists);
        while ( listNode != null ) {
            System.out.println(listNode.val);
            listNode = listNode.next;
        }
    }
    public  static ListNode mergeKLists(ArrayList<ListNode> lists) {
        if(lists==null || lists.size() ==0)
            return null;

        // 初始化优先队列
        PriorityQueue<ListNode> queue = new PriorityQueue<ListNode>(new Comparator<ListNode>(){
            public int compare(ListNode l1, ListNode l2){
                // 最小堆
                return l1.val - l2.val;
            }
        });

        // 新建链表保存返回值
        ListNode result = new ListNode(0);
        ListNode cursor = result;

        // 添加链表头节点进入优先队列
        for(ListNode list : lists) {
            if (list != null) {
                queue.offer(list);
            }
        }
        // 从优先队列，取出队首节点
        // 队首节点比其他节点小 or 比其他节点大
        while(!queue.isEmpty()) {
            ListNode temp = queue.poll();
            cursor.next = temp;
            cursor = cursor.next;
            // 将队首节点的 下一个节点入队
            if (temp.next != null) {
                queue.offer(temp.next);
            }
        }
        // 返回排序后的链表
        return result.next;
    }
}
