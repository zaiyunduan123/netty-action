package com.jesper.netty.server;

import com.jesper.netty.protocol.request.LoginRequestPacket;
import com.jesper.netty.protocol.Packet;
import com.jesper.netty.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler  extends ChannelInboundHandlerAdapter {

    /**
     * 读取客户端传过来的数据，并做处理
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf requestBtyeBuf = (ByteBuf) msg;

        // 解码
        Packet packet = PacketCodec.INSTANCE.decode(requestBtyeBuf);

        // 判断是否是登录请求数据包
        if (packet instanceof LoginRequestPacket) {

            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            LoginRequestPacket loginResPacket1 = new LoginRequestPacket();

            // 登录校验
            if (valid)
        }


    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
