package com.jesper.netty.server.handler;

import com.jesper.netty.protocol.request.GroupMessageRequestPacket;
import com.jesper.netty.protocol.response.GroupMessageResponsePacket;
import com.jesper.netty.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

@ChannelHandler.Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {

    public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();

    private GroupMessageRequestHandler(){

    }

    @Override
    protected void channelRead0(ChannelHandlerContext cxt, GroupMessageRequestPacket requestPacket) throws Exception {
        // 1. 拿到groupId构造群聊消息的响应
        String groupId = requestPacket.getToGroupId();
        GroupMessageResponsePacket responsePacket = new GroupMessageResponsePacket();
        responsePacket.setFromGroupId(groupId);
        responsePacket.setMessage(groupId);
        responsePacket.setFromUser(SessionUtil.getSession(cxt.channel()));

        // 2. 拿到群聊对应的 channelGroup, 写到每个客户端
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.writeAndFlush(responsePacket);
    }
}
