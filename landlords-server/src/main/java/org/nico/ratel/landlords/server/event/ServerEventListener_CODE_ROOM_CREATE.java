package org.nico.ratel.landlords.server.event;

import org.nico.noson.Noson;
import org.nico.ratel.utils.ChannelUtils;
import org.nico.ratel.clientactor.ClientSide;
import org.nico.ratel.room.Room;
import org.nico.ratel.BasicEventCode;
import org.nico.ratel.games.poker.doudizhu.DouDiZhuActorRoomState;
import org.nico.ratel.room.enums.RoomStatus;
import org.nico.ratel.BattleType;
import org.nico.ratel.landlords.server.ServerContains;

public class ServerEventListener_CODE_ROOM_CREATE implements ServerEventListener {

	@Override
	public void call(ClientSide clientSide, String data) {

		Room room = new Room(ServerContains.getServerId());
		room.setStatus(RoomStatus.WAIT);
		room.setType(BattleType.PVP);
		room.setRoomOwner(clientSide.getNickname());
		room.getClientSideMap().put(clientSide.getId(), clientSide);
		room.getClientSideList().add(clientSide);
		room.setCurrentSellClient(clientSide.getId());
		room.setCreateTime(System.currentTimeMillis());
		room.setLastFlushTime(System.currentTimeMillis());

		clientSide.setRoomId(room.getId());
		ServerContains.addRoom(room);

		clientSide.setStatus(DouDiZhuActorRoomState.NO_READY);

		ChannelUtils.pushToClient(clientSide.getChannel(), BasicEventCode.CODE_ROOM_CREATE_SUCCESS, Noson.reversal(room));
	}
}
