package org.nico.ratel.commons.event;

import io.netty.channel.Channel;
import org.nico.ratel.commons.clientactor.BasicActor;
import org.nico.ratel.commons.print.SimplePrinter;

public class DefaultEventHandler extends BasicEventHandler{


    @Override
    public void call(Channel channel, String data) {
        SimplePrinter.serverLog("客户端响应事件默认处理器");
    }

    @Override
    public void call(BasicActor actor, String data) {
        SimplePrinter.serverLog("服务端响应事件默认处理器");
    }
}
