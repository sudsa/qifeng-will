package com.hanxiaozhang.privateprotocol.server;

import com.hanxiaozhang.privateprotocol.codec.NettyMessageDecoder;
import com.hanxiaozhang.privateprotocol.codec.NettyMessageEncoder;
import com.hanxiaozhang.privateprotocol.constant.NettyConstant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * 功能描述: <br>
 * 〈〉
 *
 * @Author:hanxinghua
 * @Date: 2020/10/23
 */
@Slf4j
public class NettyServer {


    public static void main(String[] args) throws Exception {
        new NettyServer().bind();
    }

    public void bind() throws Exception {

        // 配置服务端的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                // Tcp/Ip协议，函数Listen初始化服务端可连接队列的大小
                .option(ChannelOption.SO_BACKLOG, 100)
                // Server启动时，配置LoggingHandler
                .handler(new LoggingHandler(LogLevel.INFO))
                // 连接建立后，配置ChannelInitializer
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws IOException {
                        ch.pipeline()
                                // Netty消息解码器Handler
                                .addLast(new NettyMessageDecoder(1024 * 1024, 4, 4))
                                // Netty消息编码器Handler
                                .addLast(new NettyMessageEncoder())
                                // 读超时Handler
                                .addLast("readTimeoutHandler", new ReadTimeoutHandler(50))
                                // 登录权限请求响应Handler
                                .addLast(new LoginAuthRespHandler())
                                // 心跳检测请求响应Handler
                                .addLast("HeartBeatHandler", new HeartBeatRespHandler());
                    }
                });

        // 绑定端口，同步等待成功
        b.bind(NettyConstant.REMOTE_IP, NettyConstant.PORT).sync();
        log.info("Netty server start ok : " + (NettyConstant.REMOTE_IP + " : " + NettyConstant.PORT));

    }

}
