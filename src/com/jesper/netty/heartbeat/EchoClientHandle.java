package com.jesper.netty.heartbeat;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by jiangyunxiong on 2018/6/27.
 *
 * EchoClientHandle是用来检测客户端的读取超时的处理器
 */
public class EchoClientHandle extends SimpleChannelInboundHandler<ByteBuf> {

    private final static Logger LOGGER = LoggerFactory.getLogger(EchoClientHandle.class);

    //当有操作操作超出指定空闲秒数时，便会触发UserEventTriggered事件
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent){
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;

            // 客户端写，服务端读，判断客户端写状态是idle了
            if (idleStateEvent.state() == IdleState.WRITER_IDLE){
                LOGGER.info("已经10秒没有发送信息!");
                //向服务端发送消息

            }
        }

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {

    }
}
