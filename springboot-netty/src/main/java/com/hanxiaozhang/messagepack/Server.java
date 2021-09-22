package com.hanxiaozhang.messagepack;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *  Tips:
 *  1.netty接收和传递信息都是经过ByteBuf进行的
 *
 * @author hanxinghua
 * @create 2020/10/21
 * @since 1.0.0
 */
public class Server {


    public static void main(String[] args) throws Exception {
        int port = 8080;
        new Server().bind(port);
    }

    public void bind(int port) throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                     // 长连接定义队列大小
                    .option(ChannelOption.SO_BACKLOG, 100)
                     // 日志打印设置
                    .childHandler(new LoggingHandler(LogLevel.INFO))
                     // 设置childHandler执行所有的连接请求
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("frame decoder", new LengthFieldBasedFrameDecoder(65535, 0, 4, 0, 4));
                            ch.pipeline().addLast("messagePack decoder",new MessagePackDecoder());
                            ch.pipeline().addLast("frame encoder",new LengthFieldPrepender(4, false));
                            ch.pipeline().addLast("messagePack encoder",new MessagePackEncoder());
                            ch.pipeline().addLast(new ServerHandler());
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
