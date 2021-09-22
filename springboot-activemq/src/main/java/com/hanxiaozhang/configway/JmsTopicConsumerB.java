package com.hanxiaozhang.configway;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.ObjectMessage;
import java.util.List;

/**
 * 功能描述: <br>
 * 〈Springboot-activeMQ消息消费者〉
 * 〈Pub/Sub模式 B订阅者〉
 *
 * @Author:hanxinghua
 * @Date: 2019/7/7
 */
//@Component
public class JmsTopicConsumerB {

    @JmsListener(destination = "stringTopic", containerFactory = "jmsListenerContainerTopic")
    public void receiveStringTopic(String msg) {
        System.out.println("BTopicConsumer接收到消息...." + msg);
    }


    @JmsListener(destination = "stringListTopic", containerFactory = "jmsListenerContainerTopic")
    public void receiveStringListTopic(List<String> list) {
        System.out.println("BTopicConsumer接收到集合主题消息...." + list);
    }


    @JmsListener(destination = "objTopic", containerFactory = "jmsListenerContainerTopic")
    public void receiveObjTopic(ObjectMessage objectMessage) throws Exception {
        System.out.println("BTopicConsumer接收到对象主题消息...." + objectMessage.getObject());
    }


    @JmsListener(destination = "objListTopic", containerFactory = "jmsListenerContainerTopic")
    public void receiveObjListTopic(ObjectMessage objectMessage) throws Exception {
        System.out.println("BTopicConsumer接收到的对象集合主题消息..." + objectMessage.getObject());
    }

}
