package com.jesper.netty.util;

import com.jesper.netty.attibute.Attributes;
import com.jesper.netty.session.Session;
import io.netty.channel.Channel;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(io.netty.channel.Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public static boolean hasLogin(io.netty.channel.Channel channel) {

        return channel.hasAttr(Attributes.SESSION);
    }

    public static Session getSession(io.netty.channel.Channel channel) {

        return channel.attr(Attributes.SESSION).get();
    }

    public static io.netty.channel.Channel getChannel(String userId) {

        return userIdChannelMap.get(userId);
    }
}
