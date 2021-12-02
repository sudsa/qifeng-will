package com.qifeng.will.configway.original;

import org.junit.Test;

/**
 * 〈一句话功能简述〉<br>
 * 〈传统JMS发送测试〉
 *
 * @author hanxinghua
 * @create 2019/7/6
 * @since 1.0.0
 */
public class JmsActiveMQTest {

    @Test
    public void sendQueueMessage(){
        JmsQueueActiveMQ.sendMessage();
    }

    @Test
    public  void  receiveQueueMessage(){
        JmsQueueActiveMQ.receiveMessage();
    }
    @Test
    public void sendTopicMessage(){
        JmsTopicActiveMQ.sendMessage();
    }

    @Test
    public  void  receiveTopicMessage(){
        JmsTopicActiveMQ.receiveMessage();
    }


    @Test
    public void sendTopicPersistenceMessage(){
        JmsTopicPersistenceActiveMQ.sendMessage();
    }


    @Test
    public void receiveTopicPersistenceMessage(){
        JmsTopicPersistenceActiveMQ.receiveMessage();
    }

}
