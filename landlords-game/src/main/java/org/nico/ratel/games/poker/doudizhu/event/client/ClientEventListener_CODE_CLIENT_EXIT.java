package org.nico.ratel.games.poker.doudizhu.event.client;

import java.util.Map;

import org.nico.ratel.client.SimpleClient;
import org.nico.ratel.commons.ClientEventCode;
import org.nico.ratel.commons.helper.MapHelper;
import org.nico.ratel.commons.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_CLIENT_EXIT extends ClientEventListener {

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
