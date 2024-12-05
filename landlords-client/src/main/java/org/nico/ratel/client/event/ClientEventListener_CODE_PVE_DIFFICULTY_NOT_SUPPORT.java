package org.nico.ratel.client.event;

import org.nico.ratel.commons.BasicEventCode;
import org.nico.ratel.commons.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_PVE_DIFFICULTY_NOT_SUPPORT extends ClientEventListener {

	@Override
	public void call(Channel channel, String data) {
		SimplePrinter.printNotice("The current difficulty is not supported, please pay attention to the following.\n");
		get(BasicEventCode.CODE_SHOW_OPTIONS_PVE).call(channel, data);
	}


}
