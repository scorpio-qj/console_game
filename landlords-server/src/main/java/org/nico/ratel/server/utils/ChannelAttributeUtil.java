package org.nico.ratel.server.utils;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

/**
 * @author 柴奇君
 * @create 2024/12/6
 * @desc
 */
public class ChannelAttributeUtil {

    public static <T> T get(Channel channel, AttributeKey<T> key) {
        return channel.attr(key).get();
    }

    public static <T> void set(Channel channel, AttributeKey<T> key, T value) {
        channel.attr(key).set(value);
    }
}
