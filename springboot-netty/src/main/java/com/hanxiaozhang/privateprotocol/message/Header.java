package com.hanxiaozhang.privateprotocol.message;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述: <br>
 * 〈消息头〉
 *
 * @Author:hanxinghua
 * @Date: 2020/10/23
 */
public final class Header {

    /**
     * 消息的校验码，固定值（0xabef）+主版本号(1~255)+次版本号(1~255)
     */
    private int crcCode = 0xabef0101;

    /**
     * 消息长度，整个消息，包括消息头和消息体
     */
    private int length;

    /**
     * 会话ID，集群节点内全局唯一，由回话ID生成器生成
     */
    private long sessionID;

    /**
     * 消息类型
     * 0	业务请求消息
     * 1	业务响应消息
     * 2	业务ONE WAY消息 （既是请求消息也是响应消息）
     * 3	握手请求消息
     * 4	握手响应消息
     * 5	心跳请求消息
     * 6	心跳应答消息
     *
     */
    private byte type;

    /**
     * 消息优先级
     */
    private byte priority;

    /**
     * 附件，可选字段，用于扩展消息头
     */
    private Map<String, Object> attachment = new HashMap<String, Object>();

    public Header(Builder builder) {
        setCrcCode(builder.crcCode);
        setLength(builder.length);
        setSessionID(builder.sessionID);
        setType(builder.type);
        setPriority(builder.priority);
        setAttachment(builder.attachment);
    }

    public Header() {

    }


    public final int getCrcCode() {
        return crcCode;
    }


    public final void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }


    public final int getLength() {
        return length;
    }


    public final void setLength(int length) {
        this.length = length;
    }


    public final long getSessionID() {
        return sessionID;
    }


    public final void setSessionID(long sessionID) {
        this.sessionID = sessionID;
    }


    public final byte getType() {
        return type;
    }


    public final void setType(byte type) {
        this.type = type;
    }


    public final byte getPriority() {
        return priority;
    }


    public final void setPriority(byte priority) {
        this.priority = priority;
    }


    public final Map<String, Object> getAttachment() {
        return attachment;
    }


    public final void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }


    @Override
    public String toString() {
        return "Header [crcCode=" + crcCode + ", length=" + length
                + ", sessionID=" + sessionID + ", type=" + type + ", priority="
                + priority + ", attachment=" + attachment + "]";
    }


    public static final class Builder {
        private int crcCode;
        private int length;
        private long sessionID;
        private byte type;
        private byte priority;
        private Map<String, Object> attachment;

        public Builder() {
        }

        public Builder crcCode(int val) {
            crcCode = val;
            return this;
        }

        public Builder length(int val) {
            length = val;
            return this;
        }

        public Builder sessionID(long val) {
            sessionID = val;
            return this;
        }

        public Builder type(byte val) {
            type = val;
            return this;
        }

        public Builder priority(byte val) {
            priority = val;
            return this;
        }

        public Builder attachment(Map<String, Object> val) {
            attachment = val;
            return this;
        }

        public Header build() {
            return new Header(this);
        }
    }
}
