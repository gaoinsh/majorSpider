package com.demo.nio.main;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by xiang.gao on 2017/12/26.
 * project majorSpider
 */
public class FileChannelMain {

    @Test
    public void read() throws IOException {
        RandomAccessFile file = new RandomAccessFile("D:\\BugReport.txt", "rw");
        FileChannel channel = file.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(1000);
        int bytesRead;
        while ((bytesRead = channel.read(buf)) != -1) {

            System.out.println("Read " + bytesRead);
            //读写转换
            buf.flip();

            while (buf.hasRemaining()) {
                System.out.print((char) buf.get());
            }

            buf.clear();
        }
    }


    @Test
    public void write() throws IOException {
//        FileChannel channel = new FileInputStream("D:\\NIO.txt").getChannel();  //fileInputStream是只读的
        RandomAccessFile file = new RandomAccessFile("D:\\BugReport.txt", "rw");
        FileChannel channel = file.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("this is nio".getBytes());
        buffer.flip();
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
        buffer.clear();
        channel.close();
    }
}
