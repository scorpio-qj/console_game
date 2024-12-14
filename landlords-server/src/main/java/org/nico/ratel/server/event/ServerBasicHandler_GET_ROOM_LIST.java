package org.nico.ratel.server.event;

import com.google.gson.internal.$Gson$Preconditions;
import org.nico.noson.Noson;
import org.nico.ratel.commons.BattleType;
import org.nico.ratel.commons.clientactor.BasicActor;
import org.nico.ratel.commons.event.BasicEventCode;
import org.nico.ratel.commons.event.BasicEventHandler;
import org.nico.ratel.commons.event.entity.RoomListData;
import org.nico.ratel.commons.room.BasicRoom;
import org.nico.ratel.commons.utils.ChannelUtils;
import org.nico.ratel.commons.utils.ProtoDataUtils;
import org.nico.ratel.games.GameInfos;
import org.nico.ratel.server.ServerContains;

import java.util.*;

public class ServerBasicHandler_GET_ROOM_LIST extends BasicEventHandler {


    @Override
    public void call(BasicActor actor, String param) {

        int gameId=Integer.parseInt(param);
        GameInfos gameInfo= GameInfos.getInfoById(gameId);
        List<BasicRoom> rooms=ServerContains.getRoomList(gameId);
        List<Map<String,String>> gameList=new ArrayList<>();
        if(rooms!=null && rooms.size()>0){

            for(BasicRoom room: rooms){
                gameList.add(ProtoDataUtils.mapBuilder()
                        .put("roomId",String.valueOf(room.getRoomId()))
                        .put("roomName",room.getRoomName())
                        .put("roomSize",room.getClientLinkedList().size())
                        .put("battleType",room.getBattleType())
                        .build());

            }

        }else {
            gameList.add(ProtoDataUtils.mapBuilder()
                    .put("roomId",100)
                    .put("roomName","test")
                    .put("roomSize","2")
                    .put("battleType", BattleType.PVE)
                    .build());
        }
        RoomListData data=new RoomListData();
        data.setGameId(gameId);
        data.setGameName(gameInfo.name());
        data.setRooms(gameList);
        ChannelUtils.pushToClient(actor.getChannel(), BasicEventCode.SC_ROOM_LIST,ProtoDataUtils.toString(data));
    }
}
