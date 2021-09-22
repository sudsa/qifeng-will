package com.hanxiaozhang.privateprotocol.message;

/**
 * 功能描述: <br>
 * 〈消息结果〉
 *
 * @Author:hanxinghua
 * @Date: 2020/10/23
 */
public final class NettyMessage {

    private Header header;

    private Object body;

    public final Header getHeader() {
        return header;
    }

    public final void setHeader(Header header) {
        this.header = header;
    }

    public final Object getBody() {
        return body;
    }

    public final void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "NettyMessage [header=" + header + "]";
    }
}
