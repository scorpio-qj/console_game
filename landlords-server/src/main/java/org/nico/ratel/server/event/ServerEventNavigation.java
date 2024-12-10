package org.nico.ratel.server.event;

import org.nico.ratel.commons.event.BasicEventCode;
import org.nico.ratel.commons.event.BasicEventHandler;
import org.nico.ratel.commons.event.DefaultEventHandler;
import org.nico.ratel.commons.event.EventCode;
import org.nico.ratel.games.GameInfos;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerEventNavigation {

    /**
     * 游戏命令字典 key为gameId 公共命令gameId=0
     */
    public static final Map<Integer, Map<String, BasicEventHandler>> events=new ConcurrentHashMap<>();

    public static final Map<String, EventCode> eventCodes=new ConcurrentHashMap<>();

    public static final DefaultEventHandler DEFAULT_EVENT_HANDLER=new DefaultEventHandler();

    static {

        for(GameInfos info:GameInfos.values()){
            events.put(info.getGameId(),new ConcurrentHashMap<>());
        }
        Map<String,BasicEventHandler> baseHandler=new ConcurrentHashMap<>();
        events.put(0,baseHandler);
        //注册基础协议
        registerBasicEvent(BasicEventCode.S_PLAYER_EXIT,ServerBasicHandler_PLAYER_EXIT.class);
        registerBasicEvent(BasicEventCode.S_READ_IDLE_STATE_TIME_OUT,ServerBasicHandler_READ_IDLE_STATE_TIME_OUT.class);
        registerBasicEvent(BasicEventCode.CS_GET_GAME_LIST,ServerBasicHandler_GET_GAME_LIST.class);
        registerBasicEvent(BasicEventCode.CS_SET_INFO,ServerBasicHandler_SET_INFO.class);
        registerBasicEvent(BasicEventCode.CS_SET_NICKNAME,ServerBasicHandler_SET_NICKNAME.class);


    }

    public static <T extends BasicEventHandler> void registerBasicEvent(EventCode code,Class<T> hClazz){

        T handler=BasicEventHandler.createHandler(hClazz);
        if(handler==null){
            return;
        }
        Map<String,BasicEventHandler> baseHandler=events.get(0);
        if(code.isServerEvent()){
            baseHandler.put(code.getEventName(),handler);
        }

    }


    public static BasicEventHandler getServerEventHandler(String event){
        return getServerEventHandler(0,event);
    }


    public static BasicEventHandler getServerEventHandler(int gameId, String event)  {


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
