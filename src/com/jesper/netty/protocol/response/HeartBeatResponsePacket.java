package com.jesper.netty.protocol.response;

import com.jesper.netty.protocol.Packet;

import static com.jesper.netty.protocol.command.Command.HEARTBEAT_RESPONSE;

public class HeartBeatResponsePacket extends Packet {

    @Override
    public Byte getCommand() {
        return HEARTBEAT_RESPONSE;
    }
}
