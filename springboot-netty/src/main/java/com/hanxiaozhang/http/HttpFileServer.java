package com.hanxiaozhang.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 功能描述: <br>
 * 〈〉
 *
 * @Author:hanxinghua
 * @Date: 2020/10/22
 */
public class HttpFileServer {

    private static final String DEFAULT_URL = "/springboot-netty/src/main/java/com/hanxiaozhang";

    public static void main(String[] args) throws Exception {
        int port = 8080;
        String url = DEFAULT_URL;
        new HttpFileServer().run(port, url);
    }

    public void run(final int port, final String url) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 请求消息解码器
                            ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                            // 目的是将多个消息转换为单一的request或者response对象
                            ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
                            // 响应解码器
                            ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                            // 目的是支持异步大文件传输
                            ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                            // 业务逻辑
                            ch.pipeline().addLast("fileServerHandler", new HttpFileServerHandler(url));
                        }
                    });
            // netty 所绑定的IP只能是其所在机器（无论物理机还是虚拟机）上的某个网卡的IP地址，Windows 可以通过ifconfig命令查询
            ChannelFuture future = b.bind("172.30.161.33", port).sync();
            System.out.println("HTTP文件目录服务器启动，网址是 : " + "http://172.30.161.33:" + port + url);
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
