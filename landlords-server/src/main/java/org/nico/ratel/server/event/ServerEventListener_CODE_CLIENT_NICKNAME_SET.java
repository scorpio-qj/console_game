package org.nico.ratel.server.event;

import org.nico.ratel.commons.utils.ChannelUtils;
import org.nico.ratel.commons.clientactor.ClientSide;
import org.nico.ratel.commons.BasicEventCode;
import org.nico.ratel.commons.helper.MapHelper;
import org.nico.ratel.server.ServerContains;

public class ServerEventListener_CODE_CLIENT_NICKNAME_SET implements ServerEventListener {

	public static final int NICKNAME_MAX_LENGTH = 10;

	@Override
	public void call(ClientSide client, String nickname) {
		if (nickname.trim().length() > NICKNAME_MAX_LENGTH || nickname.trim().isEmpty()) {
			String result = MapHelper.newInstance().put("invalidLength", nickname.trim().length()).json();
			ChannelUtils.pushToClient(client.getChannel(), BasicEventCode.CODE_CLIENT_NICKNAME_SET, result);
			return;
		}
		ServerContains.CLIENT_SIDE_MAP.get(client.getId()).setNickname(nickname);
		ChannelUtils.pushToClient(client.getChannel(), BasicEventCode.CODE_SHOW_OPTIONS, null);
	}

}
