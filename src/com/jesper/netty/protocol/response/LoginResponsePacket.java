package com.jesper.netty.protocol.response;

import com.jesper.netty.protocol.Packet;

import static com.jesper.netty.protocol.command.Command.LOGIN_RESPONSE;

public class LoginResponsePacket extends Packet{

    private boolean success;

    private String reason;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
