package org.nico.ratel.client.event;

import java.util.Map;

import org.nico.ratel.client.SimpleClient;
import org.nico.ratel.commons.ServerEventCode;
import org.nico.ratel.commons.helper.MapHelper;
import org.nico.ratel.commons.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_GAME_POKER_PLAY_PASS extends ClientEventListener {

	@Override
	public void call(Channel channel, String data) {
		Map<String, Object> map = MapHelper.parser(data);

		SimplePrinter.printNotice(map.get("clientNickname") + " passed. It is now " + map.get("nextClientNickname") + "'s turn.");

		int turnClientId = (int) map.get("nextClientId");
		if (SimpleClient.id == turnClientId) {
			pushToServer(channel, ServerEventCode.CODE_GAME_POKER_PLAY_REDIRECT);
		}
	}

}
