package com.demo.netty.chapter5.ByteBufferDemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * Created by xiang.gao on 2018/2/13.
 * project majorSpider
 */
public class CompositeByteBufDemo {

    public static void main(String[] args) {
        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();
        ByteBuf header = Unpooled.copiedBuffer("hello ,this is head ... ".getBytes(CharsetUtil.UTF_8));
    }
}
