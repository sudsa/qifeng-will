package com.qifeng.will.base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandlerInvoker;
import io.netty.util.concurrent.EventExecutorGroup;
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
public class TimeClientHandler extends ChannelHandlerAdapter {


    /**
     * 计数器
     */
    private  int counter;

    private byte[] req;

    /**
     * Creates a feign-side handler.
     */
    public TimeClientHandler() {
        req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
    }

    /**
     * 当客户端和服务端TCP链路建立成功之后，Netty的NIO会调用该方法，发送查询时间的指令给服务端
     *
     * 连续发送100次查询时间的命令
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ByteBuf message=null;
        for (int i = 0; i <100 ; i++) {
            message=Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    /**
     * 当服务端返回应答信息时，channelRead方法被调用
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println("Now is : " + body+"; the counter is :"+ ++counter);
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
        log.warn("Unexpected exception from downstream : "
                + cause.getMessage());
        ctx.close();
    }
}