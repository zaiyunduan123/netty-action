package com.jesper.netty.server.handler;

import com.jesper.netty.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {
    public static final AuthHandler INSTANCE = new AuthHandler();

    private AuthHandler() {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtil.hasLogin(ctx.channel())){
            ctx.channel().close();//如果未登录，直接强制关闭连接
        }else{
            // 如果已经经过权限认证，那么就直接调用 pipeline 的 remove() 方法删除自身
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }



}
