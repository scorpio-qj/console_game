package org.nico.ratel.client.event;

import io.netty.channel.Channel;
import org.nico.ratel.client.SimpleClient;
import org.nico.ratel.client.entity.User;
import org.nico.ratel.commons.ClientEventCode;
import org.nico.ratel.commons.event.BasicEventCode;
import org.nico.ratel.commons.event.BasicEventHandler;
import org.nico.ratel.commons.helper.MapHelper;
import org.nico.ratel.commons.print.SimplePrinter;
import org.nico.ratel.games.poker.doudizhu.event.client.ClientEventListener;

import java.util.Map;

public class ClientBasicHandler_DISCONNECT extends BasicEventHandler {

    @Override
    public void call(Channel channel, String data) {
        Map<String, Object> map = MapHelper.parser(data);

        Integer exitClientId = (Integer) map.get("exitClientId");

        String role;
        if (exitClientId == User.INSTANCE.getId()) {
            role = "You";
        } else {
            role = String.valueOf(map.get("exitClientNickname"));
        }
        SimplePrinter.printNotice(role + " left the room. Room disbanded!\n");

        ClientEventNavigation.getClientEventHandler(BasicEventCode.SC_SHOW_GLOBAL_OPTIONS.getEventName()).call(channel,data);
    }
}
