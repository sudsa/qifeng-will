package com.qifeng.will.configway;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 功能描述: <br>
 * 〈Springboot-activeMQ消息消费者〉
 * 〈P2P模式〉
 *
 * @Author:howill.zou
 * @Date: 2019/7/7
 */
@Component
public class FanoutRabbitConfig {

    @Bean
    public Queue fanoutA(){
        return new Queue("fanout.A",true);
    }
    @Bean
    public Queue fanoutB(){
        return new Queue("fanout.B",true);
    }

    @Bean
    public Queue fanoutC(){
        return new Queue("fanout.C",true);
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    Binding bindingExchangeA() {
        return BindingBuilder.bind(fanoutA()).to(fanoutExchange());
    }

    @Bean
    Binding bindingExchangeB() {
        return BindingBuilder.bind(fanoutB()).to(fanoutExchange());
    }

    @Bean
    Binding bindingExchangeC() {
        return BindingBuilder.bind(fanoutC()).to(fanoutExchange());
    }

}
