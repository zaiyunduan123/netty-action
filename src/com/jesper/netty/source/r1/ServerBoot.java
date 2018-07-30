package com.jesper.netty.source.r1;

/**
 * Created by jiangyunxiong on 2018/7/30.
 */
public class ServerBoot {

    private static final int PORT = 8087;

    public static void main(String[] args) {
        Server server = new Server(PORT);
        server.start();
    }
}
