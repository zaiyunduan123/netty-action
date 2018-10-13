package com.jesper.netty.attibute;

import io.netty.util.AttributeKey;

public interface Attributes {

    // 定义一下是否登录成功的标志位
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
