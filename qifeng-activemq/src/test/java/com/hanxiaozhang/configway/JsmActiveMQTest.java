package com.hanxiaozhang.configway;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JsmActiveMQTest {

    @Resource
    private JmsProducer jmsProducer;


    @Test
    public void testStringQueue() {

        jmsProducer.sendStringQueue("stringQueue", "消息：" + 1);
    }


    @Test
    public void testStringListQueue() {

        List<String> idList = new ArrayList<>();
        idList.add("id1");
        idList.add("id2");
        idList.add("id3");

        System.out.println("正在发送集合队列消息ing......");
        jmsProducer.sendStringListQueue("stringListQueue", idList);
    }


    @Test
    public void testObjQueue() {

        System.out.println("正在发送对象队列消息......");
        jmsProducer.sendObjQueue("objQueue", new User("1", "小明", 20));
    }


    @Test
    public void testObjListQueue() {

        System.out.println("正在发送对象集合队列消息......");

        List<Serializable> userList = new ArrayList<>();
        userList.add(new User("1", "小明", 21));
        userList.add(new User("2", "小雪", 22));
        userList.add(new User("3", "小花", 23));

        jmsProducer.sendObjListQueue("objListQueue", userList);

    }


    @Test
    public void testStringTopic() {

        jmsProducer.sendStringTopic("stringTopic", "消息：" + 1);

    }


    @Test
    public void testStringListTopic() {

        List<String> idList = new ArrayList<>();
        idList.add("id1");
        idList.add("id2");
        idList.add("id3");

        System.out.println("正在发送集合主题消息ing......");
        jmsProducer.sendStringListTopic("stringListTopic", idList);
    }


    @Test
    public void testObjTopic() {

        System.out.println("正在发送对象主题消息......");
        jmsProducer.sendObjTopic("objTopic", new User("1", "小明", 20));
    }


    @Test
    public void testObjListTopic() {

        System.out.println("正在发送对象集合主题消息......");

        List<Serializable> userList = new ArrayList<>();
        userList.add(new User("1", "小明", 21));
        userList.add(new User("2", "小雪", 22));
        userList.add(new User("3", "小花", 23));

        jmsProducer.sendObjListTopic("objListTopic", userList);
    }
}
