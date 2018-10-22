package org.lium.algorithm.sort;

public class InsertionSortList {

    public static void main(String[] args) {
        ListNode listNode = new ListNode(2);
        listNode.next = new ListNode(1);
        InsertionSortList insertionSortList = new InsertionSortList();
        ListNode sortedList = insertionSortList.insertionSortList(listNode);
        while (sortedList != null) {
            System.out.println(sortedList.val);
            sortedList = sortedList.next;
        }
    }

    /**
     * 题目解析：插入排序
     * 解题分析：
     * 简单解法：可以用额外内存来保存结果
     * in-place解法：每次移动节点,若链表有序，则不用移动只比较一次
     */
    public ListNode insertionSortList(ListNode head) {
        if( head == null || head.next == null) {
            return head;
        }
        // 链表不好处理，转为数组
        int[] arr = linkedList2IntArray(head);

        int len = arr.length;

        // 对于当前节点，移动形成左边的节点都小于它，右边的节点偶大于它
        for(int i = 1; i < len; i++){
            int temp = arr[i];
            // 当前节点左边第一个节点
            int j = i - 1;
            // 左边大于当前节点的都要右移
            while( j >= 0 && arr[j] > temp ){
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            // 当前节点放置到最后一个右移走的元素的位置
            arr[j + 1] = temp;
        }

        // 数组转List
        return IntArray2LinkedList(arr);
    }

    public int[] linkedList2IntArray(ListNode head) {
        int len = 0;
        ListNode pHead = head;
        while( pHead != null ) {
            len += 1;
            pHead = pHead.next;
        }
        // 建立数组
        int[] arr = new int[len];
        // 填充数组
        for(int i = 0; i < len; i++) {
            arr[i] = head.val;
            head = head.next;
        }

        // 返回数组
        return arr;
    }

    public ListNode IntArray2LinkedList(int[] arr) {
        ListNode result = new ListNode(0);
        ListNode cursor = result;
        for( int i = 0; i < arr.length; i++ ) {
            cursor.next = new ListNode(arr[i]);
            cursor = cursor.next;
        }
        // 返回链表
        return result.next;
    }
}