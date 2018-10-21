package com.jesper.netty.server;

import com.jesper.netty.codec.PacketCodecHandler;
import com.jesper.netty.codec.PacketDecoder;
import com.jesper.netty.codec.PacketEncoder;
import com.jesper.netty.codec.Spliter;
import com.jesper.netty.handler.IMIdleStateHandler;
import com.jesper.netty.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.util.Date;


public class NettyServer {

    private static final int PORT = 8089;

    public static void main(String[] args) {
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(boosGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        // 空闲检测
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        ch.pipeline().addLast(new Spliter());// 维持每个 channel 当前读到的数据，是有状态的
                        ch.pipeline().addLast(PacketCodecHandler.INSATNCE); // MessageToMessageCodec合并编解码
                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        // 服务端回复心跳,由于 HeartBeatRequestHandler 的处理其实是无需登录的，所以放置在 AuthHandler 前面
                        ch.pipeline().addLast(HeartBeatRequestHandler.INSTANCE);

                        ch.pipeline().addLast(AuthHandler.INSTANCE);
                        ch.pipeline().addLast(IMHandler.INSTANCE);
                    }
                });
        bind(serverBootstrap, PORT);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + "端口[" + port + "]绑定成功!");
            } else {
                System.err.println("端口[" + port + "]绑定失败!");
            }
        });
    }
}
