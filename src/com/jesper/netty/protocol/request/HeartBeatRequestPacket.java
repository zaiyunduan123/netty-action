package com.jesper.netty.protocol.request;

import com.jesper.netty.protocol.Packet;

import static com.jesper.netty.protocol.command.Command.HEARTBEAT_REQUEST;

public class HeartBeatRequestPacket extends Packet {

    @Override
    public Byte getCommand() {
        return HEARTBEAT_REQUEST;
    }
}
