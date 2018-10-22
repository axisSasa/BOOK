package org.lium.algorithm.sort;

class ListNode {
      int val;
      ListNode next;
      ListNode(int x) {
          val = x;
          next = null;
      }
  }

public class MergeSortList {

    public static void main(String[] args) {
        // 建立List
        ListNode listNode = new ListNode(4);
        listNode.next = new ListNode(8);
        listNode.next.next = new ListNode(34);
        listNode.next.next.next = new ListNode(-5);

        MergeSortList mergeSortList = new MergeSortList();
        ListNode sortedList = mergeSortList.sortList(listNode);

        while (sortedList != null) {
            System.out.println(sortedList.val);
            sortedList = sortedList.next;
        }
    }

    /**
     * 题目解析：时间复杂度nlogn 可使用归并排序
     * 不直接在这个函数实现功能，是为了方便以后使用其他方式实现时，直接更改调用函数就好了
     */
    public ListNode sortList(ListNode head) {
        // 归并排序
        return mergeSortList(head);
    }

    /**
     * 归并排序思想 - 实现
     * 分割输入 - 快慢指针找中间节点
     * 针对 分割后的输入 分别排序 - 只剩一个节点时自然有序，返回
     * 针对排序后的 分割 做合并 - 新建节点，便于形成新的有序的链表
     */
    public ListNode mergeSortList(ListNode head) {
        // head为null时返回null-head, head == null
        // 只剩一个节点时自然有序，返回head: head != null && head.next == null
        // 结合上面两种情况，利用||的短路特性，合并这两个if语句
        if (head == null || head.next == null) {
            return head;
        }

        // 找到中间节点
        ListNode mid = findMidByFSPointer(head);

        ListNode left = head;
        ListNode right;
        // 针对分割出的右部分继续归并排序
        right = mergeSortList(mid.next);
        // 断掉左右部分联系
        mid.next = null;
        // 针对分割出的右部分继续归并排序
        left = mergeSortList(left);

        return mergeList(left, right);
    }
    /**
     * 快慢指针找中间节点【返回中间偏左 or 正中间】
     * FS: fast - slow
     */
    public ListNode findMidByFSPointer(ListNode head) {
        ListNode fast = head.next;
        ListNode slow = head;
        while( fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        // 如果 head为null - 返回 null
        // 如果 head只有一个节点 - 返回 head
        // 如果 head有两个以上节点 - 返回中间节点偏左
        return slow;
    }

    /**
     * 合并两个有序链表
     */
    public ListNode mergeList(ListNode left, ListNode right) {
        // 新建节点-保存合并后的链表
        ListNode result = new ListNode(0);
        // cursor指向新链表尾端节点
        ListNode cursor = result;

        // 当两个链表都有数据的情况
        while(left != null && right != null) {
            // <= 保持升序稳定性
            if (left.val <= right.val) {
                cursor.next = left;
                // 更新left
                left = left.next;
            } else {
                cursor.next = right;
                // 更新right
                right = right.next;
            }
            // 更新result
            cursor = cursor.next;
        }

        // left数据耗尽,将right剩余节点添加到result
        // right数据耗尽,将left剩余节点添加到result
        if (left == null) {
            cursor.next = right;
        } else if (right == null) {
            cursor.next = left;
        }
        // 返回新链表
        return result.next;
    }
}