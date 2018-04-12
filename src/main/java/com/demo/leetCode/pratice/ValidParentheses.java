package com.demo.leetCode.pratice;

/**
 * Created by xiang.gao on 2018/4/11.
 * project majorSpider
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 * <p>
 * 括号必须以正确的顺序关闭，"()" 和 "()[]{}" 是有效的但是 "(]" 和 "([)]" 不是。
 */
public class ValidParentheses {

    public static void main(String[] args) {
        System.out.println(isValid2("()[]{}"));

    }

    public static boolean isValid2(String s) {
        char[] chars = s.toCharArray();

        StackEntry top = null;
        for (char c : chars) {
            if (c == '(' || c == '[' || c == '{') {
                StackEntry stackEntry = new StackEntry(c);
                stackEntry.next = top;
                top = stackEntry;
            } else if (c == ')') {
                if (top == null) {
                    return false;
                }
                if (top.val == '(') {
                    top = top.next;
                } else {
                    return false;
                }
            } else if (c == '}') {
                if (top == null) {
                    return false;
                }
                if (top.val == '{') {
                    top = top.next;
                } else {
                    return false;
                }
            } else if (c == ']') {
                if (top == null) {
                    return false;
                }
                if (top.val == '[') {
                    top = top.next;
                } else {
                    return false;
                }
            }
        }
        return top == null;
    }


    public static class StackEntry {
        public char val;
        public StackEntry next;

        public StackEntry(char val) {
            this.val = val;
        }
    }


    /**
     * 失败.......
     */
    public static boolean isValid(String s) {
        char[] chars = s.toCharArray();
        if (chars.length == 0) {
            return true;
        }
        char start = s.charAt(0);
        char end = getEnd(start);
        int endIndex = s.lastIndexOf(end, s.length());
        while (endIndex % 2 == 0) {
            endIndex = s.lastIndexOf(end, endIndex - 1);
        }
        if (endIndex < 0) {
            return false;
        }
        if (endIndex == 1 && s.length() == 2) {
            return true;
        }
        boolean valid1 = true;
        //中间段还有字符串
        if (endIndex - 1 > 0) {
            String part1 = s.substring(1, endIndex);
            valid1 = isValid(part1);
        }
        boolean valid2 = true;
        //第二段
        if (endIndex < s.length() - 1) {
            String part2 = s.substring(endIndex + 1);
            valid2 = isValid(part2);
        }
        return valid1 && valid2;
    }

    private static char getEnd(char start) {
        char end = 0;
        switch (start) {
            case '(':
                end = ')';
                break;
            case '[':
                end = ']';
                break;
            case '{':
                end = '}';
                break;

        }
        return end;
    }


}
