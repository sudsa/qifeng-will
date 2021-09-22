package com.hanxiaozhang.privateprotocol.constant;

/**
 * 功能描述: <br>
 * 〈信息类型〉
 *
 * @Author:hanxinghua
 * @Date: 2020/10/23
 */
public enum MessageType {

    // 服务请求
    SERVICE_REQ((byte) 0),
    // 服务响应
    SERVICE_RESP((byte) 1),
    // ONE WAY消息 （既是请求消息也是响应消息）
    ONE_WAY((byte) 2),
    // 登录请求
    LOGIN_REQ((byte) 3),
    // 登录响应
    LOGIN_RESP((byte) 4),
    // 心跳请求
    HEARTBEAT_REQ((byte) 5),
    // 心跳响应
    HEARTBEAT_RESP((byte) 6);

    private byte value;

    private MessageType(byte value) {
        this.value = value;
    }

    public byte value() {
        return this.value;
    }
}
