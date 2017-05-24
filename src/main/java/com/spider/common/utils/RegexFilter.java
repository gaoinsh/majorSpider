package com.spider.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiang.gao on 2017/5/17.
 * project majorSpider
 */
public class RegexFilter {

    public static List<String> regexFilterList(String content, String regex) {
        if (regex == null || content == null) {
            return null;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        List<String> result = new ArrayList<String>();
        while (matcher.find()) {
            result.add(matcher.group(1));
        }
        return result;
    }

    public static String regexFilter(String content, String regex) {
        if (regex == null || content == null) {
            return null;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        String result = null;
        if (matcher.find()) {
            result = matcher.group(1);
        }
        return result;
    }

    public static boolean regexMatch(String content, String regex) {
        return regexMatch(content, regex, false);
    }

    public static boolean regexMatch(String content, String regex, boolean caseInsensitive) {
        if (regex != null && content != null) {
            Pattern pattern;
            if (caseInsensitive) {
                pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            } else {
                pattern = Pattern.compile(regex);
            }

            Matcher matcher = pattern.matcher(content);
            return matcher.find();
        } else {
            return false;
        }
    }
}
