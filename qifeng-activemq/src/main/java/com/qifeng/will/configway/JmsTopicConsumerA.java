package com.qifeng.will.configway;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import java.util.List;

/**
 * 功能描述: <br>
 * 〈Springboot-activeMQ消息消费者〉
 * 〈Pub/Sub模式 A订阅者〉
 *
 * @Author:hanxinghua
 * @Date: 2019/7/7
 */
@Component
public class JmsTopicConsumerA {

    @JmsListener(destination = "stringTopic", containerFactory = "jmsListenerContainerTopic")
    public void receiveStringTopic(String msg, Session session, Message message) throws JMSException {
        try {
            System.out.println("监听的消息： "+msg);
            if(msg.equals("你好！")){
                // 调用acknowledge()确认，通知Broker消费成功
                message.acknowledge();
            }else{
                System.out.println("消息重发！");
                // 调用recover()通知Broker重复消息
                session.recover();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

//    @JmsListener(destination = "stringListTopic", containerFactory = "jmsListenerContainerTopic")
    public void receiveStringListTopic(List<String> list) {
        System.out.println("ATopicConsumer接收到集合主题消息...." + list);
    }


    @JmsListener(destination = "objTopic", containerFactory = "jmsListenerContainerTopic")
    public void receiveObjTopic(ObjectMessage objectMessage) throws Exception {
        System.out.println("ATopicConsumer接收到对象主题消息...." + objectMessage.getObject());
    }


    @JmsListener(destination = "objListTopic", containerFactory = "jmsListenerContainerTopic")
    public void receiveObjListTopic(ObjectMessage objectMessage) throws Exception {
        System.out.println("ATopicConsumer接收到的对象集合主题消息..." + objectMessage.getObject());
    }

}
