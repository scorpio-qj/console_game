package org.nico.ratel.server.event;

import org.nico.noson.Noson;
import org.nico.ratel.commons.utils.ChannelUtils;
import org.nico.ratel.commons.clientactor.ClientSide;
import org.nico.ratel.commons.room.Room;
import org.nico.ratel.commons.BasicEventCode;
import org.nico.ratel.commons.helper.MapHelper;
import org.nico.ratel.server.ServerContains;

import java.util.HashMap;
import java.util.Map;

public class ServerEventListener_CODE_GAME_WATCH implements ServerEventListener {

    @Override
    public void call(ClientSide clientSide, String data) {
        Room room = ServerContains.getRoom(Integer.parseInt(data));

        if (room == null) {
            String result = MapHelper.newInstance()
                    .put("roomId", data)
                    .json();

            ChannelUtils.pushToClient(clientSide.getChannel(), BasicEventCode.CODE_ROOM_JOIN_FAIL_BY_INEXIST, result);
        } else {
            // 将用户加入到房间中的观战者列表中
            clientSide.setRoomId(room.getId());
            room.getWatcherList().add(clientSide);

            Map<String, String> map = new HashMap<>(16);
            map.put("owner", room.getRoomOwner());
            map.put("status", room.getStatus().toString());
            ChannelUtils.pushToClient(clientSide.getChannel(), BasicEventCode.CODE_GAME_WATCH_SUCCESSFUL, Noson.reversal(map));
        }
    }
}
