package org.nico.ratel.games.poker.doudizhu.event.client;

import java.util.Map;

import org.nico.ratel.commons.ServerEventCode;
import org.nico.ratel.commons.helper.MapHelper;
import org.nico.ratel.commons.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_GAME_POKER_PLAY_MISMATCH extends ClientEventListener {

	@Override
	public void call(Channel channel, String data) {
		Map<String, Object> map = MapHelper.parser(data);

		SimplePrinter.printNotice("Your combination is " + map.get("playType") + " (" + map.get("playCount") + "), but the previous combination is " + map.get("preType") + " (" + map.get("preCount") + "). Mismatch!");

		if(ClientEventListener.lastPokers != null) {
			SimplePrinter.printNotice(ClientEventListener.lastSellClientNickname + "[" + ClientEventListener.lastSellClientType + "] played:");
			SimplePrinter.printPokers(ClientEventListener.lastPokers);
		}

		pushToServer(channel, ServerEventCode.CODE_GAME_POKER_PLAY_REDIRECT);
	}

}
