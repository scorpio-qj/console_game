package org.nico.ratel.client.event;

import java.util.Map;

import org.nico.ratel.commons.BasicEventCode;
import org.nico.ratel.commons.helper.MapHelper;
import org.nico.ratel.commons.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_ROOM_JOIN_FAIL_BY_INEXIST extends ClientEventListener {

	@Override
	public void call(Channel channel, String data) {
		Map<String, Object> dataMap = MapHelper.parser(data);

		SimplePrinter.printNotice("Join room failed. Room " + dataMap.get("roomId") + " doesn't exist!");
		ClientEventListener.get(BasicEventCode.CODE_SHOW_OPTIONS).call(channel, data);
	}
}
