package com.hanxiaozhang.messagepack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;


/**
 * 〈一句话功能简述〉<br>
 * 〈MessagePack 解码器〉
 * ByteBuf --> Object
 *
 * @author hanxinghua
 * @create 2020/10/21
 * @since 1.0.0
 */
public class MessagePackDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {

        int length = msg.readableBytes();
        byte[] array = new byte[length];
        msg.getBytes(msg.readerIndex(), array, 0, length);
        MessagePack messagePack = new MessagePack();
        out.add(messagePack.read(array));
    }

}
