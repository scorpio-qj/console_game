package org.nico.ratel.landlords.server.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nico.noson.Noson;
import org.nico.noson.util.string.StringUtils;
import org.nico.ratel.utils.ChannelUtils;
import org.nico.ratel.client.ClientSide;
import org.nico.ratel.games.poker.doudizhu.entity.Poker;
import org.nico.ratel.room.Room;
import org.nico.ratel.client.enums.ClientEventCode;
import org.nico.ratel.helper.MapHelper;
import org.nico.ratel.landlords.server.ServerContains;
import org.nico.ratel.utils.LastCardsUtils;

public class ServerEventListener_CODE_GAME_POKER_PLAY_REDIRECT implements ServerEventListener{

	@Override
	public void call(ClientSide clientSide, String data) {
		Room room = ServerContains.getRoom(clientSide.getRoomId());
		Map<String, Object> datas = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(data)) {
			datas = Noson.parseMap(data);
		}

		List<Map<String, Object>> clientInfos = new ArrayList<Map<String,Object>>(3);
		for(ClientSide client : room.getClientSideList()) {
			if(clientSide.getId() != client.getId()) {
				clientInfos.add(MapHelper.newInstance()
						.put("clientId", client.getId())
						.put("clientNickname", client.getNickname())
						.put("type", client.getType())
						.put("surplus", client.getPokers().size())
						.put("position", clientSide.getPre().getId() == client.getId() ? "UP" : "DOWN")
						.map());
			}
		}

		List<List<Poker>> lastPokerList = new ArrayList<>();
		for(int i = 0; i < room.getClientSideList().size(); i++){
			if(room.getClientSideList().get(i).getId() != clientSide.getId()){
				lastPokerList.add(room.getClientSideList().get(i).getPokers());
			}
		}
		String lastPokers = LastCardsUtils.getLastCards(lastPokerList);
		lastPokerList = new ArrayList<>();
		String result = MapHelper.newInstance()
				.put("pokers", clientSide.getPokers())
				.put("lastSellPokers", datas.get("lastSellPokers"))
				.put("lastSellClientId", datas.get("lastSellClientId"))
				.put("clientInfos", clientInfos)
				.put("sellClientId", room.getCurrentSellClient())
				.put("sellClientNickname", ServerContains.CLIENT_SIDE_MAP.get(room.getCurrentSellClient()).getNickname())
				.put("lastPokers",lastPokers)
				.json();

		ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_GAME_POKER_PLAY_REDIRECT, result);
	}

}
