package com.jesper.netty.source.r1;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by jiangyunxiong on 2018/7/30.
 */
public class ClientHandler {

    public static final int MAX_DATA_LEN = 1024;
    private final Socket socket;

    public ClientHandler(Socket socket){//保存客户端创建的Socket
        this.socket = socket;
    }

    public void start(){
        System.out.println("新客户端接入");
        new Thread(new Runnable() {
            @Override
            public void run() {
                doStart();
            }
        }).start();
    }

    private void doStart() {
         try{
             InputStream inputStream = socket.getInputStream();//拿到客户端的输入流
             while (true){
                 byte[] data = new byte[MAX_DATA_LEN];
                 int len;
                 while ((len = inputStream.read(data)) != -1) { //去读数据
                     String message = new String(data, 0, len);
                     System.out.println("客户端传来消息: " + message);
                     socket.getOutputStream().write(data); //将数据通过输出流输出回去
                 }
             }
         }catch (IOException e){
             e.printStackTrace();
         }
    }
}
