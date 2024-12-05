package org.nico.ratel.games.poker.doudizhu.event.client;

import java.util.Map;

import org.nico.ratel.commons.ClientEventCode;
import org.nico.ratel.commons.helper.MapHelper;
import org.nico.ratel.commons.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_ROOM_JOIN_FAIL_BY_FULL extends ClientEventListener {

	@Override
	public void call(Channel channel, String data) {
		Map<String, Object> dataMap = MapHelper.parser(data);

		SimplePrinter.printNotice("Join room failed. Room " + dataMap.get("roomId") + " is full!");
		ClientEventListener.get(ClientEventCode.CODE_SHOW_OPTIONS).call(channel, data);
	}


}
