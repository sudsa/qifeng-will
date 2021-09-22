package com.hanxiaozhang.privateprotocol.server;


import com.hanxiaozhang.privateprotocol.constant.MessageType;
import com.hanxiaozhang.privateprotocol.message.Header;
import com.hanxiaozhang.privateprotocol.message.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 功能描述: <br>
 * 〈心跳检测请求响应Handler〉
 *
 * @Author:hanxinghua
 * @Date: 2020/10/23
 */
@Slf4j
public class HeartBeatRespHandler extends ChannelHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        NettyMessage message = (NettyMessage) msg;
        // 返回心跳应答消息
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.HEARTBEAT_REQ.value()) {
            log.info("Receive feign heart beat message : ---> " + message);
            NettyMessage heartBeat = buildHeatBeat();
            log.info("Send heart beat response message to feign : ---> " + heartBeat);
            ctx.writeAndFlush(heartBeat);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildHeatBeat() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.HEARTBEAT_RESP.value());
        message.setHeader(header);
        return message;
    }

}
