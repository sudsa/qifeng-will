package com.qifeng.will.configway.original;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


/**
 * 〈一句话功能简述〉<br>
 * 〈传统JMS发送Queue消息〉
 *
 * @author hanxinghua
 * @create 2019/7/6
 * @since 1.0.0
 */
public class JmsQueueActiveMQ {

    /**
     * 发送消息
     */
    public static void sendMessage() {
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        try {
            //第一步 创建ConnectionFactory工厂对象
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616");
            //第二步 从工厂获取一个Connection链接对象
            connection = connectionFactory.createConnection();
            //第三步 连接MQ服务
            connection.start();
            //第四步 创建Session会话 createSession(是否启动事务, 消息确认机制)
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            //第五步 通过Session创建Queue（P2P模板）
            Queue queue = session.createQueue("Queue.test.original");
            //第六步 通过Session创建producer生产者
            producer = session.createProducer(queue);
            //第七步 通过Session创建消息对象
            TextMessage textMessage = session.createTextMessage("传统JMS方式发送消息");
            //第八步 通过生产者发送消息
            producer.send(textMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                //第九步 关闭资源
                producer.close();
                session.close();
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 接收消息
     */
    public static void receiveMessage(){
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        try {
            //第一步 创建ConnectionFactory工厂对象
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616");
            //第二步 从工厂获取一个Connection链接对象
            connection = connectionFactory.createConnection();
            //第三步 连接MQ服务
            connection.start();
            //第四步 创建Session会话 createSession(是否启动事务, 消息确认机制)
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            //第五步 通过Session创建Queue（P2P模板）
            Queue queue = session.createQueue("Queue.test.original");
            //第六步 通过Session创建consumer接收者
            MessageConsumer consumer = session.createConsumer(queue);
            //第七步 指定消息监听器
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        TextMessage textMessage=(TextMessage)message;
                        String text = textMessage.getText();
                        System.out.println("接收的消息："+text);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
            //第八步 测试需要，监听器一直启动
           while (true){
           }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }




}
