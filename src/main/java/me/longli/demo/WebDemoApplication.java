package me.longli.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import lombok.extern.slf4j.Slf4j;
import me.longli.demo.nettytest.EchoServerHandler;
import me.longli.demo.state_machine.MyOrderEvent;
import me.longli.demo.state_machine.MyOrderState;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.statemachine.StateMachine;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@EnableRetry
@Slf4j
@SpringBootApplication
public class WebDemoApplication implements CommandLineRunner{

    public static ApplicationEventPublisher publisher;

    @Autowired
    private StateMachine<MyOrderState, MyOrderEvent> orderMachine;

    public static void main(String[] args) throws InterruptedException {
        publisher = SpringApplication.run(WebDemoApplication.class, args);

        final DefaultEventExecutorGroup defaultEventExecutorGroup = new DefaultEventExecutorGroup(2, new ThreadFactory() {
            private final AtomicInteger threadIndex = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "NettyServerCodecThread_" + this.threadIndex.incrementAndGet());
            }
        });

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(9999))
                    .childHandler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new EchoServerHandler());
                            // 每隔30秒，检查是否读空闲，并发送事件
                            ch.pipeline().addLast(defaultEventExecutorGroup, new IdleStateHandler(30, 0, 0));
                            ch.pipeline().addLast(defaultEventExecutorGroup, new ChannelInboundHandlerAdapter() {
                                @Override
                                public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                    if (evt instanceof IdleStateEvent) {
                                        IdleStateEvent event = (IdleStateEvent) evt;
                                        if (event.state().equals(IdleState.READER_IDLE)) {
                                            final String remoteAddress = WebDemoApplication.parseChannelRemoteAddr(ctx.channel());
                                            System.out.println("NETTY SERVER PIPELINE: IDLE exception [{" + remoteAddress + "}]");
                                            WebDemoApplication.closeChannel(ctx.channel());
                                        }
                                    }

                                    ctx.fireUserEventTriggered(evt);
                                }
                            });
                        }
                    });
            ChannelFuture channelFuture = b.bind().sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully().sync();
        }
    }

    public static String parseChannelRemoteAddr(final Channel channel) {
        if (null == channel) {
            return "";
        }
        SocketAddress remote = channel.remoteAddress();
        final String addr = remote != null ? remote.toString() : "";

        if (addr.length() > 0) {
            int index = addr.lastIndexOf("/");
            if (index >= 0) {
                return addr.substring(index + 1);
            }

            return addr;
        }

        return "";
    }

    public static void closeChannel(Channel channel) {
        final String addrRemote = WebDemoApplication.parseChannelRemoteAddr(channel);
        channel.close().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                log.info("closeChannel: close the connection to remote address[{}] result: {}", addrRemote,
                        future.isSuccess());
            }
        });
    }

    @Retryable
    @Override
    public void run(String... args) throws Exception {
        orderMachine.sendEvent(MyOrderEvent.addToCart);
        orderMachine.sendEvent(MyOrderEvent.placeOrder);
        orderMachine.sendEvent(MyOrderEvent.pay);
        orderMachine.sendEvent(MyOrderEvent.pay);
    }
}
