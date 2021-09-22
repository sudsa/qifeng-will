package com.hanxiaozhang.base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandlerInvoker;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *  TimeServerHandler继承自ChannelHandlerAdapter,它用于对网络事件进行读写操作，
 *  通常我们只需要关注channelRead和exceptionCaught方法
 *
 *  模拟TCP粘包导致功能异常
 *  做法：每读到一条消息后，就计一次数，然后发送应答消息给客户端。安装设计，服务端接受到
 *  的消息总数应该跟客户端发送的消息总数相同。而且请求消息删除回车换行符后应该为“QUERY TIME ORDER"
 *
 * @author hanxinghua
 * @create 2020/10/20
 * @since 1.0.0
 */
public class TimeServerHandler  extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        // 将msg转换成Netty的ByteBuffer对象，它类似于JDK中的java.nio.ByteBuffer对象。
        ByteBuf buf = (ByteBuf) msg;
        // 创建一个"可读字节数"大小的数组
        byte[] req = new byte[buf.readableBytes()];
        // 将缓冲区中的字节数组复制到新建的byte数组中
        buf.readBytes(req);
        String body = new String(req, "UTF-8").substring(0,req.length-System.getProperty("line.separator").length());
        System.out.println("The time server receive order : " + body);
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new java.util.Date(
                System.currentTimeMillis()).toString() : "BAD ORDER";
        currentTime = currentTime + System.getProperty("line.separator");

        // 将String转为ByteBuf对象
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        // 通过ChannelHandlerContext的write方法起步发送应答消息给客户端
        ctx.write(resp);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 将消息发送队列中的消息写入到SocketChannel中发送给对方。
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 发生异常是，关闭ChannelHandlerContext,释放和ChannelHandlerContext相关关联的句柄等资源
        ctx.close();
    }
}