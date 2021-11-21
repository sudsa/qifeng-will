# springboot_activemq
## 零、设置message过期时间:
**主要：** 如果配置了消息持久化，消息过期时间以timeToLive为准，反之以一下配置为准。
i.message过期则客户端不能接收；  
ii.参数：
ttlCeiling：表示过期时间上限（程序写的过期时间不能超过此时间，超过则以此时间为准）
zeroExpirationOverride：表示过期时间（给未分配过期时间的消息分配过期时间）
```xml
<borker>
    <plugins>
        <!-- 86,400,000ms = 1 day -->
        <timeStampingBrokerPlugin ttlCeiling="360000" zeroExpirationOverride="360000" />
    </plugins>
</borker> 
```

## 一、DLQ(死信队列)
### 0.参考文章：
https://blog.csdn.net/qq_26975307/article/details/100705355
### 1.概念：
一条消息在被重发了多次后（默认为重发6次redeliveryCounter==6），将会被ActiveMQ移入"死信队列",
或者是过期的消息也将进入"死信队列"。
**Tips：**   
i.缺省的死信队列是ActiveMQ.DLQ，如果没有特别指定，死信都会被发送到这个队列。默认情况下，
  无论是Topic还是Queue，broker将使用Queue来保存DeadLeader，即死信通道通常为Queue
ii.缺省持久消息过期，会被送到DLQ，非持久消息不会送到DLQ
iii.可以通过配置文件(activemq.xml)来调整死信发送策略。

**问题：**
Topic不进入死信队列（不管你怎么去配置） 20200525




### 2.配置：
**SharedDeadLetterStrategy（默认）：**    
将所有的DeadLetter保存在一个共享的队列中，这是ActiveMQ broker端默认的策略。
共享队列默认为 “ActiveMQ.DLQ”，可以通过“deadLetterQueue”属性来设定（activemq.xml配置）
它还有2个很重要的可选参数:   
“processExpired”表示是否将过期消息放入死信队列，默认为true；  
“processNonPersistent”表示是否将“非持久化”消息放入死信队列，默认为false。   
```xml
 <deadLetterStrategy>
     <sharedDeadLetterStrategy deadLetterQueue="DLQ-QUEUE"  processExpired="true"  processNonPersistent="false" />
 </deadLetterStrategy>
```
**IndividualDeadLetterStrategy:**   
把DeadLetter放入各自的死信通道中，对于Queue而言，死信通道的前缀默认为“ActiveMQ.DLQ.Queue.”；
对于Topic而言，死信通道的前缀默认为“ActiveMQ.DLQ.Topic.”；
可选参数:    
queuePrefix自定义死信前缀;
useQueueForQueueMessages 表示使用队列保存死信，默认为true；
useQueueForTopicMessages 表示使用Topic保存死信，默认为true。    
**举例：队列order，配置单独的死信队列：**  
```xml
<!--queue=">" 表示设置所有队列-->
<policyEntry queue="order">
    <deadLetterStrategy>
       <individualDeadLetterStrategy queuePrefix="DLQ." useQueueForQueueMessages="true"/>
    </deadLetterStrategy>
</policyEntry>
```



