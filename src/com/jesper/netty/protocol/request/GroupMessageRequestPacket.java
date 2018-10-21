package com.jesper.netty.protocol.request;

import com.jesper.netty.protocol.Packet;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.jesper.netty.protocol.command.Command.GROUP_MESSAGE_REQUEST;

@Data
@NoArgsConstructor
public class GroupMessageRequestPacket extends Packet {

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_REQUEST;
    }
}
