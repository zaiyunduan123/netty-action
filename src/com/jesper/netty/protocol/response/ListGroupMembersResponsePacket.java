package com.jesper.netty.protocol.response;


import com.jesper.netty.protocol.Packet;
import com.jesper.netty.session.Session;
import lombok.Data;

import java.util.List;

import static com.jesper.netty.protocol.command.Command.LIST_GROUP_MEMBERS_RESPONSE;

@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;

    private List<Session> sessionList;

    @Override
    public Byte getCommand() {

        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}
