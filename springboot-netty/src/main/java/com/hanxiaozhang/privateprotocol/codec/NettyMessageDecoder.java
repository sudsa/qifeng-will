package com.hanxiaozhang.privateprotocol.codec;

import com.hanxiaozhang.privateprotocol.message.Header;
import com.hanxiaozhang.privateprotocol.message.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述: <br>
 * 〈消息解码器〉
 *
 * @Author:hanxinghua
 * @Date: 2020/10/23
 */
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {

    private MarshallingDecoder marshallingDecoder;

    public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) throws IOException {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
        marshallingDecoder = new MarshallingDecoder();
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {

        ByteBuf frame = (ByteBuf) super.decode(ctx, in);

        if (frame == null) {
            return null;
        }

        NettyMessage message = new NettyMessage();
        Header header = new Header
                .Builder()
                .crcCode(frame.readInt())
                .length(frame.readInt())
                .sessionID(frame.readLong())
                .type(frame.readByte())
                .priority(frame.readByte())
                .build();

        int size = frame.readInt();

        if (size > 0) {
            int keySize = 0;
            byte[] keyArray = null;
            Map<String, Object> attch = new HashMap<String, Object>(size);
            String key = null;

            for (int i = 0; i < size; i++) {
                keySize = frame.readInt();
                keyArray = new byte[keySize];
                frame.readBytes(keyArray);
                key = new String(keyArray, "UTF-8");
                attch.put(key, marshallingDecoder.decode(frame));
            }
            keyArray = null;
            key = null;
            header.setAttachment(attch);
        }
        if (frame.readableBytes() > 4) {
            message.setBody(marshallingDecoder.decode(frame));
        }
        message.setHeader(header);
        return message;
    }
}
