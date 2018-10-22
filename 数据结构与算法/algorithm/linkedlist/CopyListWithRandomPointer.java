package org.lium.algorithm.linkedlist;

class RandomListNode {
    int label;
    RandomListNode next, random;
    RandomListNode(int label) {
        this.label = label;
    }
}
public class CopyListWithRandomPointer {
    public RandomListNode copyRandomList(RandomListNode head) {
        if (head == null) {
            return head;
        }
        // 复制节点（除random外的数据）保存在元节点后
        // 这样做是为了方便深复制random数据
        RandomListNode cursor = head;
        while(cursor != null) {
            // 当前节点的拷贝节点
            RandomListNode copy = new RandomListNode(cursor.label);

            // 插入拷贝节点在当前节点后
            copy.next = cursor.next;
            cursor.next = copy;

            // 更新当前节点
            cursor = copy.next;
        }

        // 复制random
        // 复制的节点的random 应该指向原节点的random指向的节点的下一个节点
        cursor = head;
        while(cursor != null) {
            // 取出复制节点
            RandomListNode copy = cursor.next;
            // 给random赋值
            if (cursor.random != null) {
                copy.random = cursor.random.next;
            }
            // 更新cursor
            cursor = copy.next;
        }

        // 至此，链表已完成深复制，只是复制的数据还在原链表上
        // 拆分链表，取出深复制的节点形成新链表
        cursor = head;
        RandomListNode copy = head.next;
        // 让head指向新的链表头节点，方便作为返回值
        // Java的传参是传递值，所以参数head只是一个地址
        // 该地址指向哪里不影响原链表
        // 只有该地址指向的数据发生变化才影响原链表
        // 所以这里需要恢复原链表cursor
        head = copy;
        while(cursor != null) {
            // 恢复原链表
            // 构造新链表
            // 只要有cursor就一定有copy
            cursor.next = copy.next;
            if (copy.next != null) {
                copy.next = copy.next.next;
            }
            // 更新cursor,copy
            cursor = cursor.next;
            copy = copy.next;
        }
        return head;
    }
}
