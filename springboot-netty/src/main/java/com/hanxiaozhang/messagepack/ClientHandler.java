package com.hanxiaozhang.messagepack;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author hanxinghua
 * @create 2020/10/21
 * @since 1.0.0
 */
@Slf4j
public class ClientHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        log.info("The Client channelRead msg:[ {} ]",msg);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 激活通道
     * 客户端与服务端建立链接成功后，调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        User user = null;
        for (int i = 0; i < 2; i++) {
            user = new User();
            user.setName("hanxinghua");
            user.setAge(i);
            ctx.write(user);
        }
        ctx.flush();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
