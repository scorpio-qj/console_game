package org.nico.ratel.commons.event;

import io.netty.channel.Channel;

/**
 * @author 柴奇君
 * @create 2024/12/5
 * @desc
 */
public abstract class BasicEventHandler {

    public abstract void call(Channel channel, String data);
}
