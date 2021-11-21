package com.hanxiaozhang.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 * 这里重点关注channelActive、channelRead、exceptionCaught三个方法
 *
 * @author hanxinghua
 * @create 2020/11/17
 * @since 1.0.0
 */
@Slf4j
public class TestClientOtherHandler extends ChannelHandlerAdapter {

    public TestClientOtherHandler() {
        System.out.println("TestClientOtherHandler start");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("call TestClientOtherHandler.channelActive()");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    }

    /**
     * 发生异常时，打印异常日志，释放客户端资源
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 释放资源
        log.warn("Unexpected exception from downstream : " + cause.getMessage());
        ctx.close();
    }
}