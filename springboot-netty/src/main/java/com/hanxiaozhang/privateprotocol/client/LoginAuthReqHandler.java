package com.hanxiaozhang.privateprotocol.client;

import com.hanxiaozhang.privateprotocol.constant.MessageType;
import com.hanxiaozhang.privateprotocol.message.Header;
import com.hanxiaozhang.privateprotocol.message.NettyMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 功能描述: <br>
 * 〈〉
 *
 * @Author:hanxinghua
 * @Date: 2020/10/26
 */
@Slf4j
public class LoginAuthReqHandler extends ChannelHandlerAdapter {



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(buildLoginReq());
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        NettyMessage message = (NettyMessage) msg;

        // 如果是握手应答消息，需要判断是否认证成功
        if (message.getHeader() != null
                && message.getHeader().getType() == MessageType.LOGIN_RESP
                .value()) {
            byte loginResult = (byte) message.getBody();
            if (loginResult != (byte) 0) {
                // 握手失败，关闭连接
                ctx.close();
            } else {
                log.info("Login is ok : " + message);
                ctx.fireChannelRead(msg);
            }
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildLoginReq() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_REQ.value());
        message.setHeader(header);
        return message;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.fireExceptionCaught(cause);
    }
}
