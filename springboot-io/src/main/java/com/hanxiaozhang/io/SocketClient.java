package com.hanxiaozhang.io;

import java.io.*;
import java.net.Socket;

/**
 * 功能描述: <br>
 * 〈〉
 *
 * @Author:hanxinghua
 * @Date: 2021/8/15
 */
public class SocketClient {

    public static void main(String[] args) {

        try {
            Socket client = new Socket("10.0.8.11",9090);

            client.setSendBufferSize(20);
            // Nagle算法通过减少需要传输的数据包，来优化网络。默认是false，表示开启
            client.setTcpNoDelay(true);
            //设置为true时，表示支持发送一个字节的TCP紧急数据。默认是false。
            // 为false的这种情况下，当接收方收到紧急数据时不作任何处理，直接将其丢弃。
            // 如果用户希望发送紧急数据，应该把OOBINLINE设为true
            client.setOOBInline(false);
            OutputStream out = client.getOutputStream();

            InputStream in = System.in;
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            while(true){
                String line = reader.readLine();
                if(line != null ){
                    byte[] bb = line.getBytes();
                    for (byte b : bb) {
                        out.write(b);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
