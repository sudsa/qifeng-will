package com.qifeng.will.configway;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class DirectRabbitConfig {

    @Bean
    public Queue testDirectQueue(){
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        //return new Queue("TestDirectQueue",true,true,false);
        return new Queue("testDirectQueue", true);
    }

    @Bean
    public DirectExchange testDirectExchange(){
        return new DirectExchange("testDirectExchage",true, false);
    }

    //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
    @Bean
    public Binding bindingDirect(){
        return BindingBuilder.bind(testDirectQueue()).to(testDirectExchange()).with("testDirectRouting");

    }

    @Bean
    DirectExchange lonelyDirectExchange() {
        return new DirectExchange("lonelyDirectExchange");
    }


    @Bean
    public DirectExchange canalDirectExchange() {
        return new DirectExchange("exchange.fanout.canal");
    }


    //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
    @Bean
    public Binding bindingCanalDirect(){
        return BindingBuilder.bind(testDirectQueue()).to(canalDirectExchange()).with("testCanalDirectRouting");
    }

}
