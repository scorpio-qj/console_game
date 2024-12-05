package org.nico.ratel.landlords.server.event;

import org.nico.ratel.BattleType;
import org.nico.ratel.ServerEventCode;
import org.nico.ratel.BasicEventCode;
import org.nico.ratel.BattleRoleType;
import org.nico.ratel.games.poker.doudizhu.DouDiZhuActorRoomState;
import org.nico.ratel.room.enums.RoomStatus;
import org.nico.ratel.utils.ChannelUtils;
import org.nico.ratel.clientactor.ClientSide;
import org.nico.ratel.room.Room;
import org.nico.ratel.robot.RobotDecisionMakers;
import org.nico.ratel.landlords.server.ServerContains;

public class ServerEventListener_CODE_ROOM_CREATE_PVE implements ServerEventListener {

	@Override
	public void call(ClientSide clientSide, String data) {

		int difficultyCoefficient = Integer.parseInt(data);
		if (!RobotDecisionMakers.contains(difficultyCoefficient)) {
			ChannelUtils.pushToClient(clientSide.getChannel(), BasicEventCode.CODE_PVE_DIFFICULTY_NOT_SUPPORT, null);
			return;
		}

		Room room = new Room(ServerContains.getServerId());
		room.setType(BattleType.PVE);
		room.setStatus(RoomStatus.BLANK);
		room.setRoomOwner(clientSide.getNickname());
		room.getClientSideMap().put(clientSide.getId(), clientSide);
		room.getClientSideList().add(clientSide);
		room.setCurrentSellClient(clientSide.getId());
		room.setCreateTime(System.currentTimeMillis());
		room.setDifficultyCoefficient(difficultyCoefficient);

		clientSide.setRoomId(room.getId());
		ServerContains.addRoom(room);

		ClientSide preClient = clientSide;
		//Add robots
		for (int index = 1; index < 3; index++) {
			ClientSide robot = new ClientSide(-ServerContains.getClientId(), DouDiZhuActorRoomState.PLAYING, null);
			robot.setNickname("robot_" + index);
			robot.setRole(BattleRoleType.ROBOT);
			preClient.setNext(robot);
			robot.setPre(preClient);
			robot.setRoomId(room.getId());
			room.getClientSideMap().put(robot.getId(), robot);
			room.getClientSideList().add(robot);

			preClient = robot;
			ServerContains.CLIENT_SIDE_MAP.put(robot.getId(), robot);
		}
		preClient.setNext(clientSide);
		clientSide.setPre(preClient);

		ServerEventListener.get(ServerEventCode.CODE_GAME_STARTING).call(clientSide, String.valueOf(room.getId()));
	}


}
