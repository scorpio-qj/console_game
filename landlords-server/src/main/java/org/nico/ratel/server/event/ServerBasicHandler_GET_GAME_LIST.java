package org.nico.ratel.server.event;

import com.sun.org.apache.regexp.internal.RE;
import org.nico.noson.Noson;
import org.nico.ratel.commons.clientactor.BasicActor;
import org.nico.ratel.commons.event.BasicEventCode;
import org.nico.ratel.commons.event.BasicEventHandler;
import org.nico.ratel.commons.helper.MapHelper;
import org.nico.ratel.commons.utils.ChannelUtils;
import org.nico.ratel.commons.utils.ProtoDataUtils;
import org.nico.ratel.games.GameInfos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerBasicHandler_GET_GAME_LIST extends BasicEventHandler {

    @Override
    public void call(BasicActor actor, String data) {


        List<Map<String,String>> gameList=new ArrayList<>();

        for(GameInfos info: GameInfos.values()){

            gameList.add(ProtoDataUtils.mapBuilder()
                    .put("gameId",String.valueOf(info.getGameId()))
                    .put("name",info.name())
                    .build());

        }

        ChannelUtils.pushToClient(actor.getChannel(), BasicEventCode.SC_GAME_LIST, ProtoDataUtils.toString(gameList));


    }
}
