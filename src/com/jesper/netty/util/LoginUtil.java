package com.jesper.netty.util;

import com.jesper.netty.attibute.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * 给 Channel 绑定一个登录成功的标志位，然后判断是否登录成功的时候取出这个标志位就可以了
 */
public class LoginUtil {

    //给 Channel 绑定一个登录成功的标志位
    public static void markAsLogin(Channel channel){
        channel.attr(Attributes.LOGIN).set(true);
    }

    // 判断是否登录
    public static boolean hasLogin(Channel channel){
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);

        return loginAttr.get() != null;
    }
}
