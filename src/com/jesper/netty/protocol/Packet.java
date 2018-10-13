package com.jesper.netty.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
public abstract class Packet {

    /**
     * 协议版本
     */
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;

    public Byte getVersion() {
        return version;
    }

    public void setVersion(Byte version) {
        this.version = version;
    }

    /**
     * 数据内容
     * @return
     */
    @JSONField(serialize = false)
    public abstract Byte getCommand();
}
