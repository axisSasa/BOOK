package org.lium.algorithm.linkedlist;

    public class ReorderList {
        public void reorderList(ListNode head) {
            if (head == null) {
                return ;
            }
            // 查找中间节点
            ListNode mid = findMidNode(head);

            // 取中间节点后一个节点，记为afterMid
            // 设置中间节点mid的后继节点为空
            ListNode afterMid = mid.next;
            mid.next = null;

            // 翻转afterMid及之后的节点
            afterMid = reverseLinkedList(afterMid);

            //构造新的符合要求的链表
            ListNode mergeList = new ListNode(0);
            // 合成链表的尾节点
            ListNode mergeListTail = mergeList;
            // 链表长度上： head >= afterMid
            while(head != null) {
                // 分别取出两个链表的当前头节点
                ListNode temp1 = head;
                ListNode temp2 = afterMid;

                // 让下一个节点成为头节点
                head = head.next;
                // 同上，只是afterMid可能为空
                if (afterMid != null) {
                    afterMid = afterMid.next;
                }

                // 合并两个旧链表头节点到mergeListTail
                mergeListTail.next = temp1;
                temp1.next = temp2;
                // 防止链表总数为基数时，temp2可能为空的情况
                if (temp2 != null) {
                    temp2.next = null;
                }

                // 更新尾节点
                mergeListTail = temp2;
            }
            head = mergeList.next;
        }

        /**
         * 翻转链表
         */
        public static ListNode reverseLinkedList(ListNode head) {
            if (head == null) {
                return null;
            }
            ListNode newListHead = head;
            ListNode oldListHead = head;
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
            return newListHead;
        }

        /**
         * 总数奇数，返回恰好中间那个节点
         * 总数偶数，返回中间两个节点中偏左节点
         */
        public static ListNode findMidNode(ListNode head) {
            ListNode fast = head;
            ListNode slow = head;
            while(fast.next != null && fast.next.next != null) {
                fast = fast.next.next;
                slow = slow.next;
            }
            return slow;
        }
    }
