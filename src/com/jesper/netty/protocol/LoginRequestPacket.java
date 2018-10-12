package com.jesper.netty.protocol;

import lombok.Data;

@Data
public class LoginRequestPacket extends Packet {

    private Integer userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return null;
    }
}
