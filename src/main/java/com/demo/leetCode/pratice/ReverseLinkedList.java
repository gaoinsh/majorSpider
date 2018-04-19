package com.demo.leetCode.pratice;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xiang.gao on 2018/4/17.
 * project majorSpider
 */
public class ReverseLinkedList {

    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.get("hello");
    }

    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        while (head != null) {
            ListNode tmp = head.next;
            head.next = prev;
            prev = head;
            head = tmp;
        }

        return prev;
    }

    public void reverseList2(ListNode head, ListNode prev) {


    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
