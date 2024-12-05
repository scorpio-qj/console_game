package org.nico.ratel.server.event;

import org.nico.ratel.commons.utils.ChannelUtils;
import org.nico.ratel.commons.clientactor.ClientSide;
import org.nico.ratel.commons.room.Room;
import org.nico.ratel.commons.ClientEventCode;
import org.nico.ratel.commons.BattleRoleType;
import org.nico.ratel.commons.helper.MapHelper;
import org.nico.ratel.server.ServerContains;

public class ServerEventListener_CODE_CLIENT_EXIT implements ServerEventListener {

	private static final Object locked = new Object();

	@Override
	public void call(ClientSide clientSide, String data) {
		synchronized (locked){
			Room room = ServerContains.getRoom(clientSide.getRoomId());
			if (room == null) {
				return;
			}
			String result = MapHelper.newInstance()
					.put("roomId", room.getId())
					.put("exitClientId", clientSide.getId())
					.put("exitClientNickname", clientSide.getNickname())
					.json();
			for (ClientSide client : room.getClientSideList()) {
				if (client.getRole() == BattleRoleType.PLAYER) {
					ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_CLIENT_EXIT, result);
					client.init();
				}
			}

			notifyWatcherClientExit(room, clientSide);
			ServerContains.removeRoom(room.getId());
		}
	}

	/**
	 * 通知所有观战者玩家退出游戏
	 *
	 * @param room 房间
	 * @param player 退出游戏玩家
	 */
	private void notifyWatcherClientExit(Room room, ClientSide player) {
		for (ClientSide watcher : room.getWatcherList()) {
			ChannelUtils.pushToClient(watcher.getChannel(), ClientEventCode.CODE_CLIENT_EXIT, player.getNickname());
		}
	}
}
