package org.nico.ratel.server;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;
import org.nico.ratel.commons.clientactor.BasicActor;
import org.nico.ratel.commons.room.BasicRoom;
import org.nico.ratel.server.utils.IdUtils;

public class ServerContains {

	/**
	 * channel对应玩家id
	 */
	public static final Map<String ,Long> CHANNEL_ID_MAP=new ConcurrentHashMap<>();

	/**
	 * 玩家id对应实体
	 */
	public static final Map<Long, BasicActor> CLIENT_MAP=new ConcurrentHashMap<>();

	/**
	 * 游戏房间 gameId->rooms
	 */
	public static final Map<Integer, List<BasicRoom>> GAME_ROOMS =new ConcurrentHashMap<>();

	/**
	 * 所有房间 roomId->room
	 */
	public static final Map<Long,BasicRoom> ROOMS =new ConcurrentHashMap<>();



	public static long getClientId(Channel channel){
		String text = channel.id().asLongText();
		Long clientId = CHANNEL_ID_MAP.get(text);
		if (null == clientId) {
			clientId = IdUtils.getId();
			CHANNEL_ID_MAP.put(text,clientId);
		}
		return clientId;
	}

	public static BasicRoom getRoom(long roomId){

		return ROOMS.get(roomId);
	}

	public static List<BasicRoom> getRoomList(int gameId){
		return GAME_ROOMS.get(gameId);
	}





}
