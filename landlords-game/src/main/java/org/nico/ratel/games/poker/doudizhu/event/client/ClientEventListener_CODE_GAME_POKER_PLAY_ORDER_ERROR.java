package org.nico.ratel.games.poker.doudizhu.event.client;

import org.nico.ratel.commons.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_GAME_POKER_PLAY_ORDER_ERROR extends ClientEventListener {

	@Override
	public void call(Channel channel, String data) {

		SimplePrinter.printNotice("It is not your turn yet. Please wait for other players!");
	}

}
