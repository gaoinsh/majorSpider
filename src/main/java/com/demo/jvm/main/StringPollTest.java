package com.demo.jvm.main;

/**
 * Created by xiang.gao on 2018/2/26.
 * project majorSpider
 * 字符串常量池
 * intern() 如果字符串常量池中已经包含一个等于此String对象的字符串,则返回此对象的引用;
 * 否则将此String对象包含的字符串添加到常量池中,并返回此对象的引用;
 */
public class StringPollTest {

    public static void main(String[] args) {

        //jdk1.7 intern实现不再复制实例到字符串常量池中,只是在常量池中记录首次出现的实例引用
        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);

        String str3 = new String("字符串");
        System.out.println(str3.intern() == str3);
    }
}
