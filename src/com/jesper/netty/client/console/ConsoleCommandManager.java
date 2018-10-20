package com.jesper.netty.client.console;

import com.jesper.netty.util.SessionUtil;
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

    public ConsoleCommandManager() {
        consoleCommandMap = new HashMap<>();
        consoleCommandMap.put("sendToUser", new SendToUserConsoleCommand());
        consoleCommandMap.put("logout", new LogoutConsoleCommand());
        consoleCommandMap.put("createGroup", new CreateGroupConsoleCommand());

    }

    @Override
    public void exec(Scanner scanner, Channel channel) {
        // 获取第一个指令
        String command = scanner.next();

        if (!SessionUtil.hasLogin(channel)){
            return;
        }

        ConsoleCommand consoleCommand = consoleCommandMap.get(command);

        if (consoleCommand != null){
            consoleCommand.exec(scanner, channel);
        } else{
            System.err.println("无法识别[" + command + "]指令，请重新输入!");
        }
    }
}
