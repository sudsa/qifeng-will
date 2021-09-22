package com.hanxiaozhang.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * 功能描述: <br>
 * 〈SocketBIO与SocketClient在JDK1.4演示BIO〉
 *
 * @Author:hanxinghua
 * @Date: 2021/8/15
 */
public class SocketBIO {


    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(9090, 20);

        System.out.println("step1: new ServerSocket(9090) ");

        while (true) {
            // 阻塞1
            Socket client = server.accept();
            System.out.println("step2:feign\t" + client.getPort());

            new Thread(new Runnable() {

                @Override
                public void run() {
                    InputStream in = null;
                    try {
                        in = client.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        while (true) {
                            // 阻塞2
                            String dataline = reader.readLine();
                            if (null != dataline) {
                                System.out.println(dataline);
                            } else {
                                client.close();
                                break;
                            }
                        }
                        System.out.println("客户端断开");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    }


}
