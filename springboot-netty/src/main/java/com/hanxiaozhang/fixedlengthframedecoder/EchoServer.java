package com.hanxiaozhang.fixedlengthframedecoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 功能描述: <br>
 * 〈〉
 *  Tips： 这里使用telnet命令行去测试EchoServer
 *  步骤：Windows电脑找到cmd命令，输入telnet localhost 8080，在Telnet窗口中按下“Ctrl+]”；
 *  然后，输入set localecho，再连续按两次回车键，再输入HTTP请求即可见
 *
 * @Author:hanxinghua
 * @Date: 2020/10/21
 */
public class EchoServer {

    public static void main(String[] args) throws Exception {
        int port = 8080;
        new EchoServer().bind(port);
    }

    public void bind(int port) throws Exception {
        // 配置服务端的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(new FixedLengthFrameDecoder(20));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new EchoServerHandler());
                        }
                    });

            // 绑定端口，同步等待成功
            ChannelFuture f = b.bind(port).sync();

            // 等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } finally {
            // 优雅退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
