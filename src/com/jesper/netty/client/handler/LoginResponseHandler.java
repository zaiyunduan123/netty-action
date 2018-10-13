package com.jesper.netty.client.handler;

import com.jesper.netty.protocol.request.LoginRequestPacket;
import com.jesper.netty.protocol.response.LoginResponsePacket;
import com.jesper.netty.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    /**
     * 给服务端传数据
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端开始登录");

        // 创建登录对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("jesper");
        loginRequestPacket.setPassword("abc");

        //编码这里也不需要了

        // 调用 writeAndFlush() 就能把loginRequestPacket写到服务端
        ctx.channel().writeAndFlush(loginRequestPacket);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginResponsePacket loginResponsePacket) throws Exception {

        // 处理登录
        if (loginResponsePacket.isSuccess()) {
            LoginUtil.markAsLogin(channelHandlerContext.channel());
            System.out.println(new Date() + ": 客户端登录成功");
        } else {
            System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
        }

    }
}
