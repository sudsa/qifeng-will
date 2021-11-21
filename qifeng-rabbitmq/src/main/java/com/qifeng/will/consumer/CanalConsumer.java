package com.qifeng.will.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.channels.Channel;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class CanalConsumer {

    //声明使用的的监听器工厂（不声明使用默认的工厂）
    @RabbitListener( queues = {"testDirectQueue"})
    public void receiver5(Message msg, Channel channel) throws IOException, InterruptedException {
        //打印数据
        String message = new String(msg.getBody(), StandardCharsets.UTF_8);
        log.info("队列消费消息{}"+message);
        //channel.basicReject(msg.getMessageProperties().getDeliveryTag(), false);
    }

}
