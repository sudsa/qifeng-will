package com.hanxiaozhang.privateprotocol.codec;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;

import java.io.IOException;


/**
 * 功能描述: <br>
 * 〈Marshalling解码器〉
 *
 * @Author:hanxinghua
 * @Date: 2020/10/23
 */
public class MarshallingDecoder {

    private final Unmarshaller unmarshaller;

    /**
     * 创建最大对象大小为1048576字节的新解码器
     * 如果接收到的对象的大小大于1048576字节，将引发StreamCorruptedException异常
     *
     * @throws IOException
     */
    public MarshallingDecoder() throws IOException {
        unmarshaller = MarshallingCodecFactory.buildUnMarshalling();
    }

    /**
     * 解码
     *
     * @param in
     * @return
     * @throws Exception
     */
    protected Object decode(ByteBuf in) throws Exception {

        // 获取当前readerIndex处的32位整数，并将这个缓冲区中的readerIndex增加4
        int objectSize = in.readInt();
        // 返回此缓冲区子区域的一部分
        ByteBuf buf = in.slice(in.readerIndex(), objectSize);
        // 理解有些困难 20201023
        ByteInput input = new ChannelBufferByteInput(buf);
        try {
            unmarshaller.start(input);
            Object obj = unmarshaller.readObject();
            unmarshaller.finish();
            in.readerIndex(in.readerIndex() + objectSize);
            return obj;
        } finally {
            unmarshaller.close();
        }
    }
}
