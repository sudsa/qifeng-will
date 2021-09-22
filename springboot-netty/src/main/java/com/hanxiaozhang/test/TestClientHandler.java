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
 * @create 2020/10/20
 * @since 1.0.0
 */
@Slf4j
public class TestClientHandler extends ChannelHandlerAdapter {

    private byte[] req;


    public TestClientHandler() {
        // client启动一次实例化一次
        System.out.println("TestClientHandler start");
        req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("call TestClientHandler.channelActive()");
        ByteBuf message=null;
        for (int i = 0; i <10 ; i++) {
            message=Unpooled.buffer(req.length);
            ctx.writeAndFlush(message.writeBytes(req));
        }
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