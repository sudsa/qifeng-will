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
 * 〈P2P模式〉
 *
 * @Author:hanxinghua
 * @Date: 2019/7/7
 */
@Component
public class JmsQueueConsumer {

    @JmsListener(destination = "stringQueue", containerFactory = "jmsListenerContainerQueue")
    public void receiveStringQueue(String msg, Session session, Message message) {
        try {
            System.out.println("监听的消息： "+msg);
            if(msg.equals("你好！")){
                //调用acknowledge()确认，通知Broker消费成功
                message.acknowledge();
            }else{
                System.out.println("消息重发！");
                //调用recover()通知Broker重复消息
                session.recover();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
        System.out.println("接收到消息...." + msg);
    }

//    @JmsListener(destination = "stringListQueue", containerFactory = "jmsListenerContainerQueue")
    public void receiveStringListQueue(List<String> list) {
        System.out.println("接收到集合队列消息...." + list);
    }


    @JmsListener(destination = "objQueue", containerFactory = "jmsListenerContainerQueue")
    public void receiveObjQueue(ObjectMessage objectMessage) throws Exception {
        System.out.println("接收到对象队列消息...." + objectMessage.getObject());
    }


    @JmsListener(destination = "objListQueue", containerFactory = "jmsListenerContainerQueue")
    public void receiveObjListQueue(ObjectMessage objectMessage) throws Exception {
        System.out.println("接收到的对象队列消息..." + objectMessage.getObject());
    }


}
