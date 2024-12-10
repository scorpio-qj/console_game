package org.nico.ratel.server.event;

import com.sun.org.apache.regexp.internal.RE;
import org.nico.noson.Noson;
import org.nico.ratel.commons.clientactor.BasicActor;
import org.nico.ratel.commons.event.BasicEventCode;
import org.nico.ratel.commons.event.BasicEventHandler;
import org.nico.ratel.commons.helper.MapHelper;
import org.nico.ratel.commons.utils.ChannelUtils;
import org.nico.ratel.games.GameInfos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerBasicHandler_GET_GAME_LIST extends BasicEventHandler {

    @Override
    public void call(BasicActor actor, String data) {


        List<Map<String,Object>> gameList=new ArrayList<>();

        for(GameInfos info: GameInfos.values()){

            Map<String,Object> map=new HashMap<>();
            map.put("gameId",info.getGameId());
            map.put("name",info.name());
            gameList.add(map);

        }
        String result=MapHelper.newInstance().put("gameList",gameList).json();
        ChannelUtils.pushToClient(actor.getChannel(), BasicEventCode.SC_GAME_LIST, Noson.reversal(result));


    }
}
