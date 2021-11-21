package com.qifeng.will.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

/**
 * 功能描述: <br>
 * 〈C10K问题〉
 *  没有两个公网ip，没有演示
 *  SocketBIO与C10Kclient
 *
 * @Author:hanxinghua
 * @Date: 2021/8/15
 */
public class C10Kclient {

    public static void main(String[] args) {
        LinkedList<SocketChannel> clients = new LinkedList<>();
        InetSocketAddress serverAddr = new InetSocketAddress("82.156.10.221", 9090);

        //端口号的问题：65535
        //  windows
        for (int i = 10000; i < 65000; i++) {
            try {
                SocketChannel client1 = SocketChannel.open();
                // 一个IP地址
                client1.bind(new InetSocketAddress("192.168.150.1", i));
                //  192.168.150.1：10000   192.168.150.11：9090
                client1.connect(serverAddr);
                clients.add(client1);


//                SocketChannel client2 = SocketChannel.open();
//                // 另一个IP地址
//                client2.bind(new InetSocketAddress("192.168.110.100", i));
//                //  192.168.110.100：10000  192.168.150.11：9090
//                client2.connect(serverAddr);
//                clients.add(client2);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        System.out.println("clients "+ clients.size());

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
