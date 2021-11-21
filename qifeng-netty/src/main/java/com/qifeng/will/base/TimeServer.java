package com.qifeng.will.base;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 〈一句话功能简述〉<br>
 * 〈时间服务端〉
 *
 * @author howill.zou
 * @create 2020/10/20
 * @since 1.0.0
 */
public class TimeServer {

    public static void main(String[] args) throws Exception {
        int port = 8080;
        new TimeServer().bind(port);
    }


    public void bind(int port) throws Exception {

        // 配置服务端的NIO线程组，一个用于服务端接受客户端的链接，另一个用于进行SocketChannel的网络读写
        // bossGroup在服务器一启动就开始工作，负责监听客户端的连接请求。
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 当建立连接后，就交给了workGroup进行处理
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 创建ServerBootstrap对象，netty用于启动NIO服务端的辅助启动类，为了降低服务端的开发复杂度
            ServerBootstrap b = new ServerBootstrap();

            // 将两个NIO线程组传递到ServerBootstrap中
            b.group(bossGroup, workerGroup)
                    // 创建Channel为NioServerSocketChannel,它的功能对应JDK NIO中的ServerSocketChannel
                    .channel(NioServerSocketChannel.class)
                    // 将backlog设置为1024
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 绑定I/O事件的处理类ChildChannelHandler
                    .childHandler(new ChannelInitializer<SocketChannel>(){
                        @Override
                        protected void initChannel(SocketChannel arg0) throws Exception {
                            arg0.pipeline().addLast(new TimeServerHandler());
                        }
                    });

            // 绑定端口，并调用同步阻塞方法sync()
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
