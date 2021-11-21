package com.qifeng.will.qifengwebflux.handle.websocket;

import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.FluxSink;

public class WebSocketSender {
    private WebSocketSession session;// WebSocket 连接的 session
    private FluxSink<WebSocketMessage> sink;//WebSocket对应的 FluxSink

    public WebSocketSender(WebSocketSession session, FluxSink<WebSocketMessage> sink) {
        this.session = session;
        this.sink = sink;
    }

    public void sendData(String data) {
        sink.next(session.textMessage(data));
    }

}
