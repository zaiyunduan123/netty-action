package com.jesper.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by jiangyunxiong on 2018/6/20.
 */
public class MultiplexerTimeServer implements Runnable {

    private Selector selector;

    private ServerSocketChannel serverChannel;

    private volatile boolean stop;

    //资源初始化
    public MultiplexerTimeServer(int port) {
        try {
            selector = Selector.open();
            //开启所有客户端连接的父管道
            serverChannel = ServerSocketChannel.open();
            //开启非阻塞模式
            serverChannel.configureBlocking(false);
            //绑定监听端口
            serverChannel.socket().bind(new InetSocketAddress(port), 1024);
            //注册到多路复用器Selector上，监听ACCEPT事件
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("The time server is start in port : " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        /**
         * 无限循环体内论询准备就绪的Key
         */
        while (!stop) {
            try {
                selector.select(1000);//休眠时间1s，selector每隔1s被唤醒一次
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        // 多路复用器关闭后，所有注册在上面的Channel和Pipe等资源都会被自动去注册并关闭，所以不需要重复释放资源
        if (selector != null)
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            //建立物理链路，相当完成了tcp三次握手
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            //将新接入的客户端注册到多路复用器Selector上，监听read事件，读取客户端发送的网络消息
            socketChannel.register(selector, SelectionKey.OP_READ);
        }
        if (key.isReadable()){
            SocketChannel socketChannel = (SocketChannel) key.channel();
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            //异步读取客户端请求消息到缓冲区
            int readBytes = socketChannel.read(readBuffer);
            if (readBytes > 0){
                readBuffer.flip();
                byte[] bytes = new byte[readBuffer.remaining()];
                readBuffer.get(bytes);
                String body = new String(bytes, "UTF-8");
                System.out.println("The time server receive order : "
                        + body);
                String currentTime = "QUERY TIME ORDER"
                        .equalsIgnoreCase(body) ? new java.util.Date(
                        System.currentTimeMillis()).toString()
                        : "BAD ORDER";
                doWrite(socketChannel, currentTime);
            }else if (readBytes < 0){
                key.cancel();
                socketChannel.close();
            }else
                ;//读到0字节，忽略
        }
    }
    private void doWrite(SocketChannel channel, String response)
            throws IOException {
        if (response != null && response.trim().length() > 0) {
            byte[] bytes = response.getBytes();
            //将pojo对象encode成ByteBuffer
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            //调用write接口，将消息异步发送到客户端
            channel.write(writeBuffer);
            /**
             * 如果发送区TCP缓冲区满，会导致写半包，此时需要注册监听写操作位，循环写，直到整包消息写入TCP缓冲区
             */
        }
    }
}
