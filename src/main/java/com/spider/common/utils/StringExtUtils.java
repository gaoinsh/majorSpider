package com.spider.common.utils;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * Created by xiang.gao on 2017/5/18.
 * project majorSpider
 */
public class StringExtUtils {

    public static String unescape(String str) {
        if (str == null) {
            return null;
        }
        return StringEscapeUtils.unescapeHtml(str.trim());
    }

    public static void main(String[] args) {
        System.out.println(unescape("DJ Khaled Featuring Justin Bieber, Quavo, Chance The Rapper &amp; Lil Wayne"));
    }
}
