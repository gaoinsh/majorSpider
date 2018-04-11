package com.demo.leetCode.pratice;

/**
 * Created by xiang.gao on 2018/4/11.
 * project majorSpider
 * 判断一个整数是否是回文数
 * 思路是对的,但是负数不需要考虑,不需要循环两遍,直接reverse看是否和原数值相等即可
 */
public class palindromeNumber {

    public static void main(String[] args) {
        System.out.println(isPalindrome(-2147447412));
    }

    public static boolean isPalindrome(int x) {
        int pos = 0;
        int j = x;
        if (x < 0) {
            return false;
        }
        //几位数
        while (j != 0) {
            j = j / 10;
            pos++;
        }
        int half = pos / 2;
        //12345
        int reverse = 0;
        int currPost = 1;
        int result = 0;
        while (x != 0) {
            int last = x % 10;
            reverse = reverse * 10 + last;
            if (currPost++ == half) {
                result = reverse;
            }
            x = x / 10;
        }

        //54321
        int i = 1;
        int result2 = 0;
        while (i++ <= half) {
            result2 = result2 * 10 + reverse % 10;
            reverse = reverse / 10;
        }

        return result2 == result;
    }
}
