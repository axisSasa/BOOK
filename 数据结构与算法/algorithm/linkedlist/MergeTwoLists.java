package org.lium.algorithm.linkedlist;


public class MergeTwoLists {

    public ListNode mergeTwoLists(ListNode listOne, ListNode listTwo) {
        ListNode cursor = new ListNode(0);
        ListNode result = cursor;
        while( listOne != null || listTwo != null) {
            // 两个链表都健在
            if (listOne != null && listTwo != null) {
                if (listOne.val > listTwo.val) {
                    cursor.next = listTwo;
                    listTwo = listTwo.next;
                } else {
                    cursor.next = listOne;
                    listOne = listOne.next;
                }
                cursor = cursor.next;
            } else if ( listOne == null) {
                // 链表用完了
                cursor.next = listTwo;
                break;
            } else if (listTwo == null) {
                cursor.next = listOne;
                break;
            }
        }
        // 返回结果
        return result.next;
    }
}