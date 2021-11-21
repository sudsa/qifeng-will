package com.qifeng.will.qifengwebflux.route;

import com.qifeng.will.qifengwebflux.handle.RedisReactiveHandler;
import com.qifeng.will.qifengwebflux.handle.TimeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Autowired
    private TimeHandler timeHandler;

    @Autowired
    private RedisReactiveHandler reactiveHandler;

    @Bean
    public RouterFunction<ServerResponse> timerRouter() {
        return route(GET("/time"), req -> timeHandler.getTime(req))
                .andRoute(GET("/date"), timeHandler::getDate);  // 这种方式相对于上一行更加简洁
    }

    @Bean
    public RouterFunction<ServerResponse> timerRouterPer() {
        return route(GET("/times"), timeHandler::sendTimePerSec)
                .andRoute(GET("/redis"),reactiveHandler::redis);  // 增加这一行
    }



}
