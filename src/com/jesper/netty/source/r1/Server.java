package com.jesper.netty.source.r1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by jiangyunxiong on 2018/7/30.
 */
public class Server {

    private ServerSocket serverSocket;

    public Server(int port){
        try{
            this.serverSocket = new ServerSocket(port);
            System.out.println("服务端启动成功，端口:" + port);
        }catch (IOException exception){
            System.out.println("服务端启动失败");
        }
    }

    public void start(){ //放在单独线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Socket client = serverSocket.accept();//accept是个阻塞方法，只有当客户端过来后才会创建Socket
                        new ClientHandler(client).start();
                    }catch (IOException e){
                        System.out.println("服务端异常");
                    }

                }
            }
        }).start();
    }


}
