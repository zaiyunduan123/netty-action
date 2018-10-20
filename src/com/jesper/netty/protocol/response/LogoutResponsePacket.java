package com.jesper.netty.protocol.response;

import com.jesper.netty.protocol.Packet;
import lombok.Data;
import static com.jesper.netty.protocol.command.Command.LOGOUT_RESPONSE;


@Data
public class LogoutResponsePacket extends Packet{

    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {
        return LOGOUT_RESPONSE;
    }
}
