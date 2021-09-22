package com.hanxiaozhang.linebasedframedecoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.logging.Logger;

/**
 * 功能描述: <br>
 * 〈〉
 *
 * @Author:hanxinghua
 * @Date: 2020/10/21
 */
@Slf4j
public class TimeClientHandler extends ChannelHandlerAdapter {


    private int counter;

    private byte[] req;

    /**
     * Creates a feign-side handler.
     */
    public TimeClientHandler() {
        req = ("QUERY TIME ORDER" + System.getProperty("line.separator"))
                .getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ByteBuf message = null;
        for (int i = 0; i < 100; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        String body = (String) msg;
        System.out.println("Now is : " + body + " ; the counter is : "
                + ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 释放资源
        log.warn("Unexpected exception from downstream : "
                + cause.getMessage());
        ctx.close();
    }
}
