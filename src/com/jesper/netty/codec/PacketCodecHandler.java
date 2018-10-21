package com.jesper.netty.codec;

import com.jesper.netty.protocol.Packet;
import com.jesper.netty.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;
/**
 * MessageToMessageCodec可以让我们的编解码操作放到一个类里面去实现，定义一个 PacketCodecHandler,他是一个无状态的 handler，因此可以使用单例模式来实现。
 */
@ChannelHandler.Sharable
public class PacketCodecHandler extends MessageToMessageCodec<ByteBuf, Packet> {

    public static final PacketCodecHandler INSATNCE = new PacketCodecHandler();

    private PacketCodecHandler(){
    }


    // 解码
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
         list.add(PacketCodec.INSTANCE.decode(byteBuf));
    }

    // 编码
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, List<Object> list) throws Exception {
        ByteBuf byteBuf = ctx.channel().alloc().ioBuffer();
        PacketCodec.INSTANCE.encode(byteBuf, packet);
        list.add(byteBuf);
    }
}
