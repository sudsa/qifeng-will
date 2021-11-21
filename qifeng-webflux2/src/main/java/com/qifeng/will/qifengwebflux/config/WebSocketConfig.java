package com.qifeng.will.qifengwebflux.config;

import com.qifeng.will.qifengwebflux.handle.websocket.EchoHandler;
import com.qifeng.will.qifengwebflux.handle.websocket.WebSocketSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class WebSocketConfig {

    @Bean
    public HandlerMapping handlerMapping(@Autowired EchoHandler echoHandler){
        //定义映射集合
        Map<String, WebSocketHandler> map = new HashMap<>();
        //配置映射模型
        map.put("/websocket/{token}", echoHandler);
        //映射处理
        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
        //优先配置
        handlerMapping.setOrder(Ordered.HIGHEST_PRECEDENCE);
        //映射路劲
        handlerMapping.setUrlMap(map);
        return handlerMapping;//
    }

    //配置适配器
    @Bean
    public WebSocketHandlerAdapter webSocketHandlerAdapter(){
        return new WebSocketHandlerAdapter();

    }

    @Bean
    public ConcurrentHashMap<String, WebSocketSender> senderMap(){
        return new ConcurrentHashMap<String, WebSocketSender>();
    }
}
