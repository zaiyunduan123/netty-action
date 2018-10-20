package com.jesper.netty.protocol.request;

import com.jesper.netty.protocol.Packet;
import lombok.Data;

import static com.jesper.netty.protocol.command.Command.JOIN_GROUP_REQUEST;

@Data
public class JoinGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return JOIN_GROUP_REQUEST;
    }
}
