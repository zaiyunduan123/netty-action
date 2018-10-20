package com.jesper.netty.protocol.request;

import com.jesper.netty.protocol.Packet;
import lombok.Data;

import static com.jesper.netty.protocol.command.Command.QUIT_GROUP_REQUEST;

@Data
public class QuitGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {

        return QUIT_GROUP_REQUEST;
    }
}
