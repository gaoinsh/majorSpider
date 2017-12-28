package com.demo.nio.main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by xiang.gao on 2017/12/27.
 * project majorSpider
 */
public class ClientNonBlockingMain {

    public static void main(String[] args) throws IOException, InterruptedException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(9090));
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);


        boolean flag = true;
        while (socketChannel.finishConnect()) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isReadable()) {
                    System.out.println("ready to read  ... ");
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    socketChannel.read(buffer);
                    String msg = "";
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        msg += (char) buffer.get();
                    }
                    buffer.clear();
                    System.out.println("receive msg : " + msg);
                } else if (selectionKey.isWritable()) {
                    System.out.println(" ready to write .. ");
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    buffer.put("hello, this is socket channel .. ".getBytes());
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        socketChannel.write(buffer);
                    }
                    buffer.clear();
                }
                iterator.remove();
            }
            Thread.sleep(1000);
        }

    }
}
