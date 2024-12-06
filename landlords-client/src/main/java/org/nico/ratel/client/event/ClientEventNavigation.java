package org.nico.ratel.client.event;

import org.nico.ratel.commons.event.BasicEventCode;
import org.nico.ratel.commons.event.BasicEventHandler;
import org.nico.ratel.games.GameInfos;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 柴奇君
 * @create 2024/12/5
 * @desc
 */
public class ClientEventNavigation {

    /**
     * 游戏命令字典 key为gameId 公共命令gameId=0
     */
    public static final Map<Integer,Map<String, BasicEventHandler>> events=new ConcurrentHashMap<>();


    static {

         for(GameInfos info:GameInfos.values()){
             events.put(info.getGameId(),new ConcurrentHashMap<>());
         }
         events.put(0,new ConcurrentHashMap<>());

    }


    public static BasicEventHandler getClientEventHandler(String event){
         return getClientEventHandler(0,event);
    }


    public static BasicEventHandler getClientEventHandler(int gameId, String event)  {


        if(!events.containsKey(gameId)){
            return null;
        }

        Map<String,BasicEventHandler> handlerMap=events.get(gameId);

        if(handlerMap.containsKey(event)){
            return handlerMap.get(event);
        }
        if(gameId==0){
            BasicEventHandler handler=BasicEventCode.getEventHandler(event);
            if(handler!=null){
                handlerMap.putIfAbsent(event,handler);
            }
            return handler;
        }else {

            BasicEventHandler handler=GameInfos.getHandler(gameId,event);
            if(handler!=null){
                handlerMap.putIfAbsent(event,handler);
            }
            return handler;

        }

    }



}
