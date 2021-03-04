package com.murray.utils;

import com.murray.server.netty.Attributes;
import com.murray.session.Session;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {
    // userId -> channel 的映射
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();
 
 
    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }
 
    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }
    
    public static boolean hasLogin(Channel channel) {
 
        return channel.hasAttr(Attributes.SESSION);
    }
 
    public static Session getSession(Channel channel) {
 
        return channel.attr(Attributes.SESSION).get();
    }
 
    public static Channel getChannel(String userId) {
        return userIdChannelMap.getOrDefault(userId, null);
    }
    public static int getSize( ) {

        return userIdChannelMap.size();
    }
}