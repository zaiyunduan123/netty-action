package com.jesper.netty.protocol.response;

import com.jesper.netty.protocol.Packet;
import com.jesper.netty.session.Session;
import lombok.Data;

import static com.jesper.netty.protocol.command.Command.GROUP_MESSAGE_RESPONSE;

@Data
public class GroupMessageResponsePacket extends Packet {

    private String fromGroupId;

    private Session fromUser;

    private String message;

    @Override
    public Byte getCommand() {

        return GROUP_MESSAGE_RESPONSE;
    }
}

