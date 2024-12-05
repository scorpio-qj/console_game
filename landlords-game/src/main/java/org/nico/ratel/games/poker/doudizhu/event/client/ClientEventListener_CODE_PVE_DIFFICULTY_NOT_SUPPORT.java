package org.nico.ratel.games.poker.doudizhu.event.client;

import org.nico.ratel.commons.ClientEventCode;
import org.nico.ratel.commons.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_PVE_DIFFICULTY_NOT_SUPPORT extends ClientEventListener {

	@Override
	public void call(Channel channel, String data) {
		SimplePrinter.printNotice("The current difficulty is not supported, please pay attention to the following.\n");
		ClientEventListener.get(ClientEventCode.CODE_SHOW_OPTIONS_PVE).call(channel, data);
	}


}
