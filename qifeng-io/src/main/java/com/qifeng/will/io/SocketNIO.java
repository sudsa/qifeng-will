package com.hanxiaozhang.io;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;


/**
 * 功能描述: <br>
 * 〈〉
 *
 *  javac SocketNIO.java &&  strace -ff -o out java  SocketNIO
 *
 *  SocketNIO与C10Kclient使用
 *
 * @Author:hanxinghua
 * @Date: 2021/8/15
 */
public class SocketNIO {

    //  what   why  how
    public static void main(String[] args) throws Exception {

        LinkedList<SocketChannel> clients = new LinkedList<>();
        // 服务端开启监听：接受客户端
        ServerSocketChannel ss = ServerSocketChannel.open();

        ss.bind(new InetSocketAddress(9090));
        // 让接受客户端  不阻塞
        ss.configureBlocking(false);


        while (true) {
            // 循环第一步：接受客户端的连接
            // Thread.sleep(1000);
            // 不会阻塞，没有返回 系统层面：-1 Java：NULL
            SocketChannel client = ss.accept();
            // accept调用内核：
            // 如果没有客户端连接，在BIO的时候一直卡着，但是在NIO不卡着，返回值 -1，NULL(Java中返回null)；
            // 如果来客户端的连接，accept返回的是这个客户端的fd（例如fd5），Object(Java中返回是对象)。
            // 设置 NONBLOCKING，ss.configureBlocking(false)  就是代码能往下走了。

            if (client == null) {
                System.out.println("null.....");
            } else {
                //  两个socket：服务端listen的socket，连接socket，连接后数据读写使用。
                //  服务端listen的socket，在连接请求三次握手后，通过accept得到连接的socket。
                client.configureBlocking(false);
                int port = client.socket().getPort();
                System.out.println("feign..port: " + port);
                clients.add(client);
            }

            ByteBuffer buffer = ByteBuffer.allocateDirect(4096);

            // 循环第二步：遍历已经链接进来的客户端能不能读写数据
            // 串行化！！！！  多线程！！
            for (SocketChannel c : clients) {
                // >0  -1  0   // 不会阻塞
                int num = c.read(buffer);
                if (num > 0) {
                    buffer.flip();
                    byte[] aaa = new byte[buffer.limit()];
                    buffer.get(aaa);

                    String b = new String(aaa);
                    System.out.println(c.socket().getPort() + " : " + b);
                    buffer.clear();
                }
            }
        }
    }

}
