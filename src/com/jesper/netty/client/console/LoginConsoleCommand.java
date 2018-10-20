package com.jesper.netty.client.console;

import com.jesper.netty.protocol.request.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class LoginConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        System.out.println("输入用户名登录：");
        loginRequestPacket.setUserName(scanner.nextLine());
        loginRequestPacket.setPassword("abc");

        // 发送登录数据包
        channel.writeAndFlush(loginRequestPacket);
        waitForLoginResponse();
    }

    /**
     * 登录逻辑的最大处理时间
     */
    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
