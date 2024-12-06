package org.nico.ratel.commons.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 柴奇君
 * @create 2024/12/5
 * @desc
 */
public enum BasicEventCode implements Events {



    SC_CONNECT("SC_CONNECT",2,"客户端连接成功",0),

    SC_DISCONNECT("SC_DISCONNECT",2,"客户端断开连接",0),

    SC_KICK("SC_KICK",2,"客户端被踢出",0),


    //------------------------------------------------------------------------------------------------------//

    CS_EXIT("CS_EXIT",1,"玩家退出",0),

    CS_OFFLINE("CS_OFFLINE",1,"玩家离线",0),

    CS_SET_INFO("CS_SET_INFO",1,"客户端设置信息",0),

    CS_SET_NICKNAME("CS_SET_NICKNAME",1,"设置昵称",0),

    CS_HEAD_BEAT("CS_HEAD_BEAT",1,"客户端心跳",0),



    ;

    /**
     * 消息方向
     * 1 client to server
     * 2 server to client
     */
    private int dir;

    private String desc;

    private String name;

    private int gameId;

    BasicEventCode(String name, int dir, String desc,int gameId) {
        this.name=name;
        this.dir=dir;
        this.desc=desc;
        this.gameId=gameId;
    }

    public int getDir() {
        return dir;
    }

    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }

    public int getGameId() {
        return gameId;
    }

    private static final Logger LOGGER= LoggerFactory.getLogger(BasicEventCode.class);

    private static final Map<String,BasicEventHandler> eventHandlers=new HashMap<>();

    /**
     * 注册事件处理器
     * @param event
     * @param handler
     */
    public static void registerEventHandler(String event,BasicEventHandler handler){

        if(eventHandlers.containsKey(event))
            return;

        try{
            BasicEventCode code= BasicEventCode.valueOf(event);
            eventHandlers.put(event,handler);
        }catch (IllegalArgumentException e){

            LOGGER.error("注册基础协议失败event："+event,e);
        }
    }

    public static BasicEventHandler getEventHandler(String event){
        return eventHandlers.get(event);
    }


    @Override
    public String getEventName() {
        return getName();
    }
}