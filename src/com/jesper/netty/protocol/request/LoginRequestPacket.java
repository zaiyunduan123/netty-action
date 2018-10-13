package com.jesper.netty.protocol.request;


import com.jesper.netty.protocol.Packet;
import lombok.Data;

import static com.jesper.netty.protocol.command.Command.LOGIN_REQUEST;

@Data
public class LoginRequestPacket extends Packet {

    private String userId;

    private String username;

    private String password;



    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
