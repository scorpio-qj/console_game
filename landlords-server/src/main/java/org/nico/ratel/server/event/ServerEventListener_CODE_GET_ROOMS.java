package org.nico.ratel.server.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.nico.noson.Noson;
import org.nico.ratel.commons.utils.ChannelUtils;
import org.nico.ratel.commons.clientactor.ClientSide;
import org.nico.ratel.commons.room.Room;
import org.nico.ratel.commons.ClientEventCode;
import org.nico.ratel.commons.helper.MapHelper;
import org.nico.ratel.server.ServerContains;

public class ServerEventListener_CODE_GET_ROOMS implements ServerEventListener {

	@Override
	public void call(ClientSide clientSide, String data) {
		List<Map<String, Object>> roomList = new ArrayList<>(ServerContains.getRoomMap().size());
		for (Entry<Integer, Room> entry : ServerContains.getRoomMap().entrySet()) {
			Room room = entry.getValue();
			roomList.add(MapHelper.newInstance()
					.put("roomId", room.getId())
					.put("roomOwner", room.getRoomOwner())
					.put("roomClientCount", room.getClientSideList().size())
					.put("roomType", room.getType())
					.map());
		}
		ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_SHOW_ROOMS, Noson.reversal(roomList));
	}

}
