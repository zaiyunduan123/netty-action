package com.jesper.netty.client.handler;

import com.jesper.netty.protocol.request.HeartBeatRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {

    private static final int HEARTBEAT_INTERVAL = 5;//每隔 5 秒，向服务端发送一个心跳数据包

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //scheduleAtFixedRate()，类似 jdk 的定时任务机制，可以每隔一段时间执行一个任务
        ctx.executor().scheduleAtFixedRate(() -> {
            ctx.writeAndFlush(new HeartBeatRequestPacket());
        }, HEARTBEAT_INTERVAL, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);

        super.channelActive(ctx);
    }
}
