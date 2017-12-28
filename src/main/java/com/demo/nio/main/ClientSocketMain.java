package com.demo.nio.main;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by xiang.gao on 2017/12/28.
 * project majorSpider
 */
public class ClientSocketMain {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(9090));

        OutputStream os = socket.getOutputStream();
        os.write("hello this is socket ...".getBytes());

        os.close();
        socket.close();

    }
}
