package com.jesper.netty.protocol.request;

import com.jesper.netty.protocol.Packet;
import lombok.Data;

import static com.jesper.netty.protocol.command.Command.LIST_GROUP_MEMBERS_REQUEST;

@Data
public class ListGroupMembersRequestPacket extends Packet{

    private String groudId;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_REQUEST;
    }
}
