package com.jesper.netty.client.console;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 对控制台要执行的操作进行管理的管理类
 */
public class ConsoleCommandManager implements ConsoleCommand {

    //把所有要管理的控制台指令都塞到一个 map 中
    private Map<String, ConsoleCommand> consoleCommandMap;

    @Override
    public void exec(Scanner scanner, Channel channel) {
        consoleCommandMap = new HashMap<>();
//        consoleCommandMap.put("sendToUser", )

    }
}
