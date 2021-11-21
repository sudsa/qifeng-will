package com.hanxiaozhang.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 〈一句话功能简述〉<br>
 * 〈时间服务端〉
 *
 *  Handler中 ctx.fireChannelRead() 表示传递读消息至下一个处理器，
 *  其他的类似，例如ctx.fireUserEventTriggered()等等
 *
 * @author hanxinghua
 * @create 2020/10/26
 * @since 1.0.0
 */
public class TestServer {

    public static void main(String[] args) throws Exception {
        int port = 8080;
        new TestServer().bind(port);
    }


    public void bind(int port) throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel arg0) throws Exception {
                            arg0.pipeline().addLast(new TestServerHandler())
                                    // IdleStateHandler只能触发下一个含有userEventTriggered()方法的Handler，（上一个，或者下下一个都不会触发）
                                    .addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS))
                                    .addLast(new TestServerHeartBeatRespOneHandler())
//                                    .addLast(new TestServerHeartBeatRespTwoHandler());

                            ;
                        }
                    });

            ChannelFuture f = b.bind(port).sync();

            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
