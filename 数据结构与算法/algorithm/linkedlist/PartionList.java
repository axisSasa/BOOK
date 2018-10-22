package org.lium.algorithm.linkedlist;


public class PartionList {
    public ListNode partition(ListNode head, int x) {
        if (head == null || head.next == null) {
            return head;
        }
        // 新建链表
        ListNode rightHead = new ListNode(0);
        ListNode leftHead = new ListNode(0);
        // 新建链表光标
        ListNode cursorL = leftHead;
        ListNode cursorR = rightHead;
        while( head != null ) {
            if (head.val >= x) {
                // 取出head来构造大于x的链表
                cursorR.next = head;
                cursorR = cursorR.next;
            } else {
                cursorL.next = head;
                cursorL = cursorL.next;
            }
            // 无论怎样都要更新head的光标
            head = head.next;
        }
        // 即将合成的链表最后一个节点不能再指向任何其他节点
        cursorR.next = null;

        // 拼接leftHead rightHead
        if (leftHead.next != null) {
            cursorL.next = rightHead.next;
        } else {
            return rightHead.next;
        }
        return leftHead.next;
    }
}
