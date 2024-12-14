package org.nico.ratel.client.event;

import org.nico.ratel.commons.event.BasicEventCode;
import org.nico.ratel.commons.event.BasicEventHandler;
import org.nico.ratel.commons.event.DefaultEventHandler;
import org.nico.ratel.commons.event.EventCode;
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

    public static final Map<String, EventCode> eventCodes=new ConcurrentHashMap<>();

    public static final DefaultEventHandler DEFAULT_EVENT_HANDLER=new DefaultEventHandler();


    static {

         for(GameInfos info:GameInfos.values()){
             events.put(info.getGameId(),new ConcurrentHashMap<>());
         }
         Map<String,BasicEventHandler> baseHandler=new ConcurrentHashMap<>();
         events.put(0,baseHandler);

         //注册基础协议
         registerBasicEvent(BasicEventCode.C_SHOW_OPTION_SETTING,ClientBasicHandler_SHOW_OPTION_SETTING.class);
         registerBasicEvent(BasicEventCode.C_GAME_WATCH,ClientBasicHandler_GAME_WATCH.class);
         registerBasicEvent(BasicEventCode.C_ROOM_OPTIONS,ClientBasicHandler_ROOM_OPTIONS.class);
         registerBasicEvent(BasicEventCode.SC_GAME_LIST,ClientBasicHandler_GAME_LIST.class);
         registerBasicEvent(BasicEventCode.SC_SET_NICKNAME,ClientBasicHandler_SET_NICKNAME.class);
         registerBasicEvent(BasicEventCode.SC_CONNECT,ClientBasicHandler_CONNECT.class);
         registerBasicEvent(BasicEventCode.SC_DISCONNECT,ClientBasicHandler_DISCONNECT.class);
         registerBasicEvent(BasicEventCode.SC_PLAYER_EXIT,ClientBasicHandler_PLAYER_EXIT.class);
         registerBasicEvent(BasicEventCode.SC_SHOW_GLOBAL_OPTIONS,ClientBasicHandler_SHOW_GLOBAL_OPTIONS.class);
         registerBasicEvent(BasicEventCode.SC_ROOM_LIST,ClientBasicHandler_ROOM_LIST.class);
         //registerBasicEvent(BasicEventCode.SC_KICK,);
         //for(BasicEventCode code :BasicEventCode.values()){
         //    if(code.isClientEvent()){
         //
         //    }
         //}

    }

    public static <T extends BasicEventHandler> void registerBasicEvent(EventCode code,Class<T> hClazz){

        T handler=BasicEventHandler.createHandler(hClazz);
        if(handler==null){
            return;
        }
        Map<String,BasicEventHandler> baseHandler=events.get(0);
        if(code.isClientEvent()){
            baseHandler.put(code.getEventName(),handler);
        }

    }

    public static BasicEventHandler getClientEventHandler(String event){
         return getClientEventHandler(0,event);
    }


    public static BasicEventHandler getClientEventHandler(int gameId, String event)  {

        if(!events.containsKey(gameId)){
            return DEFAULT_EVENT_HANDLER;
        }

        Map<String,BasicEventHandler> handlerMap=events.get(gameId);

        if(handlerMap.containsKey(event)){
            return handlerMap.get(event);
        }

        if(gameId!=0){
            BasicEventHandler handler=GameInfos.getHandler(gameId,event);
            if(handler!=null){
                handlerMap.putIfAbsent(event,handler);
                return handler;
            }
        }

        return DEFAULT_EVENT_HANDLER;


    }

    public static EventCode getEventCode(String name){
        return getEventCode(0,name);
    }

    public static EventCode getEventCode(int gameId,String name){

        if(eventCodes.containsKey(name)){
            return eventCodes.get(name);
        }

        if(gameId==0){
            BasicEventCode code= BasicEventCode.valueOf(name);
            eventCodes.putIfAbsent(name,code);
            return code;
        }else{

            EventCode code=GameInfos.getEventCode(gameId,name);
            if(code!=null){
                eventCodes.putIfAbsent(name,code);
            }
            return code;

        }


    }



}
