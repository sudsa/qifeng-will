package com.hanxiaozhang.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author hanxinghua
 * @create 2020/10/26
 * @since 1.0.0
 */
public class TestServerHandler extends ChannelHandlerAdapter {


    public TestServerHandler() {
        // Server与Client建立一次链接，就实例化一次
        System.out.println("TestServerHandler start");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        System.out.println("The time server receive order : " + new String(req, "UTF-8"));
        ctx.writeAndFlush(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("-----------exceptionCaught--------------");
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("TestServerHandler.userEventTriggered");
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent)evt;
            if (event.state()== IdleState.READER_IDLE){
                System.out.println("TestServerHandler.userEventTriggered-->IdleStateEvent");
            }
        }
    }

}