package com.qifeng.will.configway.springxml;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 〈一句话功能简述〉<br>
 * 〈XML配置消息队列测试〉
 *
 * @author hanxinghua
 * @create 2019/7/6
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JsmActiveMQTest {

    @Autowired
    private JmsSender jmsSender;

    @Test
    public  void  producerQueue(){
        jmsSender.sendMessage("XML配置消息队列测试，你好！");
    }

    @Test
    public  void  consumerQueue(){
        jmsSender.receiverMessage();;
    }

}
