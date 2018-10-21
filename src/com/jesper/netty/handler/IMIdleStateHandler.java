package com.jesper.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class IMIdleStateHandler extends IdleStateHandler {

    private static final int READER_IDEL_TIME = 15;//如果 15 秒内没有读到数据，就表示连接假死

    public IMIdleStateHandler(){
        super(READER_IDEL_TIME, 0, 0, TimeUnit.SECONDS);
    }

    /**
     * 连接假死之后会回调 channelIdle() 方法,这个方法里面打印消息，并手动关闭连接。
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        System.out.println(READER_IDEL_TIME + "秒内未读到数据，关闭连接");
        ctx.channel().close();
    }
}
