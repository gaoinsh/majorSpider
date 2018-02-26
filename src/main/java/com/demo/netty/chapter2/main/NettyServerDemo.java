package com.demo.netty.chapter2.main;

import com.demo.netty.chapter2.handler.ServerChannelHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by xiang.gao on 2018/2/13.
 * project majorSpider
 */
public class NettyServerDemo {

    public static void main(String[] args) {
        ServerBootstrap startBoot = new ServerBootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        startBoot.group(group).channel(NioServerSocketChannel.class).localAddress(8090).childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ServerChannelHandler());
            }
        });
        try {
            ChannelFuture future = startBoot.bind().sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                group.shutdownGracefully().sync();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }
}
