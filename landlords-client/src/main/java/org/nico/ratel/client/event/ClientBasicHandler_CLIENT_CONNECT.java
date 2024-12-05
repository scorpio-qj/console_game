package org.nico.ratel.client.event;

import io.netty.channel.Channel;
import org.nico.noson.Noson;
import org.nico.ratel.client.SimpleClient;
import org.nico.ratel.commons.ServerEventCode;
import org.nico.ratel.commons.event.BasicClientEventHandler;
import org.nico.ratel.commons.print.SimplePrinter;
import org.nico.ratel.commons.utils.ChannelUtils;

import java.util.HashMap;
import java.util.Map;


public class ClientBasicHandler_CLIENT_CONNECT extends BasicClientEventHandler {


    @Override
    public void call(Channel channel, String data) {
        SimplePrinter.printNotice("Connected to server. Welcome to ratel!");
        SimpleClient.id = Integer.parseInt(data);

        Map<String, Object> infos = new HashMap<>();
        infos.put("version", SimpleClient.VERSION);
        ChannelUtils.pushToServer(channel, ServerEventCode.CODE_CLIENT_INFO_SET, Noson.reversal(infos));
    }
}
