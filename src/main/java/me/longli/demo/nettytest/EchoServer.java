package me.longli.demo.nettytest;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;

public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        final EchoServerHandler myHandler = new EchoServerHandler();
        final EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            ServerBootstrap b1 = b.group(group);
            ServerBootstrap b2 = b.channel(NioServerSocketChannel.class); // 指定使用NIO传输channel
            ServerBootstrap b3 = b.localAddress(port); // 指定端口
            ServerBootstrap b4 = b.childHandler(new ChannelInitializer<>() { // 将channelHandler添加到channelPipeline // TODO 与 b.childHandler(new EchoServerHandler()); 的区别在哪？
                @Override
                protected void initChannel(Channel ch) throws Exception { // 每次接到新的连接，都会执行到这里。// ChannelInitializer 更像是一个 ChannelHandler 的延迟初始化器？
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(myHandler);
                }
            });
            ChannelFuture bindFuture = b.bind(); // 异步地绑定服务器
            ChannelFuture syncBindFuture = bindFuture.sync(); // 同步等待

            Channel channel = syncBindFuture.channel();
            ChannelFuture closeFuture = channel.closeFuture();
            ChannelFuture syncCloseFuture = closeFuture.sync(); // TODO 什么时候会执行close呢？

            System.out.println("EchoServer end. b=" + (b==b1 && b1==b2 && b2==b3 && b3==b4));
        } finally {
            Future<?> shutdownFuture = group.shutdownGracefully();
            shutdownFuture.sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int port = Integer.parseInt(args[0]);
        EchoServer server = new EchoServer(port);
        server.start();
    }
}
