package org.nico.ratel.client.event;

import io.netty.channel.Channel;
import org.nico.ratel.client.SimpleClient;
import org.nico.ratel.commons.ClientEventCode;
import org.nico.ratel.commons.event.BasicClientEventHandler;
import org.nico.ratel.commons.helper.MapHelper;
import org.nico.ratel.commons.print.SimplePrinter;
import org.nico.ratel.games.poker.doudizhu.event.client.ClientEventListener;

import java.util.Map;

public class ClientBasicHandler_CLIENT_DISCONNECT extends BasicClientEventHandler {

    @Override
    public void call(Channel channel, String data) {
        Map<String, Object> map = MapHelper.parser(data);

        Integer exitClientId = (Integer) map.get("exitClientId");

        String role;
        if (exitClientId == SimpleClient.id) {
            role = "You";
        } else {
            role = String.valueOf(map.get("exitClientNickname"));
        }
        SimplePrinter.printNotice(role + " left the room. Room disbanded!\n");

        ClientEventListener.get(ClientEventCode.CODE_SHOW_OPTIONS).call(channel, data);
    }
}
