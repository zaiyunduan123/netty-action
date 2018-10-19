package com.jesper.netty.attibute;

import com.jesper.netty.session.Session;
import io.netty.util.AttributeKey;

public interface Attributes {

    // 定义一下是否登录成功的标志位
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
