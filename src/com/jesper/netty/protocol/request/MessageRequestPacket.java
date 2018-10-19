package com.jesper.netty.protocol.request;

import com.jesper.netty.protocol.Packet;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.jesper.netty.protocol.command.Command.MESSAGE_REQUEST;

@Data
@NoArgsConstructor
public class MessageRequestPacket extends Packet{

    private String message;

    public MessageRequestPacket(String message) {
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
