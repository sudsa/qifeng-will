package com.qifeng.will.configway.springxml;


import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.support.JmsUtils;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * 功能描述: <br>
 * 〈xml注解配置JMS发送者〉
 *
 * @Author:hanxinghua
 * @Date: 2019/7/6
 */
public class JmsSender {

    private JmsTemplate jmsQueueTemplate;

    public void setJmsQueueTemplate(JmsTemplate jmsQueueTemplate) {
        this.jmsQueueTemplate = jmsQueueTemplate;
    }

    /**
     * 功能描述: <br>
     * 〈JmsTemplate模板发送消息〉
     *
     */
    public String sendMessage(final String msg) {
        String result = null;

        //1. 发送消息
        //1.1 发送消息：
        send(msg);
        //1.2 内置消息转换器方式发送消息：
        //convertAndSend(msg);
        //1.3 发送消息之后同步接收返回信息：
        //Message replyMsg = sendAndReceive(msg);

        //2. 处理接收返回的消息(对应1.3)
        //result = handleResult(replyMsg);

        return result;
    }



    /**
     * 功能描述: <br>
     * 〈JmsTemplate模板接收消息〉
     *   同步方法
     *
     */
    public void receiverMessage(){
        try {
            //调用receive()后，JmsTemplate会查看队列或主题是否有消息，直到结束消息或超时才返回
            TextMessage receive = (TextMessage)jmsQueueTemplate.receive();
            System.out.println("JmsTemplate模板接收消息: "+receive.getText());
        } catch (JMSException e) {
            e.printStackTrace();
            //把受检异常转JMSException换为非受检异常JMSException
            JmsUtils.convertJmsAccessException(e);
        }
    }


    private void send(String msg) {
        this.jmsQueueTemplate.send(new MessageCreator(){
            @Override
            public Message createMessage(Session sn) throws JMSException {
                TextMessage txtMsg = sn.createTextMessage(msg);
                return txtMsg;
            }
        });
    }

    private void convertAndSend(String msg) {
        this.jmsQueueTemplate.convertAndSend(msg);
    }

    private Message sendAndReceive(String msg) {
        return this.jmsQueueTemplate.sendAndReceive(new MessageCreator(){
            @Override
            public Message createMessage(Session sn) throws JMSException {
                TextMessage txtMsg = sn.createTextMessage(msg);
                return txtMsg;
            }
        });
    }

    private String handleResult(Message replyMsg) {
        String result = null;
        try {
            TextMessage txtMsg = (TextMessage)replyMsg;
            result = txtMsg.getText();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        System.out.println("JmsSender_result: "+result);
        return result;
    }

}