package com.demo.nio.main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by xiang.gao on 2017/12/26.
 * project majorSpider
 */
public class ServerSocketChannelBlockingMain {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        SocketAddress address = new InetSocketAddress(9999);
        serverSocketChannel.bind(address);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        SocketChannel socketChannel = serverSocketChannel.accept();
        int read;
        while ((read = socketChannel.read(buffer)) != -1) {
            String msg = "";
            System.out.println("Read " + read);
            buffer.flip();
            while (buffer.hasRemaining()) {
                msg += (char) buffer.get();
            }
            System.out.println(" i receive your msg : " + msg);
            buffer.clear();
        }
        serverSocketChannel.close();

    }
}
