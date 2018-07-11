package com.jesper.netty.debug;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by jiangyunxiong on 2018/7/11.
 * <p>
 * 通过debug分析Netty的绑定原理
 */
public class SimpleServer {

    public static void main(String[] args) throws Exception {

        /**
         * EventLoopGroup 就是一个死循环，不停地检测IO事件，处理IO事件，执行任务
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            /**
             * ServerBootstrap 是服务端的一个启动辅助类，通过给他设置一系列参数来绑定端口启动服务
             */
            ServerBootstrap b = new ServerBootstrap();

            /**
             * group(bossGroup, workerGroup) 我们需要两种类型的人干活，
             * 一个是老板，一个是工人，老板负责从外面接活，接到的活分配给工人干，
             * 放到这里，bossGroup的作用就是不断地accept到新的连接，将新的连接丢给workerGroup来处理
             */
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)//channel就是一个连接或者一个服务端bind动作,原理是 使用反射原理创建NioServerSocketChannel对象 相当于new出一个 NioServerSocketChannel对象
                    .handler(new SimpleServerHandler())//表示服务器启动过程中，需要经过哪些流程
                    .childHandler(new ChannelInitializer<SocketChannel>() {//表示一条新的连接进来之后，该怎么处理
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                        }
                    });

            //这里就是真正的启动过程了，绑定8888端口，等待服务器启动完毕，才会进入下行代码
            ChannelFuture f = b.bind(8888).sync();

            //等待服务端关闭socket
            f.channel().closeFuture().sync();


        } finally {
            //关闭两组死循环
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * SimpleServerHandler最终的顶层接口为ChannelHander，
     * 是netty的一大核心概念，表示数据流经过的处理器，可以理解为流水线上的每一道关卡
     */
    private static class SimpleServerHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("channelActive");
        }

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            System.out.println("channelRegistered");
        }

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            System.out.println("handlerAdded");
        }
    }

}
