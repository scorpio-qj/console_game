package org.nico.ratel.server;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import io.netty.channel.Channel;
import org.nico.ratel.commons.clientactor.BasicActor;
import org.nico.ratel.commons.clientactor.ClientSide;
import org.nico.ratel.commons.room.BasicRoom;
import org.nico.ratel.commons.room.Room;
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


	/**
	 * The map of server side
	 */
	private final static Map<Integer, Room> ROOM_MAP = new ConcurrentSkipListMap<>();

	/**
	 * The list of client side
	 */
	public final static Map<Integer, ClientSide> CLIENT_SIDE_MAP = new ConcurrentSkipListMap<>();


	private final static AtomicInteger CLIENT_ATOMIC_ID = new AtomicInteger(1);

	private final static AtomicInteger SERVER_ATOMIC_ID = new AtomicInteger(1);

	public final static int getClientId() {
		return CLIENT_ATOMIC_ID.getAndIncrement();
	}

	public final static int getServerId() {
		return SERVER_ATOMIC_ID.getAndIncrement();
	}

	public final static ThreadPoolExecutor THREAD_EXCUTER = new ThreadPoolExecutor(500, 500, 0, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>());


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


	/*
	public static Room getRoom(int id) {
		Room room = ROOM_MAP.get(id);
		if (room != null) {
			room.setLastFlushTime(System.currentTimeMillis());
		}
		return room;
	}

	public static Map<Integer, Room> getRoomMap() {
		return ROOM_MAP;
	}

	public static Room removeRoom(int id) {
		return ROOM_MAP.remove(id);
	}

	public static Room addRoom(Room room) {
		return ROOM_MAP.put(room.getId(), room);
	}
	*/


}
