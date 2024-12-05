package org.nico.ratel.landlords.client.event;

import java.util.Map;

import org.nico.ratel.BasicEventCode;
import org.nico.ratel.helper.MapHelper;
import org.nico.ratel.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_ROOM_JOIN_FAIL_BY_FULL extends ClientEventListener {

	@Override
	public void call(Channel channel, String data) {
		Map<String, Object> dataMap = MapHelper.parser(data);

		SimplePrinter.printNotice("Join room failed. Room " + dataMap.get("roomId") + " is full!");
		ClientEventListener.get(BasicEventCode.CODE_SHOW_OPTIONS).call(channel, data);
	}


}
