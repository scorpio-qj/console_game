package org.nico.ratel.games.poker.doudizhu.event.client;

import org.nico.ratel.commons.ClientEventCode;
import org.nico.ratel.commons.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_ROOM_PLAY_FAIL_BY_INEXIST extends ClientEventListener {

	@Override
	public void call(Channel channel, String data) {

		SimplePrinter.printNotice("Play failed. Room already disbanded!");
		ClientEventListener.get(ClientEventCode.CODE_SHOW_OPTIONS).call(channel, data);
	}


}
