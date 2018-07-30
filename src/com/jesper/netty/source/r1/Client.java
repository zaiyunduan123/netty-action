package com.jesper.netty.source.r1;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by jiangyunxiong on 2018/7/30.
 */
public class Client {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8087;
    private static final int SLEEP_TIME = 5000;

    public static void main(String[] args) throws IOException {
        final Socket socket = new Socket(HOST, PORT);

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("客户端启动成功....");
                while (true) {
                    try {
                        String message = "hello world";
                        System.out.println("客户端发送数据: " + message);
                        socket.getOutputStream().write(message.getBytes());//将数据写到服务端
                    } catch (Exception e) {
                        System.out.println("写数据出错！");
                    }
                    sleep();
                }
            }
        }).start();
    }

    private static void sleep() {
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
