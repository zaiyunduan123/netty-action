package com.jesper.netty.util;

import com.jesper.netty.attibute.Attributes;
import com.jesper.netty.session.Session;
import io.netty.channel.Channel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {
    // userId->channel 的映射
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    /**
     * 保存useId->channel的映射关系，并且给channel附上一个属性,该属性就是当前用户的session
     * @param session
     * @param channel
     */
    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    /**
     * 删除useId->channel 的映射关系
     * @param channel
     */
    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    /**
     * 判断用户是否登录只需要判断当前是否有用户的会话信息
     */
    public static boolean hasLogin(Channel channel) {
        return channel.hasAttr(Attributes.SESSION);
    }

    /**
     * 通过channel获取当前用户的session，其实是取出channel的session属性
     * @param channel
     * @return
     */
    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }


    public static Channel getChannel(String userId) {
        return userIdChannelMap.get(userId);
    }
}
