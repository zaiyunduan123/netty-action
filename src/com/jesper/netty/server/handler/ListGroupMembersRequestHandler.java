package com.jesper.netty.server.handler;

import com.jesper.netty.protocol.request.ListGroupMembersRequestPacket;
import com.jesper.netty.protocol.response.ListGroupMembersResponsePacket;
import com.jesper.netty.session.Session;
import com.jesper.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;

@ChannelHandler.Sharable
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {
    public static final ListGroupMembersRequestHandler INSTANCE = new ListGroupMembersRequestHandler();

    private ListGroupMembersRequestHandler() {

    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ListGroupMembersRequestPacket requestPacket) throws Exception {
        // 1. 获取群的 channelGroup
        String groudId = requestPacket.getGroudId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groudId);

        // 2. 遍历群成员的 channel， 对应的 session，构造群成员的信息
        ArrayList<Session> sessionList = new ArrayList<>();
        for (Channel channel : channelGroup){
            Session session = SessionUtil.getSession(channel);
            sessionList.add(session);
        }

        // 3. 构造获取成员列表响应写回到客户端
        ListGroupMembersResponsePacket responsePacket = new ListGroupMembersResponsePacket();

        responsePacket.setGroupId(groudId);
        responsePacket.setSessionList(sessionList);
        channelHandlerContext.channel().writeAndFlush(responsePacket);

    }
}
