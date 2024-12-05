package org.nico.ratel.server.event;

import org.nico.ratel.commons.utils.ChannelUtils;
import org.nico.ratel.commons.clientactor.ClientSide;
import org.nico.ratel.commons.room.Room;
import org.nico.ratel.commons.ClientEventCode;
import org.nico.ratel.commons.BattleRoleType;
import org.nico.ratel.commons.helper.MapHelper;
import org.nico.ratel.server.ServerContains;

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
				ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_CLIENT_EXIT, result);
				client.init();
			}
		}
		ServerContains.removeRoom(room.getId());
	}
}
