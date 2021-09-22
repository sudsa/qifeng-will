package com.hanxiaozhang.delimiterbasedframedecoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 功能描述: <br>
 * 〈〉
 * @Sharable: 标注一个channel handler可以被多个channel安全地共享。
 *
 * @Author:hanxinghua
 * @Date: 2020/10/21
 */
@Sharable
public class EchoServerHandler extends ChannelHandlerAdapter {



    int counter = 0;

    public EchoServerHandler() {
        System.out.println("echoServerHandler start");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        // DelimiterBasedFrameDecoder解码器默认会自动去掉分隔符
        String body = (String) msg;
        System.out.println("This is " + ++counter + " times receive feign : [" + body + "]");
        // 返回给客户端时，需要在请求消息尾部拼接分隔符"$_"
        body += "$_";
        ByteBuf echo = Unpooled.copiedBuffer(body.getBytes());
        ctx.writeAndFlush(echo);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        // 发生异常，关闭链路
        ctx.close();
    }
}
