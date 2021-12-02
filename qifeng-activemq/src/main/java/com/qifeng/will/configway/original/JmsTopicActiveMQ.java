package com.qifeng.will.configway.original;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


/**
 * 〈一句话功能简述〉<br>
 * 〈传统JMS发送Topic消息〉
 *
 * @author hanxinghua
 * @create 2019/7/6
 * @since 1.0.0
 */
public class JmsTopicActiveMQ {

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
            //第五步 通过Session创建Queue（Pub/Sub模板）
            Topic topic = session.createTopic("topic.test.original");
            //第六步 通过Session创建producer生产者
            producer= session.createProducer(topic);
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
        MessageProducer producer = null;
        try {
            //第一步 创建ConnectionFactory工厂对象
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616");
            //第二步 从工厂获取一个Connection链接对象
            connection = connectionFactory.createConnection();
            //第三步 连接MQ服务
            connection.start();
            //第四步 创建Session会话 createSession(是否启动事务, 消息确认机制)
            final  Session session  = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);
            //第五步 通过Session创建Queue（Pub/Sub模板）
            Topic topic = session.createTopic("topic.test.original");
            //第六步 通过Session创建consumer接收者
            MessageConsumer consumer = session.createConsumer(topic);
            //第七步 指定消息监听器
            consumer.setMessageListener(new MessageListener() {
                int i=0;
                @Override
                public void onMessage(Message message) {
                    try {
                        TextMessage textMessage=(TextMessage)message;
                        String text = textMessage.getText();
                        if(text.equals("你好！")){
                            //调用acknowledge()确认，通知Broker消费成功
                            message.acknowledge();
                        }else{
                            System.out.println("重发次数： "+i++);
                            //调用recover()通知Broker重复消息，最多重复6次
                            session.recover();
                        }
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
