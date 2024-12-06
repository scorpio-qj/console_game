package org.nico.ratel.server.event;


import org.nico.ratel.commons.utils.ChannelUtils;
import org.nico.ratel.commons.clientactor.ClientSide;
import org.nico.ratel.commons.room.Room;
import org.nico.ratel.commons.ClientEventCode;
import org.nico.ratel.commons.BattleRoleType;
import org.nico.ratel.games.poker.doudizhu.DouDiZhuRoleType;
import org.nico.ratel.commons.ServerEventCode;
import org.nico.ratel.commons.helper.MapHelper;
import org.nico.ratel.commons.helper.PokerHelper;
import org.nico.ratel.server.ServerContains;
import org.nico.ratel.server.robot.RobotEventListener;

import java.util.Map;

public class ServerEventListener_CODE_GAME_LANDLORD_ELECT implements ServerEventListener {

	@Override
	public void call(ClientSide clientSide, String data) {

		Room room = ServerContains.getRoom(clientSide.getRoomId());
		Map<String, Object> map = MapHelper.parser(data);
		int highestScore = (Integer)map.get("highestScore");

		if (room == null) {
			return;
		}
		if (highestScore == 3) {
			room.setBaseScore(highestScore);
			confirmLandlord(clientSide, room);
			return;
		}
		if (clientSide.getNext().getId() == room.getFirstSellClient()) {
			if (highestScore == 0) {
				for (ClientSide client : room.getClientSideList()) {
					if (client.getRole() == BattleRoleType.PLAYER) {
						ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_GAME_LANDLORD_CYCLE, null);
					}
				}
				get(ServerEventCode.CODE_GAME_STARTING).call(clientSide, null);
				return;
			} else {
				room.setBaseScore(highestScore);
				int landlordId = (Integer)map.get("currentLandlordId");
				for (ClientSide client : room.getClientSideList())
					if (client.getId() == landlordId) {
						confirmLandlord(client, room);
						return;
					}
			}
			
		}
		ClientSide turnClientSide = clientSide.getNext();
		room.setCurrentSellClient(turnClientSide.getId());
		String result;

		if (highestScore != 0) {
			result = MapHelper.newInstance()
				.put("roomId", room.getId())
				.put("roomOwner", room.getRoomOwner())
				.put("roomClientCount", room.getClientSideList().size())
				.put("preClientNickname", clientSide.getNickname())
				.put("preClientId", clientSide.getId())
				.put("nextClientNickname", turnClientSide.getNickname())
				.put("nextClientId", turnClientSide.getId())
				.put("highestScore", highestScore)
				.put("currentLandlordId", (Integer)map.get("currentLandlordId"))
				.json();
		} else {
			result = MapHelper.newInstance()
				.put("roomId", room.getId())
				.put("roomOwner", room.getRoomOwner())
				.put("roomClientCount", room.getClientSideList().size())
				.put("preClientNickname", clientSide.getNickname())
				.put("nextClientNickname", turnClientSide.getNickname())
				.put("nextClientId", turnClientSide.getId())
				.put("highestScore", 0)
				.json();
		}
		for (ClientSide client : room.getClientSideList()) {
			if (client.getRole() == BattleRoleType.PLAYER) {
				ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_GAME_LANDLORD_ELECT, result);
				continue;
			}
			if (client.getId() == turnClientSide.getId()) {
				RobotEventListener.get(ClientEventCode.CODE_GAME_LANDLORD_ELECT).call(client, result);
			}
		}
		notifyWatcherRobLandlord(room, clientSide);
	}

	public void confirmLandlord(ClientSide clientSide, Room room) {
		clientSide.getPokers().addAll(room.getLandlordPokers());
		PokerHelper.sortPoker(clientSide.getPokers());

		int currentClientId = clientSide.getId();
		room.setLandlordId(currentClientId);
		room.setLastSellClient(currentClientId);
		room.setCurrentSellClient(currentClientId);
		clientSide.setType(DouDiZhuRoleType.LANDLORD);

		for (ClientSide client : room.getClientSideList()) {
			String result = MapHelper.newInstance()
					.put("roomId", room.getId())
					.put("roomOwner", room.getRoomOwner())
					.put("roomClientCount", room.getClientSideList().size())
					.put("landlordNickname", clientSide.getNickname())
					.put("landlordId", clientSide.getId())
					.put("additionalPokers", room.getLandlordPokers())
					.put("baseScore", room.getBaseScore())
					.json();
			client.resetRound();

			if (client.getRole() == BattleRoleType.PLAYER) {
				ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_GAME_LANDLORD_CONFIRM, result);
				continue;
			}

			if (currentClientId == client.getId()) {
				RobotEventListener.get(ClientEventCode.CODE_GAME_POKER_PLAY).call(client, result);
			}
		}

		notifyWatcherConfirmLandlord(room, clientSide);
	}

	/**
	 * 通知房间内的观战人员谁是地主
	 *
	 * @param room	房间
	 * @param landlord 地主
	 */
	private void notifyWatcherConfirmLandlord(Room room, ClientSide landlord) {
		String json = MapHelper.newInstance()
							.put("landlord", landlord.getNickname())
							.put("additionalPokers", room.getLandlordPokers())
							.json();

		for (ClientSide watcher : room.getWatcherList()) {
			ChannelUtils.pushToClient(watcher.getChannel(), ClientEventCode.CODE_GAME_LANDLORD_CONFIRM, json);
		}
	}

	/**
	 * 通知房间内的观战人员抢地主情况
	 *
	 * @param room	房间
	 */
	private void notifyWatcherRobLandlord(Room room, ClientSide player) {
		for (ClientSide watcher : room.getWatcherList()) {
			ChannelUtils.pushToClient(watcher.getChannel(), ClientEventCode.CODE_GAME_LANDLORD_ELECT, player.getNickname());
		}
	}
}
