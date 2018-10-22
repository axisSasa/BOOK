package org.lium.algorithm.treeheap;
class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; next = null; }
  }

public class SortedListToBST {
    /**
     * 将链表转换为数组， 方便使用
     */
    public static int[] listNode2IntArray(ListNode head) {
        // 获取链表长度，建立数组
        int len = getLinkedListLength(head);
        int[] arr = new int[len];

        // head不越界
        for( int i = 0; i < len; i++ ) {
            arr[i] = head.val;
            head = head.next;
        }

        // 返回数组
        return arr;
    }

    /**
     * 获取链表长度
     */
    public static int getLinkedListLength(ListNode head) {
        int length = 0;
        while( head != null ) {
            length += 1;
            head = head.next;
        }
        return length;
    }

    /**
     * 递归建立根节点
     */
    public static TreeNode sortedListToBST(ListNode head) {
        // 处理空链表
        if( head == null ) {
            return null;
        }
        int[] num = listNode2IntArray(head);
        return sortedArrayToBST(num, 0, num.length - 1);
    }

    /**
     * 递归建立根节点
     */
    public static TreeNode sortedArrayToBST(int[] num, int startIndex, int endIndex) {
        // 处理异常
        if ( startIndex > endIndex ) {
            return null;
        }
        // 取中间节点
        // 若和为偶数【两索引间有奇数个数】，则mid恰为startIndex, endIndex 中间节点
        // 若和为奇数【两索引间有偶数个数】，则mid为startIndex, endIndex 中间偏节右点
        int temp = startIndex + endIndex;
        int mid = temp / 2 + temp % 2;

        // 构造根节点
        TreeNode root = new TreeNode( num[mid] );

        // 递归处理 左右子节点
        root.left = sortedArrayToBST( num, startIndex, mid - 1 );
        root.right = sortedArrayToBST( num, mid + 1, endIndex );

        // 返回根节点
        return root;
    }
}