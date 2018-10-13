package com.jesper.netty.protocol.response;

import com.jesper.netty.protocol.Packet;
import lombok.Data;

import static com.jesper.netty.protocol.command.Command.LOGIN_RESPONSE;

@Data
public class LoginResponsePacket extends Packet{

    private boolean success;

    private String reason;



    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
