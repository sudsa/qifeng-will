# 目录结构：
|
|-- base :模拟TCP粘包拆包导致功能的异常
|-- delimiterbasedframedecoder :使用DelimiterBasedFrameDecoder解决TCP粘包拆包
|-- fixedlengthframedecoder :使用FixedLengthBasedFrameDecoder解决TCP粘包拆包
|-- linebasedframedecoder :使用LineBasedFrameDecoder解码器解决TCP粘包拆包
|-- messagepack 使用MessagePack作为编解码器的开发
|-- websocket Netty Websocket协议开发


# TCP
TCP是个"流"协议，所谓流，就没有界限的一串数据。
## TCP粘包/拆包：
### 概念：
TCP底层并不了解上层业务数据的具体含义，它会根据TCP缓冲区的实际情况进行包的划分，所以在业务上认为，
一个完整的包可能会被TCP拆成多个包进行发送，也有可能吧多个小的包封装成一个大的数据包发送，这就是TCP粘包和拆包。
### 原因：
i.应用程序write写入的字节大小大于套接字接口发送缓冲区大小；
ii.进行MSS（最大报文段长度）大小的TCP分段；
iii.以太网帧的payload（有效载荷）大于MTU(最大传输单元)进行IP分片
[TCP粘包_拆包问题原因.png]
### 解决：
i.消息定长，例如每个报文的大小固定长度200字节，如果不够用空格补齐。
ii.在包尾增加回车换行符进行分割，例如FTP协议；
iii.将消息分为消息头和消息体，消息头中包含消息总长度（或消息体长度）的字段，
通常设计思路为消息头的第一个字段使用int32来表示消息的总长度；
iv.更复杂的应用层协议。

## TCP上层的应用协议进行区分消息的4种方式：
+ 消息长度固定，累计读取到长度总和为定长LEN的报文后，就认为读取到一个完整的消息：将计数器位置，重新开始读取下一个数据报。
+ 将回车换行符作为消息的结束符，例如FTP协议，这种方式在文本协议中应用比较广泛；
+ 将特殊的分隔符作为消息的结束标志，回车换行符就是一种特殊的结束分隔符；
+ 通过在消息头中定义长度字段来标识消息的总长度。
**TIps：Netty对上面4种应用做了统一的抽象，提供了4种解码器来解决对应的问题，使用起来非常方便。**


# 解码器
## LineBasedFrameDecoder
原理：
它依次遍历ByteBuf中的可读字节，判断看是否有"\n"或者"\r\n"，如果有，就以此位置为结束位置，
从可读索引到结束位置区间的字节就组成了一行。它是以换行符为结束标识的解码器，支持携带结束符
或者不带结束符两种解码方式，同时支持配置单行的最大长度。

## StringDecoder
将接收到对象转换成字符串，然后继续调用后边的Handler。

## DelimiterBasedFrameDecoder
它可以自动完成以分割符作为码流结束标识的消息的解码。

## FixedLengthBasedFrameDecoder
固定长度解码器，它能够按照指定的长度对消息进行解码。如果是半包消息，FixedLengthBasedFrameDecoder
会缓存半包消息并等待下一个包到达后进行拼包

# java序列化
## 作用：
网络传输和对象持久化
## 缺点：
无法跨语言、序列化后码流太大、序列化性能太低

# 业界主流的编解码框架
## Protobuf（Google Protocol Buffers）：
它将数据结构以.Proto文件进行描述，通过代码生成工具
可以生成对应数据结构的POJO对象和Protobuf相关的方法和属性。
特点：结构化数据存储格式（XML、JSON等）；高效的编解码性能；
言语无关、平台无关、拓展性好；官方支持Java、C++和Python三种语言。

## Facebook的Thrift
解决Facebook各系统间大数据量的传输通信以及系统之间语言环境不同需要跨平台的特性，
因此Thrift支持多种程序语言，如C++、C#、Cocoa、Erlang、Haskell、Java、Ocami、Prel
PHP、Python、Ruby和Smalltalk。

##JBoss Marshalling
它是java对象序列化API包，修正了JDK自带的序列化包的很多问题。

## MessagePack编解码
它是一个高效的二进制序列化框架，它像JSON一样支持不同语言间的数据
交换，但是它的性能更快，序列化之后码流也更小。

# HTTP协议的弊端：
i.HTTP协议为半双工协议；
ii.HTTP消息冗长而繁琐；
iii.针对服务器推送的黑客攻击，例如长时间轮询。

# WebSocket
## 概念：
它是HTML5 开始提供的一种浏览器与服务器间进行全双工通信的网络技术，
在WebSocket API中，浏览器和服务器只需要做一个握手的动作，然后，浏览器和服务器
之间就形成了一条快速通道，两者就可以直接相互传送数据了。
## 特点：
单一的TCP链接，采用全双工模式通信；
对代理、防火墙和路由器透明；
无头部信息、Cookie和身份验证；
无安全开销；
通过"ping/pong"帧保持链路激活；
服务器可以主动传递消息给客户端，不需要客户端轮询。


Netty
[Netty服务端创建时序图.png]
[Netty客户端创建时序图.png]



