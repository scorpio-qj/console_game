package org.nico.ratel.landlords.server.event;

import org.nico.ratel.utils.ChannelUtils;
import org.nico.ratel.clientactor.ClientSide;
import org.nico.ratel.room.Room;
import org.nico.ratel.BasicEventCode;
import org.nico.ratel.BattleRoleType;
import org.nico.ratel.helper.MapHelper;
import org.nico.ratel.landlords.server.ServerContains;

public class ServerEventListener_CODE_CLIENT_OFFLINE implements ServerEventListener {

	@Override
	public void call(ClientSide clientSide, String data) {

		Room room = ServerContains.getRoom(clientSide.getRoomId());

		if (room == null) {
			ServerContains.CLIENT_SIDE_MAP.remove(clientSide.getId());
			return;
		}

		if (room.getWatcherList().contains(clientSide)) {
			return;
		}

		String result = MapHelper.newInstance()
				.put("roomId", room.getId())
				.put("exitClientId", clientSide.getId())
				.put("exitClientNickname", clientSide.getNickname())
				.json();
		for (ClientSide client : room.getClientSideList()) {
			if (client.getRole() != BattleRoleType.PLAYER) {
				continue;
			}
			if (client.getId() != clientSide.getId()) {
				ChannelUtils.pushToClient(client.getChannel(), BasicEventCode.CODE_CLIENT_EXIT, result);
				client.init();
			}
		}
		ServerContains.removeRoom(room.getId());
	}
}
