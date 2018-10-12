package com.jesper.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

public class FirstClientHandler extends ChannelInboundHandlerAdapter{

    /**
     * 这个方法会在客户端连接建立成功之后被调用
     * 编写向服务端写数据的逻辑
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println(new Date() + "：客户端写出数据");

        //1. 获取数据
        ByteBuf buffer = getByteBuf(ctx);

        //2. ctx.channel().writeAndFlush() 把数据写到服务端
        ctx.channel().writeAndFlush(buffer);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx){

        //1. ctx.alloc() 获取到一个 ByteBuf 的内存管理器，再通过buffer（）分配一个 ByteBuf，
        ByteBuf buffer = ctx.alloc().buffer();

        //2. 准备数据，指定字符串的字符集 utf-8
        byte[] bytes = "jesper-client,hello".getBytes(Charset.forName("utf-8"));

        //3. 把字符串的二进制数据填充到 ByteBuf
        buffer.writeBytes(bytes);

        return buffer;
    }
}
