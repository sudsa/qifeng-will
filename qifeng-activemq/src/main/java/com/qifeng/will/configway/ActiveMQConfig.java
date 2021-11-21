package com.hanxiaozhang.configway;


import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@Slf4j
@EnableJms
@Configuration
public class ActiveMQConfig {



    /**
     * 创建连接工厂
     *
     * @param redeliveryPolicy
     * @return
     */
    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory (RedeliveryPolicy redeliveryPolicy){

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        // 设置重发机制
        activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);
        // 解决ActiveMq消息对象进行序列化时报错
        activeMQConnectionFactory.setTrustAllPackages(true);

        return activeMQConnectionFactory;
    }


    /**
     * 设置重发机制
     *
     * @return
     */
    @Bean
    public RedeliveryPolicy redeliveryPolicy(){

        RedeliveryPolicy redeliveryPolicy=   new RedeliveryPolicy();
        // 是否在每次尝试重新发送失败后,增长这个等待时间
        redeliveryPolicy.setUseExponentialBackOff(true);
        // 重发次数,默认为6次
        redeliveryPolicy.setMaximumRedeliveries(3);
        // 重发时间间隔,默认为1秒
        redeliveryPolicy.setInitialRedeliveryDelay(1);
        // 第一次失败后重新发送之前等待500毫秒,第二次失败再等待500 * 2毫秒,这里的2就是value
        redeliveryPolicy.setBackOffMultiplier(2);
        // 是否避免消息碰撞
        redeliveryPolicy.setUseCollisionAvoidance(false);
        // 设置重发最大拖延时间-1 表示没有拖延只有UseExponentialBackOff(true)为true时生效
        redeliveryPolicy.setMaximumRedeliveryDelay(-1);

        return redeliveryPolicy;
    }


    /**
     *  配置JmsTemplate模板
     *
     * @param activeMQConnectionFactory
     * @return
     */
    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory activeMQConnectionFactory){
        JmsTemplate jmsTemplate=new JmsTemplate();

        // 配置连接工厂
        jmsTemplate.setConnectionFactory(activeMQConnectionFactory);

        // 设置是否启用消息时间戳，表示消息被producer发送的时间(非broker接收的时间)。默认值为true。
        jmsTemplate.setMessageTimestampEnabled(true);

        // 设置QOS值，作用：是否开启deliveryMode、priority、timeToLive配置用于发送消息
        jmsTemplate.setExplicitQosEnabled(false);
        // 进行持久化配置：1表示非持久化，2表示持久化。仅当"isExplicitQosEnabled"等于"true"时，此选项才有用
        jmsTemplate.setDeliveryMode(2);
        // 持久化消息的有效时间，仅当"isExplicitQosEnabled"等于"true"时，此选项才有用
        // 注意:timeToLive属性只会在messageTimestampEnabled=false的情况下才有意义
        jmsTemplate.setTimeToLive(1000*60);
        // 设置生成值发送消息的优先级，仅当"isExplicitQosEnabled"等于"true"时，此选项才有用
        jmsTemplate.setPriority(1);

        // 此处可不设置默认，在发送消息时也可设置队列
        jmsTemplate.setDefaultDestination(null);
        // 客户端签收模式,测试不管用，需要配置在消息监听器上 20190718
//        jmsTemplate.setSessionAcknowledgeMode(2);

        return jmsTemplate;
    }


    /**
     *配置JmsMessagingTemplate模板
     *
     * @param jmsTemplate
     * @return
     */
    @Bean
    public JmsMessagingTemplate jmsMessagingTemplate(JmsTemplate jmsTemplate){
        JmsMessagingTemplate jmsMessagingTemplate = new JmsMessagingTemplate();
        jmsMessagingTemplate.setJmsTemplate(jmsTemplate);
        return jmsMessagingTemplate;
    }


    /**
     * Queue模式的监听器
     *
     * @param activeMQConnectionFactory
     * @return
     */
    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerQueue(ConnectionFactory activeMQConnectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(activeMQConnectionFactory);
        // 是否开启事务
//        bean.setSessionTransacted(true);
        // 应答机制
        factory.setSessionAcknowledgeMode(2);

        return factory;
    }


    /**
     * Topic模式的监听器
     *
     * @param activeMQConnectionFactory
     * @return
     */
    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ConnectionFactory activeMQConnectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setPubSubDomain(true);
        factory.setConnectionFactory(activeMQConnectionFactory);
        // 设置并行消费者数量 例：1-10 动态分配1-10消费者（有点类似克隆多个消费者）
        factory.setConcurrency("2");
        // 重连间隔时间
        factory.setRecoveryInterval(1000L);
        // 应答机制
        factory.setSessionAcknowledgeMode(2);
        return factory;
    }




    // ----------------------------其他配置方式------------------------

    /**
     * 创建连接工厂_其他（不启用）
     *
     * @param brokerUrl
     * @param userName
     * @param password
     * @param redeliveryPolicy
     * @return
     */
    //@Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory_other(@Value("${activemq_1.url}") String brokerUrl,
                                                                     @Value("${activemq_1.username}") String userName,
                                                                     @Value("${activemq_1.password}") String password,
                                                                     RedeliveryPolicy redeliveryPolicy)
    {
        log.info(brokerUrl + "-" + userName);
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(brokerUrl);
        connectionFactory.setUserName(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setRedeliveryPolicy(redeliveryPolicy);
        return connectionFactory;
    }


    /**
     *配置JmsMessagingTemplate模板（另一种方式）
     *
     * @param activeMQConnectionFactory
     * @return
     */
    //@Bean
    public JmsMessagingTemplate jmsMessagingTemplate_other(ConnectionFactory activeMQConnectionFactory){
        JmsMessagingTemplate jmsMessagingTemplate = new JmsMessagingTemplate();
        jmsMessagingTemplate.setConnectionFactory(activeMQConnectionFactory);
        //此处可不设置默认，在发送消息时也可设置队列
        jmsMessagingTemplate.setDefaultDestinationName(null);
        return jmsMessagingTemplate;
    }


}
