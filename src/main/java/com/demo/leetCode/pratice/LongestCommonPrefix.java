package com.demo.leetCode.pratice;

/**
 * Created by xiang.gao on 2018/4/11.
 * project majorSpider
 */
public class LongestCommonPrefix {

    public static void main(String[] args) {
        String[] strs = new String[2];
        strs[0] = "a";
        strs[1] = "b";
        System.out.println(longestCommonPrefix(strs));

    }


    /**
     * abcd
     * <p>
     * abce
     * <p>
     * abed
     */
    public static String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        String template = strs[0];
        for (int i = 1; i < strs.length; i++) {
            int size = template.length();

            while (size > 0) {
                if (size <= strs[i].length()) {
                    String subStr = strs[i].substring(0, size);
                    if (subStr.equals(template)) {
                        break;
                    }
                }
                size--;
                template = template.substring(0, size);
            }
        }
        return template;
    }
}
