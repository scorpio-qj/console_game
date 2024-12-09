package org.nico.ratel.server.event;

import org.nico.ratel.commons.BattleRoleType;
import org.nico.ratel.commons.ClientEventCode;
import org.nico.ratel.commons.clientactor.BasicActor;
import org.nico.ratel.commons.clientactor.ClientSide;
import org.nico.ratel.commons.event.BasicEventCode;
import org.nico.ratel.commons.event.BasicEventHandler;
import org.nico.ratel.commons.helper.MapHelper;
import org.nico.ratel.commons.room.BasicRoom;
import org.nico.ratel.commons.room.Room;
import org.nico.ratel.commons.utils.ChannelUtils;
import org.nico.ratel.server.ServerContains;

public class ServerBasicHandler_PLAYER_EXIT extends BasicEventHandler {

    private static final Object locked = new Object();
    @Override
    public void call(BasicActor actor, String data) {
        synchronized (locked){
            BasicRoom room = ServerContains.getRoom(actor.getRoomId());

            if(room==null){
                return;
            }
            String result = MapHelper.newInstance()
                    .put("roomId", room.getRoomId())
                    .put("exitClientId", actor.getId())
                    .put("exitClientNickname", actor.getNickName())
                    .json();
            for (BasicActor client : room.getClientLinkedList()) {
                //通知房间中比赛玩家
                if (client.getActorType() == BattleRoleType.PLAYER.getType()) {
                    ChannelUtils.pushToClient(client.getChannel(), BasicEventCode.SC_PLAYER_EXIT, result);
                    //TODO 设置房间中玩家数据
                }
            }

            room.getClientLinkedList().remove(actor);
            room.getClientMap().remove(actor.getId());

            if(room.getClientLinkedList().size()==0){

                ServerContains.ROOMS.remove(room.getRoomId());
                ServerContains.GAME_ROOMS.get(room.getGameId()).remove(room);
            }

            notifyWatcherClientExit(room, actor);

        }

    }

    /**
     * 通知所有观战者玩家退出游戏
     *
     * @param room 房间
     * @param actor 退出游戏玩家
     */
    private void notifyWatcherClientExit(BasicRoom room, BasicActor actor) {
        for (BasicActor watcher : room.getWatcherList()) {
            ChannelUtils.pushToClient(watcher.getChannel(), BasicEventCode.SC_PLAYER_EXIT, actor.getNickName());
        }
    }
}
