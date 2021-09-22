package com.hanxiaozhang.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 功能描述: <br>
 * 〈多路复用器单线程模式〉
 *
 *  Linux下指定IO模型的参数：
 *  -Djava.nio.channels.spi.SelectorProvider=sun.nio.ch.EPollSelectorProvider
 *  -Djava.nio.channels.spi.SelectorProvider=sun.nio.ch.PollSelectorProvider
 *
 *  javac SocketMultiplexingSingleThreadv1.java && strace -ff -o poll java  -Djava.nio.channels.spi.SelectorProvider=sun.nio.ch.PollSelectorProvider SocketMultiplexingSingleThreadv1
 *  javac SocketMultiplexingSingleThreadv1.java && strace -ff -o epoll java   SocketMultiplexingSingleThreadv1
 *
 * @Author:hanxinghua
 * @Date: 2021/8/18
 */
public class SocketMultiplexingSingleThreadv1 {


    private ServerSocketChannel server = null;
    /**
     * java的selector是linux中的多路复用器(select、poll、epoll)或 nginx的event{}
     */
    private Selector selector = null;
    int port = 9090;

    public void initServer() {

        try {
            server = ServerSocketChannel.open();
            // 设置成非阻塞
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));


            // select、poll、epoll，优先选择：epoll，但是可以-Djava参数修正。
            // 如果在epoll模型下，open  -->  epoll_create -> fd3
            selector = Selector.open();

            //在select、poll模型下：jvm里开辟一个数组fd4放进去
            //在epoll模型下：epoll_ctl(fd3,ADD,fd7,EPOLLIN
            server.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("ctl lazy loading");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        initServer();
        System.out.println("服务器启动了。。。。。");

        try {
            // 死循环
            while (true) {

                Set<SelectionKey> keys = selector.keys();
                System.out.println(keys.size()+"   size");


                //1. 调用多路复用器(select、poll  or  epoll (epoll_wait))
                /*
                selector.select()是啥意思：
                i. select、poll: 内核的select（fd4）或 poll(fd4)
                ii. epoll: 内核的epoll_wait()

                selector.select(long timeout) --> 参数可以带时间
                没有时间：0 ，阻塞，有时间设置一个超时

                selector.wakeup()：
                使尚未返回的selector操作立即返回 --> 结果返回0

                懒加载：
                在触碰到selector.select()调用的时候触发了epoll_ctl的调用
                 */
                while (selector.select() > 0) {
                    // selector.selectedKeys() 约等于 返回的有状态的fd集合
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iter = selectionKeys.iterator();
                    // 不管使用那种多路复用器，只能返回状态，需要一个一个的去处理它们的R/W。同步好辛苦！！！！
                    //  NIO：自己对着每一个fd调用系统调用，浪费资源。
                    //  多路复用器：调用了一次select方法，知道具体的那些可以R/W了。

                    // 前边可以强调过，socket分为 listen的socket 和 连接通信的socket
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        // set  不移除会重复循环处理
                        iter.remove();
                        if (key.isAcceptable()) {
                            // 看代码的时，这里是重点，如果要去接受一个新的连接，语义上，accept接受连接且返回新连接的fd，
                            // 那新的 fd怎么办？
                            // select、poll：因为它们内核没有空间，在jvm中保存，它和前边的fd4那个listen放在一起
                            // epoll：我们希望通过epoll_ctl把新的客户端fd注册到内核空间
                            acceptHandler(key);
                        } else if (key.isReadable()) {
                            // 连read 还有 write都处理了
                            readHandler(key);
                            // 在当前线程（单线程），这个方法可能会阻塞，如果阻塞了很长时间，其他的IO早就没电了。。。
                            /*
                            拓展所以，为什么提出了IO THREADS(IO 多线程)
                            例如：redis  是不是用了epoll，redis是不是有个io threads的概念 ，redis是不是单线程的
                            例如：tomcat 8,9：异步的处理方式 IO和处理上解耦
                             */
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void acceptHandler(SelectionKey key) {
        try {
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            //  ServerSocketChannel目的是调用accept接受客户端  fd7
            SocketChannel client = ssc.accept();
            client.configureBlocking(false);

            // 前边讲过了
            ByteBuffer buffer = ByteBuffer.allocate(8192);


            //你看，又调用了register
            /*
            select、poll：jvm里开辟一个数组 fd7 放进去
            epoll: epoll_ctl(fd3,ADD,fd7,EPOLLIN
             */
            client.register(selector, SelectionKey.OP_READ, buffer);
            System.out.println("-------------------------------------------");
            System.out.println("新客户端：" + client.getRemoteAddress());
            System.out.println("-------------------------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readHandler(SelectionKey key) {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.clear();
        int read = 0;
        try {
            while (true) {
                read = client.read(buffer);
                if (read > 0) {
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        client.write(buffer);
                    }
                    buffer.clear();
                } else if (read == 0) {
                    break;
                } else {
                    // read 为-1时关闭client
                    client.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static void main(String[] args) {
        SocketMultiplexingSingleThreadv1 service = new SocketMultiplexingSingleThreadv1();
        service.start();
    }


}
