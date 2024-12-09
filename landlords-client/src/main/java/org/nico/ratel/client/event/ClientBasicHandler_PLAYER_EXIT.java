package org.nico.ratel.client.event;

import io.netty.channel.Channel;
import org.nico.ratel.client.entity.User;
import org.nico.ratel.commons.event.BasicEventCode;
import org.nico.ratel.commons.event.BasicEventHandler;
import org.nico.ratel.commons.helper.MapHelper;
import org.nico.ratel.commons.print.SimplePrinter;

import java.util.Map;

/**
 * @author 柴奇君
 * @create 2024/12/9
 * @desc
 */
public class ClientBasicHandler_PLAYER_EXIT extends BasicEventHandler {

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

        //todo 回到初始页，显示所有选项
        ClientEventNavigation.getClientEventHandler(BasicEventCode.SC_SHOW_GLOBAL_OPTIONS.getEventName()).call(channel, data);
    }
}
