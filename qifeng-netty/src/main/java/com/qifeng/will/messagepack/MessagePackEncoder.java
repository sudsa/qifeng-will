package com.hanxiaozhang.messagepack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈MessagePack 编码器〉
 *  Object --> ByteBuf
 *
 * @author hanxinghua
 * @create 2020/10/21
 * @since 1.0.0
 */
public class MessagePackEncoder extends MessageToByteEncoder<Object> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        MessagePack messagePack = new MessagePack();
        byte[] bytes = messagePack.write(msg);
        out.writeBytes(bytes);
    }
}
