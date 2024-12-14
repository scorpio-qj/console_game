package org.nico.ratel.client.event;

import io.netty.channel.Channel;
import org.nico.noson.Noson;
import org.nico.ratel.client.SimpleClient;
import org.nico.ratel.client.entity.User;
import org.nico.ratel.commons.ServerEventCode;
import org.nico.ratel.commons.event.BasicEventCode;
import org.nico.ratel.commons.event.BasicEventHandler;
import org.nico.ratel.commons.print.SimplePrinter;
import org.nico.ratel.commons.utils.ChannelUtils;
import org.nico.ratel.commons.utils.ProtoDataUtils;

import java.util.HashMap;
import java.util.Map;


public class ClientBasicHandler_CONNECT extends BasicEventHandler {


    @Override
    public void call(Channel channel, String data) {
        SimplePrinter.printNotice("Connected to server. Welcome to ratel!");
        User.INSTANCE.setId(Long.parseLong(data));

        String param=ProtoDataUtils.mapBuilder().put("version",SimpleClient.VERSION).toString();
        ChannelUtils.pushToServer(channel, BasicEventCode.CS_SET_INFO, param);
    }
}
