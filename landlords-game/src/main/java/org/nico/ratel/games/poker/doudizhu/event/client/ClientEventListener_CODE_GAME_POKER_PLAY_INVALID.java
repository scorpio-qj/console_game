package org.nico.ratel.games.poker.doudizhu.event.client;

import org.nico.ratel.commons.ServerEventCode;
import org.nico.ratel.commons.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_GAME_POKER_PLAY_INVALID extends ClientEventListener {

	@Override
	public void call(Channel channel, String data) {

		SimplePrinter.printNotice("This combination is invalid.");

		if(ClientEventListener.lastPokers != null) {
			SimplePrinter.printNotice(ClientEventListener.lastSellClientNickname + "[" + ClientEventListener.lastSellClientType + "] played:");
			SimplePrinter.printPokers(ClientEventListener.lastPokers);
		}

		pushToServer(channel, ServerEventCode.CODE_GAME_POKER_PLAY_REDIRECT);
	}

}
