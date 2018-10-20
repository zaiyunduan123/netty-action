package com.jesper.netty.client.handler;

import com.jesper.netty.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * 客户端接受消息后的处理
 *
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    /**
     * 客户端接收到信息直接打印
     * @param channelHandlerContext
     * @param messageResponsePacket
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageResponsePacket messageResponsePacket) throws Exception {
        String fromUserId = messageResponsePacket.getFromUserId();
        String fromUserName = messageResponsePacket.getFromUserName();
        System.out.println(fromUserId + ":" + fromUserName + " -> " + messageResponsePacket .getMessage());
    }
}
