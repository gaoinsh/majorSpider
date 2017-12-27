package com.demo.nio.main;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by xiang.gao on 2017/12/26.
 * project majorSpider
 */
public class BIOMain {

    @Test
    public void read() throws IOException {
        FileInputStream fis = new FileInputStream("D:\\BugReport.txt");

        byte[] buffer = new byte[1024];
        int len;
        while ((len = fis.read(buffer)) != -1) {
            System.out.println(new String(buffer, 0, len));
        }

    }

}
