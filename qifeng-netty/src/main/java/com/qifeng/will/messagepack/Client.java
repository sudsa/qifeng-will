package com.hanxiaozhang.messagepack;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author hanxinghua
 * @create 2020/10/21
 * @since 1.0.0
 */
public class Client {

    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        new Client().connect("127.0.0.1", port);
    }

    public void connect(String ip, int port) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast("frame decoder", new LengthFieldBasedFrameDecoder(65535, 0, 4, 0, 4))
                                    .addLast("messagePack decoder", new MessagePackDecoder())
                                    .addLast("frame encoder", new LengthFieldPrepender(4, false))
                                    .addLast("messagePack encoder", new MessagePackEncoder())
                                    .addLast(new ClientHandler());
                        }
                    });

            ChannelFuture f = b.connect(ip, port).sync();

            f.channel().closeFuture().sync();

        } finally {
            group.shutdownGracefully();
        }
    }

}


