package me.longli.demo.nettytest;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class AgentInboundHandler extends SimpleChannelInboundHandler<ByteBuf> {
    public final String name = "AgentInboundHandler";

    /**
     * 由于这个field的存在，这个AgentInboundHandler是不可复用的 —————— 对与ServerChannel而言，每次创建新的subChannel，就要new一个新的AgentInboundHandler实例。
     */
    private ChannelFuture subConnectFuture;

    public AgentInboundHandler() {
        super(false);
    }

    @Override
    public void channelActive(ChannelHandlerContext serverSubCtx) throws Exception {
        // super.channelActive(ctx);
        System.out.println(Thread.currentThread().getName() + "-" + name);
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new SimpleChannelInboundHandler<ByteBuf>() {
            public final String name = "BaiduClientInboundHandler";
            @Override
            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                System.out.println(Thread.currentThread().getName() + "-" + name);
                ByteBuf byteBuf = Unpooled.wrappedBuffer("已经连接到百度".getBytes(StandardCharsets.UTF_8));
                serverSubCtx.write(byteBuf);
            }
            @Override
            protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception { // 虽然连上去了，但没有响应，也不会执行到这里。
                System.out.println(name + "作为客户端接收到消息：" + msg.toString(CharsetUtil.UTF_8));
            }
            @Override
            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                cause.printStackTrace();
                ctx.close(); // 关闭该channel
            }
        });
        bootstrap.group(serverSubCtx.channel().eventLoop());
        subConnectFuture = bootstrap.connect(new InetSocketAddress("www.baidu.com", 80));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println(name + "作为服务端接收到消息：" + msg.toString(CharsetUtil.UTF_8));
        if (subConnectFuture.isDone()) {
            System.out.println("fuck");
        }
        ctx.fireChannelRead(msg);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close(); // 关闭该channel
    }
}
