package com.demo.leetCode.pratice;

/**
 * Created by xiang.gao on 2018/4/12.
 * project majorSpider
 * 合并两个已排序的链表，并将其作为一个新列表返回。新列表应该通过拼接前两个列表的节点来完成。
 * <p>
 * 示例：
 * <p>
 * 输入：1->2->4, 1->3->4
 * 输出：1->1->2->3->4->4
 */
public class MergeTwoSortedList {

    public static void main(String[] args) {
        ListNode l1 = new ListNode(5);

        ListNode l2 = new ListNode(3);
        l2.next = new ListNode(12);
        ListNode listNode = mergeTwoLists2(l1, l2);
        while (listNode != null) {
            System.out.print(listNode.val + " - >");
            listNode = listNode.next;
        }
    }

    /**
     * 1->1->2->3->4->4
     */
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        ListNode head = new ListNode(-1);
        head.next = l1;

        ListNode head1 = head;

        while (l2 != null) {
            int val2 = l2.val;
            //找到要插入的前面一个node
            while (head1.next != null && head1.next.val <= val2) {
                head1 = head1.next;
            }

            //一直查到结尾都没有找到匹配的位置,因为是有序链表,后面的元素也不需要继续匹配
            if (head1.next == null) {
                head1.next = l2;
                break;
            }
            //保存下一个node
            ListNode p = l2.next;
            l2.next = head1.next;
            head1.next = l2;

            l2 = p;
        }
        return head.next;
    }

    public static ListNode mergeTwoLists2(ListNode list1, ListNode list2) {
        if (list1 == null) return list2; //判断到某个链表为空就返回另一个链表。如果两个链表都为空呢？没关系，这时候随便返回哪个链表，不也是空的吗?
        if (list2 == null) return list1;
        ListNode list0 = null;//定义一个链表作为返回值
        if (list1.val < list2.val) {//判断此时的值，如果list1比较小，就先把list1赋值给list0，反之亦然
            list0 = list1;
            list0.next = mergeTwoLists(list1.next, list2);//做递归，求链表的下一跳的值
        } else {
            list0 = list2;
            list0.next = mergeTwoLists(list1, list2.next);
        }
        return list0;
    }


    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

}
