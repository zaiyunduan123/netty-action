package com.jesper.netty.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
@Data
public abstract class Packet {

    /**
     * 协议版本
     */
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;

    /**
     * 数据内容
     * @return
     */
    @JSONField(serialize = false)
    public abstract Byte getCommand();
}
