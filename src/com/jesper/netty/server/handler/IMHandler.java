package com.jesper.netty.server.handler;

import com.jesper.netty.protocol.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

import static com.jesper.netty.protocol.command.Command.*;

/**
 * 缩短事件传播路径
 * 压缩 handler - 合并平行 handler
 */
@ChannelHandler.Sharable
public class IMHandler extends SimpleChannelInboundHandler<Packet>{

    public static final IMHandler INSTANCE = new IMHandler();

    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    private IMHandler(){
        handlerMap = new HashMap<>();

        handlerMap.put(LOGIN_REQUEST, LoginRequestHandler.INSTANCE);
        handlerMap.put(MESSAGE_REQUEST, MessageRequestHandler.INSTANCE);
        handlerMap.put(JOIN_GROUP_REQUEST, JoinGroupRequestHandler.INSTANCE);
        handlerMap.put(QUIT_GROUP_REQUEST, QuitGroupRequestHandler.INSTANCE);
        handlerMap.put(LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestHandler.INSTANCE);
        handlerMap.put(GROUP_MESSAGE_REQUEST, GroupMessageRequestHandler.INSTANCE);
        handlerMap.put(LOGOUT_REQUEST, LogoutRequestHandler.INSTANCE);
    }

    /**
     * channelRead0内部会做指令类型转换，最终调用到每个指令 handler 的 channelRead0() 方法
     * @param channelHandlerContext
     * @param packet
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet packet) throws Exception {
        handlerMap.get(packet.getCommand()).channelRead(channelHandlerContext, packet);
    }
}
