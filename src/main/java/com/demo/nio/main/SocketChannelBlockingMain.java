package com.demo.nio.main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by xiang.gao on 2017/12/27.
 * project majorSpider
 */
public class SocketChannelBlockingMain {

    public static void main(String[] args) throws IOException, InterruptedException {
        SocketChannel socketChannel = SocketChannel.open();
        SocketAddress address = new InetSocketAddress(9999);
        if (socketChannel.connect(address)) {
            ByteBuffer buffer = ByteBuffer.allocate(64);
            for (int i = 0; ; i++) {
                String msg = "this is " + i + " times request . ";
                buffer.put(msg.getBytes());
                buffer.flip();
                Thread.sleep(1000L);
                System.out.println(" put : " + msg);
                while (buffer.hasRemaining()) {
                    socketChannel.write(buffer);
                }
                buffer.clear();
            }
        }
        socketChannel.close();
    }
}
