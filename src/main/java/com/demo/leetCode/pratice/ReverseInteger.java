package com.demo.leetCode.pratice;

/**
 * Created by xiang.gao on 2018/4/11.
 * project majorSpider
 * 给定一个范围为 32 位 int 的整数，将其颠倒。
 */
public class ReverseInteger {

    public static void main(String[] args) {
        int reverse = reverse2(-44123);
        System.out.println(reverse);

    }

    public static int reverse(int x) {
        String num = x + "";
        char[] chars = num.toCharArray();
        int negative = 1;
        char[] rs = new char[chars.length];
        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];
            if (i == 0 && aChar == '-') {
                negative = 0;
                rs[0] = '-';
            } else {
                rs[chars.length - i - negative] = aChar;
            }
        }


        Long l = new Long(new String(rs));
        if (l >= Integer.MAX_VALUE || l <= Integer.MIN_VALUE) {
            return 0;
        }
        return l.intValue();
    }


    /**
     * 标准答案
     */
    public static int reverse2(int x) {
        int result = 0;
        while (x != 0) {

            if (result > Integer.MAX_VALUE / 10 || result < Integer.MIN_VALUE / 10)
                return 0;
            result = result * 10 + x % 10;
            x = x / 10;
        }
        return result;
    }
}
