package org.nico.ratel.games.poker.doudizhu.event.client;

import java.util.Map;

import org.nico.ratel.client.SimpleClient;
import org.nico.ratel.commons.ServerEventCode;
import org.nico.ratel.commons.helper.MapHelper;
import org.nico.ratel.commons.print.SimplePrinter;
import org.nico.ratel.commons.print.SimpleWriter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_GAME_LANDLORD_ELECT extends ClientEventListener {

	@Override
	public void call(Channel channel, String data) {
		Map<String, Object> map = MapHelper.parser(data);
		int turnClientId = (int) map.get("nextClientId");
		int highestScore = (int) map.get("highestScore");
		if (map.containsKey("preClientNickname")) {
			if (highestScore != 0 && map.get("preClientId") == map.get("currentLandlordId")) {
				SimplePrinter.printNotice(map.get("preClientNickname") + " robs the landlord with " + highestScore + " score" + (highestScore == 1 ? "" : "s") + "!");
			} else {
				SimplePrinter.printNotice(map.get("preClientNickname") + " don't rob the landlord!");
			}
		}

		if (turnClientId != SimpleClient.id) {
			SimplePrinter.printNotice("It's " + map.get("nextClientNickname") + "'s turn. Please wait patiently for his/her confirmation !");
		} else {
			String message = "It's your turn. What score do you want to rob the landlord? [0";

			for(int i = highestScore + 1; i <= 3; ++i) {
				message = message + "/" + i;
			}

			message = message + "] (enter [exit|e] to exit current room)";
			SimplePrinter.printNotice(message);
			String line = SimpleWriter.write("getScore");
			if (!line.equalsIgnoreCase("exit") && !line.equalsIgnoreCase("e")) {
				try {
					int currentScore = Integer.parseInt(line);
					if (currentScore <= highestScore && currentScore != 0 || currentScore > 3) {
						SimplePrinter.printNotice("Invalid options");
						this.call(channel, data);
						return;
					}

					String result;
					if (currentScore > highestScore) {
						result = MapHelper.newInstance()
							.put("highestScore", currentScore)
							.put("currentLandlordId", SimpleClient.id)
							.json();
					} else if (map.containsKey("currentLandlordId")) {
						result = MapHelper.newInstance()
							.put("highestScore", highestScore)
							.put("currentLandlordId", (int) map.get("currentLandlordId"))
							.json();
					} else {
						result = MapHelper.newInstance()
							.put("highestScore", 0)
							.json();
					}

					this.pushToServer(channel, ServerEventCode.CODE_GAME_LANDLORD_ELECT, result);
				} catch (Exception e) {
					SimplePrinter.printNotice("Invalid options");
					this.call(channel, data);
				}
			} else {
				this.pushToServer(channel, ServerEventCode.CODE_CLIENT_EXIT);
			}
		}

	}

}
