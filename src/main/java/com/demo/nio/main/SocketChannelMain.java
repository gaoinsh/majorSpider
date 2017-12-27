package com.demo.nio.main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by xiang.gao on 2017/12/27.
 * project majorSpider
 */
public class SocketChannelMain {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(9090));
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ);




    }
}
