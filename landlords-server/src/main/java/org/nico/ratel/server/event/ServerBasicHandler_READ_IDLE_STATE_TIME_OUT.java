package org.nico.ratel.server.event;

import org.nico.ratel.commons.BattleRoleType;
import org.nico.ratel.commons.clientactor.BasicActor;
import org.nico.ratel.commons.event.BasicEventCode;
import org.nico.ratel.commons.event.BasicEventHandler;
import org.nico.ratel.commons.helper.MapHelper;
import org.nico.ratel.commons.room.BasicRoom;
import org.nico.ratel.commons.utils.ChannelUtils;
import org.nico.ratel.server.ServerContains;

/**
 * @author 柴奇君
 * @create 2024/12/9
 * @desc
 */
public class ServerBasicHandler_READ_IDLE_STATE_TIME_OUT extends BasicEventHandler {

    @Override
    public void call(BasicActor actor, String data) {


        ServerContains.CHANNEL_ID_MAP.remove(actor.getChannel().id().asLongText());
        ServerContains.CLIENT_MAP.remove(actor.getId());

        BasicRoom room = ServerContains.getRoom(actor.getRoomId());

        if(room!=null){

            if(room.getWatcherList().contains(actor)){
                return;
            }

            String result = MapHelper.newInstance()
                    .put("roomId", room.getRoomId())
                    .put("exitClientId", actor.getId())
                    .put("exitClientNickname", actor.getNickName())
                    .json();
            for (BasicActor client : room.getClientLinkedList()) {
                //如果是机器人
                if (client.getActorType() != BattleRoleType.PLAYER.getType()) {
                    continue;
                }
                if (client.getId() != actor.getId()) {
                    ChannelUtils.pushToClient(client.getChannel(), BasicEventCode.SC_PLAYER_EXIT, result);
                    //todo 当其他玩家离线时处理
                }
            }

            room.getClientLinkedList().remove(actor);
            room.getClientMap().remove(actor.getId());

            if(room.getClientLinkedList().size()==0){

                ServerContains.ROOMS.remove(room.getRoomId());
                ServerContains.GAME_ROOMS.get(room.getGameId()).remove(room);
            }



        }

    }
}
