package com.qifeng.will.configway.springxml;





import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.jms.support.JmsUtils;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * 功能描述: <br>
 * 〈XML注解配置JMS接收者〉
 * 〈监听器接收消息〉
 *
 * @Author:hanxinghua
 * @Date: 2019/7/6
 */
public class JmsReceiver implements SessionAwareMessageListener<TextMessage> {
    @Override
    public void onMessage(TextMessage message, Session session) {
        //handleSynchMessage(message, session);
        handleRecoverMessage(message, session);

    }


    /**
     * 功能描述: <br>
     * 〈消费者接受消息后，同步反馈消息〉
     *
     * @Author:hanxinghua
     * @Date: 2019/7/15
     */
    private void handleSynchMessage(TextMessage message, Session session) {
        MessageProducer producer = null;
        TextMessage txtMsg;
        try {
            System.out.println("XML注解配置消息队列,收到消息："+message.getText());
            txtMsg = session.createTextMessage("JmsReceiver成功接受消息！");
            producer = session.createProducer(null);
            producer.send(message.getJMSReplyTo(), txtMsg);
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            JmsUtils.closeMessageProducer(producer);
        }
    }

    /**
     * 功能描述: <br>
     * 〈处理需要重发的消息〉
     *
     * @Author:hanxinghua
     * @Date: 2019/7/15
     */
    private void handleRecoverMessage(TextMessage message, Session session) {
        try {
            System.out.println("监听的消息： "+message.getText());
            if(message.getText().equals("你好！")){
                //调用acknowledge()确认，通知Broker消费成功
                message.acknowledge();
            }else{
                System.out.println("重发次数。。。");
                //调用recover()通知Broker重复消息
                session.recover();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}