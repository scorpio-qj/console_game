package org.nico.ratel.games.poker.doudizhu.event.client;

import org.nico.ratel.commons.ClientEventCode;
import org.nico.ratel.commons.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_CLIENT_KICK extends ClientEventListener {

	@Override
	public void call(Channel channel, String data) {

		SimplePrinter.printNotice("You have been kicked from the room for being idle.\n");

		ClientEventListener.get(ClientEventCode.CODE_SHOW_OPTIONS).call(channel, data);
	}

}
