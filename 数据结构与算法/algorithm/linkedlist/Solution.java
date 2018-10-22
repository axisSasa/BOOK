package org.lium.algorithm.linkedlist;

import java.util.ArrayList;
import java.util.List;

class ListNode {
     int val;
     ListNode next;
  ListNode(int x) {
      val = x;
       next = null;
     }
 }

public class Solution {
     public static void main(String[] args) {
         ListNode listNode1 = new ListNode(1);
         listNode1.next = new ListNode(8);
         ListNode listNode2 = new ListNode(0);
         ListNode a = addTwoNumbers(listNode1, listNode2);

         a.next.next = new ListNode(9);
         a.next.next.next = new ListNode(7);
         linkedListReverse(a);
     }

    public ListNode deleteDuplicates(ListNode head) {
        // 因为链表已经排序，所以重复值总是相邻的
        ListNode cursor = head;
        while(cursor != null){
            // 如果下一个节点重复，则删除下一个节点
            // 下一个节点不重复，移动光标
            if (cursor.next != null && cursor.val == cursor.next.val) {
                cursor.next = cursor.next.next;
            } else {
                cursor = cursor.next;
            }
        }
        return head;
    }

     public static void linkedListReverse(ListNode head) {
         ListNode newListHead = head;
         ListNode oldListHead = head;
         printLinkedList(head);
         while ( oldListHead != null ) {
             // 存放旧链表下一个节点
             ListNode temp = oldListHead.next;
             // 旧链表首节点 指向 新链表
             oldListHead.next = newListHead;
             // 让旧链表首节点称为 新链表新首节点
             newListHead = oldListHead;
             // 更新旧链表首节点
             oldListHead = temp;
         }
         head.next = null;
         // 打印链表
         printLinkedList(newListHead);
     }
     public static void printLinkedList(ListNode head) {
         String result = "";
         while(head != null) {
             result += head.val;
             head = head.next;
         }
         System.out.println(result);
     }
    public static ListNode addTwoNumbers(ListNode listNode1, ListNode listNode2) {
        // 确保至少有一个节点
        if (listNode1 == null) {
            listNode1 = new ListNode(0);
        }
        if (listNode2 == null) {
            listNode2 = new ListNode(0);
        }
        int temp = 0;
        ListNode listNode = new ListNode(0);
        ListNode result = listNode;
        // 遍历节点
        while( listNode1 != null || listNode2 != null ) {
            if ( listNode1 != null ) {
                temp += listNode1.val;
                listNode1 = listNode1.next;
            }
            if ( listNode2 != null ) {
                temp += listNode2.val;
                listNode2 = listNode2.next;
            }
            listNode.next = new ListNode(temp % 10);
            listNode = listNode.next;
            // 下次是否进位
            temp = temp >= 10 ? 1 : 0;
        }
        // 处理最后的进位
        if (temp == 1) {
            listNode.next = new ListNode(1);
        }
        // 返回结果
        return result.next;
    }
}
