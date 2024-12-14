package org.nico.ratel.client.event;

import io.netty.channel.Channel;
import org.nico.noson.util.string.StringUtils;
import org.nico.ratel.client.entity.User;
import org.nico.ratel.commons.event.BasicEventCode;
import org.nico.ratel.commons.event.BasicEventHandler;
import org.nico.ratel.commons.helper.MapHelper;
import org.nico.ratel.commons.print.SimplePrinter;
import org.nico.ratel.commons.print.SimpleWriter;
import org.nico.ratel.commons.utils.ChannelUtils;
import org.nico.ratel.commons.utils.ProtoDataUtils;

import java.util.Map;

/**
 * @author 柴奇君
 * @create 2024/12/9
 * @desc
 */
public class ClientBasicHandler_SET_NICKNAME extends BasicEventHandler {

    public static final int NICKNAME_MAX_LENGTH = 10;

    @Override
    public void call(Channel channel, String data) {

        //首次输入，data为null
        if (StringUtils.isNotBlank(data)) {
            Map dataMap = ProtoDataUtils.toObject(data,Map.class);
            //Map<String, Object> dataMap = MapHelper.parser(data);
            if (dataMap.containsKey("invalidLength")) {
                SimplePrinter.printNotice("Your nickname has invalid length: " + dataMap.get("invalidLength"));
            }
        }
        SimplePrinter.printNotice("Please set your nickname (upto " + NICKNAME_MAX_LENGTH + " characters)");
        String nickname = SimpleWriter.write(User.INSTANCE.getNickname(), "nickname");

        //名字非法，重新输入
        if (nickname.trim().length() > NICKNAME_MAX_LENGTH) {
            String result = ProtoDataUtils.mapBuilder().put("invalidLength",nickname.trim().length()).toString();
            call(channel, result);
        } else {
            ChannelUtils.pushToServer(channel, BasicEventCode.CS_SET_NICKNAME, nickname);
            User.INSTANCE.setNickname(nickname);
        }
    }
}
