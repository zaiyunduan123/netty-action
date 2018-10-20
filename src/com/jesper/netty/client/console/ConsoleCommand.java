package com.jesper.netty.client.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 把在控制台要执行的操作抽象出来，抽象出一个接口
 */
public interface ConsoleCommand {
    void exec(Scanner scanner, Channel channel);
}
