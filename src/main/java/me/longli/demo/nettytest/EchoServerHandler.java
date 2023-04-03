package me.longli.demo.nettytest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import me.longli.demo.WebDemoApplication;
import me.longli.demo.event.MyEvent;

import java.time.LocalDateTime;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("服务端接收到消息：" + in.toString(CharsetUtil.UTF_8));
        ctx.write(in); // 将消息发送回对方

        WebDemoApplication.publisher.publishEvent(new MyEvent(this, "channelRead", LocalDateTime.now()));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ChannelFuture channelFuture = ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);// 将未决消息冲刷到远程节点
        channelFuture.addListener(ChannelFutureListener.CLOSE); // 并且关闭该Channel

        WebDemoApplication.publisher.publishEvent(new MyEvent(this, "channelReadComplete", LocalDateTime.now()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close(); // 关闭该channel

        WebDemoApplication.publisher.publishEvent(new MyEvent(this, "exceptionCaught", LocalDateTime.now()));
    }
}
