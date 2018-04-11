package com.demo.leetCode.pratice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiang.gao on 2018/4/11.
 * project majorSpider
 * 给定两个非空链表来代表两个非负整数，位数按照逆序方式存储，它们的每个节点只存储单个数字。将这两数相加会返回一个新的链表。
 * <p>
 * 你可以假设除了数字 0 之外，这两个数字都不会以零开头。
 * <p>
 * 示例：
 * <p>
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 */
public class TwoNumberAdd {

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(9);
        l2.next = new ListNode(8);
        ListNode listNode = addTwoNumbers2(l1, l2);
        System.out.println(listNode);

    }


    /**
     * 另外一种尝试
     */
    public static ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        ListNode rs = new ListNode(-1);
        int next = 0;
        ListNode curr = rs;
        while (l1 != null || l2 != null) {
            int val1 = l1 == null ? 0 : l1.val;
            int val2 = l2 == null ? 0 : l2.val;
            int sum = val1 + val2 + next;
            next = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        if (next > 0) {
            curr.next = new ListNode(next);
        }
        return rs.next;
    }

    /**
     * 思路1 .取出两个链表数字,然后相加,组成链表
     */

    public static ListNode addTwoNumbers1(ListNode l1, ListNode l2) {
        List<Integer> n1 = getNumber(l1);
        List<Integer> n2 = getNumber(l2);
        int n1Size = n1.size();
        int n2Size = n2.size();
        int size = n1.size() > n2Size ? n1Size : n2Size;
        List<Integer> max = n1Size > n2Size ? n1 : n2;
        List<Integer> min = n1Size > n2Size ? n2 : n1;

        ListNode rs = null;
        ListNode last = null;
        int nextPost = 0;
        for (int i = 0; i < size + 1; i++) {
            int currVal;
            if (i < size) {
                Integer number1 = max.get(i);
                if (i < min.size()) {
                    Integer number2 = min.get(i);
                    int i1 = number1 + number2 + nextPost;
                    nextPost = i1 / 10;
                    currVal = i1 % 10;
                } else {
                    int i1 = number1 + nextPost;
                    currVal = i1 % 10;
                    nextPost = i1 / 10;
                }
            } else {
                currVal = nextPost;
                if (currVal <= 0) {
                    break;
                }
            }
            ListNode currNode = new ListNode(currVal);
            if (rs == null) {
                rs = currNode;
                last = currNode;
            } else {
                last.next = currNode;
                last = currNode;
            }

        }


        return rs;
    }


    private static List<Integer> getNumber(ListNode node) {
        List<Integer> rs = new ArrayList<>();
        while (node != null) {
            int val = node.val;
            node = node.next;
            rs.add(val);
        }
        return rs;
    }


    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
