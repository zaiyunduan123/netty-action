package com.jesper.netty.server.handler.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class InBoundHandlerA extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("InBoundHandlerA:" + msg);
        //父类的 channelRead() 方法会自动调用到下一个 inBoundHandler 的 channelRead() 方法，并且会把当前 inBoundHandler 里处理完毕的对象传递到下一个 inBoundHandler
        super.channelRead(ctx, msg);
    }
}
