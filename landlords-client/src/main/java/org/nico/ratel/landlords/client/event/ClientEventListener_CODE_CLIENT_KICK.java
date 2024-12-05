package org.nico.ratel.landlords.client.event;

import org.nico.ratel.BasicEventCode;
import org.nico.ratel.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_CLIENT_KICK extends ClientEventListener {

	@Override
	public void call(Channel channel, String data) {

		SimplePrinter.printNotice("You have been kicked from the room for being idle.\n");

		get(BasicEventCode.CODE_SHOW_OPTIONS).call(channel, data);
	}

}
