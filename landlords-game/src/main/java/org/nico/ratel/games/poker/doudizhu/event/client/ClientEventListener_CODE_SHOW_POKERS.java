package org.nico.ratel.games.poker.doudizhu.event.client;

import java.util.List;
import java.util.Map;

import org.nico.noson.Noson;
import org.nico.noson.entity.NoType;
import org.nico.ratel.games.poker.Poker;
import org.nico.ratel.commons.helper.MapHelper;
import org.nico.ratel.commons.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_SHOW_POKERS extends ClientEventListener {

	@Override
	public void call(Channel channel, String data) {

		Map<String, Object> map = MapHelper.parser(data);

		ClientEventListener.lastSellClientNickname = (String) map.get("clientNickname");
		ClientEventListener.lastSellClientType = (String) map.get("clientType");

		SimplePrinter.printNotice(ClientEventListener.lastSellClientNickname + "[" + ClientEventListener.lastSellClientType + "] played:");
		ClientEventListener.lastPokers = Noson.convert(map.get("pokers"), new NoType<List<Poker>>() {});
		SimplePrinter.printPokers(ClientEventListener.lastPokers);

		if (map.containsKey("sellClientNickname")) {
			SimplePrinter.printNotice("Next player is " + map.get("sellClientNickname") + ". Please wait for him to play his combination.");
		}
	}

}
