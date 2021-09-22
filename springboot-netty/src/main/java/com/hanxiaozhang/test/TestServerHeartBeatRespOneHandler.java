package com.hanxiaozhang.test;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author hanxinghua
 * @create 2020/10/26
 * @since 1.0.0
 */
public class TestServerHeartBeatRespOneHandler extends ChannelHandlerAdapter {


    public TestServerHeartBeatRespOneHandler() {
        // Server与Client建立一次链接，就实例化一次
        System.out.println("TestServerHeartBeatRespOneHandler start");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("================TestServerHeartBeatRespOneHandler.userEventTriggered");
        // 传递给下一个handle的UserEventTriggered方法
//        ctx.fireUserEventTriggered(evt);
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent)evt;
            if (event.state()== IdleState.READER_IDLE){
                System.out.println("TestServerHeartBeatRespOneHandler.userEventTriggered-->IdleStateEvent");
            }
        }
    }

}