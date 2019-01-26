package com.jesper.netty.socket;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * 客户端每隔两秒发送一个带有时间戳的 "hello world" 给服务端，服务端收到之后打印。
 */
public class IOClient {

    /**
     * 连接上服务端 8000 端口之后，每隔 2 秒，客户端向服务端写一个带有时间戳的 "hello world"
     */
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Socket socket = new Socket("127.0.0.1", 8000);
                while (true) {
                    try {
                        socket.getOutputStream().write((new Date() + ": hello world").getBytes());
                        Thread.sleep(2000);
                    } catch (Exception e) {
                    }
                }
            } catch (IOException e) {
            }
        }).start();
    }
}