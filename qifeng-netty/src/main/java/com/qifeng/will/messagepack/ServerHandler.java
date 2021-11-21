package com.hanxiaozhang.messagepack;

import java.util.List;

import io.netty.channel.ChannelHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.msgpack.MessagePack;
import org.msgpack.type.Value;

import io.netty.channel.ChannelHandlerContext;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author hanxinghua
 * @create 2020/10/21
 * @since 1.0.0
 */
@Slf4j
public class ServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        log.info("The Server channelRead msg:[ {} ]",msg);
        MessagePack messagePack = new MessagePack();
        byte[] write = messagePack.write(msg);
        User user = messagePack.read(write, User.class);
        log.info("msg --> User:[{}]",user);
        String str ="server accept success!";
        ctx.writeAndFlush(str);

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
