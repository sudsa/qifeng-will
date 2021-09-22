package com.hanxiaozhang.producer;

import com.alibaba.fastjson.JSONObject;
import com.hanxiaozhang.configway.KafkaProducer;
import com.hanxiaozhang.configway.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController("kafka")
public class KafkaSendMsgController {

    @Autowired
    private KafkaProducer producer;  //使用RabbitTemplate,这提供了接收/发送等等方法

    @GetMapping("/send")
    public String sendDirectMessage() {
        for(int i=0;i<=5;i++){
            String messageId = String.valueOf(UUID.randomUUID());
            String messageData = "test message, hello!";
            String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            User user=new User();
            user.setId(messageId);
            user.setName(messageData);
            user.setAge( Integer.valueOf("456789"));
            //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
            producer.send(JSONObject.toJSONString(user));
        }
        return "ok";
    }


    @GetMapping("/send/transaction")
    public String sendtransaction() {
        for (int i = 0; i <= 5; i++) {
            String messageId = String.valueOf(UUID.randomUUID());
            String messageData = "test message, hello!";
            User user = new User();
            user.setId(messageId);
            user.setName(messageData);
            user.setAge(Integer.valueOf("456789"));
            //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
            producer.sendtransaction(JSONObject.toJSONString(user));
        }
        return "ok>>>";
    }
}
