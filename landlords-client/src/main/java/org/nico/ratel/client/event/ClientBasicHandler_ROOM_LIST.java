package org.nico.ratel.client.event;

import io.netty.channel.Channel;
import org.nico.ratel.client.entity.User;
import org.nico.ratel.commons.event.BasicEventCode;
import org.nico.ratel.commons.event.BasicEventHandler;
import org.nico.ratel.commons.event.entity.RoomListData;
import org.nico.ratel.commons.print.FormatPrinter;
import org.nico.ratel.commons.print.SimplePrinter;
import org.nico.ratel.commons.print.SimpleWriter;
import org.nico.ratel.commons.utils.OptionsUtils;
import org.nico.ratel.commons.utils.ProtoDataUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientBasicHandler_ROOM_LIST extends BasicEventHandler {

    @Override
    public void call(Channel channel, String data) {


        RoomListData roomListData= ProtoDataUtils.toObject(data, RoomListData.class);
        if (roomListData != null) {

            String format = "#\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t#\n";
            FormatPrinter.printNotice(format, "NUM","ROOMID","NAME", "SIZE","TYPE");
            for(int i=1;i<=roomListData.getRooms().size();i++){
                Map<String,String> game= roomListData.getRooms().get(i-1);
                FormatPrinter.printNotice(format, i, game.get("roomId"),game.get("roomName"), game.get("roomSize"),game.get("battleType"));
            }
            String param= ProtoDataUtils.mapBuilder().put("gameId",roomListData.getGameId()).put("name",roomListData.getGameName()).toString();
            SimplePrinter.printNotice("");
            ClientEventNavigation.getClientEventHandler(BasicEventCode.C_ROOM_OPTIONS.getEventName()).call(channel, param);
        } else {
            SimplePrinter.printNotice("No available rooms. Please wait update!");
            ClientEventNavigation.getClientEventHandler(BasicEventCode.CS_GET_GAME_LIST.getEventName()).call(channel, data);
        }

    }
}
