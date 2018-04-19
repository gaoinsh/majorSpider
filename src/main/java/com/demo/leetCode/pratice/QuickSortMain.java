package com.demo.leetCode.pratice;

/**
 * Created by xiang.gao on 2018/4/16.
 * project majorSpider
 */
public class QuickSortMain {

    public static void main(String[] args) {
        int[] arr = new int[]{9, 5, 14, 30, 2, 20, 7};
        quickSort(arr, 0, arr.length - 1);
        for (int a : arr) {
            System.out.print(a + " ");
        }
    }

    private static void quickSort(int[] a, int left, int right) {
        if (left > right) {
            return;
        }
        int i = left;
        int j = right;
        int base = a[left];
        while (i != j) {
            while (i < j && a[j] >= base) {
                j--;
            }
            while (i < j && a[i] <= base) {
                i++;
            }
            if (j > i) {
                int t = a[j];
                a[j] = a[i];
                a[i] = t;
            }

        }
        a[left] = a[i];
        a[i] = base;
        quickSort(a, left, i - 1);
        quickSort(a, i + 1, right);
    }
}
