package org.nico.ratel.server.event;

import org.nico.ratel.commons.ClientEventCode;
import org.nico.ratel.commons.clientactor.BasicActor;
import org.nico.ratel.commons.event.BasicEventCode;
import org.nico.ratel.commons.event.BasicEventHandler;
import org.nico.ratel.commons.helper.MapHelper;
import org.nico.ratel.commons.utils.ChannelUtils;
import org.nico.ratel.server.ServerContains;

/**
 * @author 柴奇君
 * @create 2024/12/9
 * @desc
 */
public class ServerBasicHandler_SET_NICKNAME extends BasicEventHandler {

    public static final int NICKNAME_MAX_LENGTH = 10;

    @Override
    public void call(BasicActor actor, String data) {
        if (data.trim().length() > NICKNAME_MAX_LENGTH || data.trim().isEmpty()) {
            String result = MapHelper.newInstance().put("invalidLength", data.trim().length()).json();
            ChannelUtils.pushToClient(actor.getChannel(), BasicEventCode.SC_SET_NICKNAME, result);
            return;
        }
        actor.setNickName(data);
        ChannelUtils.pushToClient(actor.getChannel(), BasicEventCode.SC_SHOW_GLOBAL_OPTIONS, null);
    }
}
