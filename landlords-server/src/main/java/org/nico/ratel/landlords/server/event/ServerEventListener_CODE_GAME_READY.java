package org.nico.ratel.landlords.server.event;

import org.nico.ratel.ServerEventCode;
import org.nico.ratel.BasicEventCode;
import org.nico.ratel.BattleRoleType;
import org.nico.ratel.games.poker.doudizhu.DouDiZhuActorRoomState;
import org.nico.ratel.room.enums.RoomStatus;
import org.nico.ratel.utils.ChannelUtils;
import org.nico.ratel.clientactor.ClientSide;
import org.nico.ratel.room.Room;
import org.nico.ratel.helper.MapHelper;
import org.nico.ratel.print.SimplePrinter;
import org.nico.ratel.landlords.server.ServerContains;

import java.util.concurrent.ConcurrentSkipListMap;

public class ServerEventListener_CODE_GAME_READY implements ServerEventListener {
	@Override
	public void call(ClientSide clientSide, String data) {
		Room room = ServerContains.getRoom(clientSide.getRoomId());
		if (room == null) {
			return;
		}
		SimplePrinter.serverLog("房间状态：" + room.getStatus());
		SimplePrinter.serverLog("玩家状态：" + clientSide.getStatus());
		if (room.getStatus() == RoomStatus.STARTING) {
			return;
		}
		if (clientSide.getStatus() == DouDiZhuActorRoomState.PLAYING || clientSide.getStatus() == DouDiZhuActorRoomState.TO_CHOOSE || clientSide.getStatus() == DouDiZhuActorRoomState.CALL_LANDLORD) {
			return;
		}
		clientSide.setStatus(clientSide.getStatus() == DouDiZhuActorRoomState.READY ? DouDiZhuActorRoomState.NO_READY : DouDiZhuActorRoomState.READY);
		String result = MapHelper.newInstance()
				.put("clientNickName", clientSide.getNickname())
				.put("status", clientSide.getStatus())
				.put("clientId", clientSide.getId())
				.json();
		boolean allReady = true;
		ConcurrentSkipListMap<Integer, ClientSide> roomClientMap = (ConcurrentSkipListMap<Integer, ClientSide>) room.getClientSideMap();
		if (roomClientMap.size() < 3) {
			allReady = false;
		} else {
			for (ClientSide client : room.getClientSideList()) {
				if (client.getRole() == BattleRoleType.PLAYER && client.getStatus() != DouDiZhuActorRoomState.READY) {
					allReady = false;
					break;
				}
			}
		}

		for (ClientSide client : room.getClientSideList()) {
			if (client.getRole() == BattleRoleType.PLAYER) {
				ChannelUtils.pushToClient(client.getChannel(), BasicEventCode.CODE_GAME_READY, result);
			}
		}

		if (allReady) {
			ServerEventListener.get(ServerEventCode.CODE_GAME_STARTING).call(clientSide, data);
		}
	}
}
