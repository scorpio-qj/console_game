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
public enum BasicEventCode implements EventCode {



    CS_HEAD_BEAT("CS_HEAD_BEAT",1,"客户端心跳包",0),

    CS_EXIT("CS_EXIT",1,"玩家退出",0),

    CS_OFFLINE("CS_OFFLINE",1,"玩家离线",0),

    CS_SET_INFO("CS_SET_INFO",1,"客户端设置信息",0),

    CS_SET_NICKNAME("CS_SET_NICKNAME",1,"设置昵称",0),

    CS_GET_GAME_LIST("CS_GET_GAME_LIST",1,"获取游戏列表",0),

    //------------------------------------------------------------------------------------------------------//

    S_READ_IDLE_STATE_TIME_OUT("S_READ_IDLE_STATE_TIME_OUT",2,"读数据超时",0),

    S_PLAYER_EXIT("S_PLAYER_EXIT",2,"玩家退出",0),



    //------------------------------------------------------------------------------------------------------//

    SC_CONNECT("SC_CONNECT",3,"客户端连接成功",0),

    SC_DISCONNECT("SC_DISCONNECT",3,"客户端断开连接",0),

    SC_KICK("SC_KICK",3,"客户端被踢出",0),

    SC_SET_NICKNAME("SC_SET_NICKNAME",3,"设置昵称",0),

    SC_PLAYER_EXIT("SC_PLAYER_EXIT",3,"客户端退出",0),

    SC_SHOW_GLOBAL_OPTIONS("SC_SHOW_GLOBAL_OPTIONS",3,"游戏开始界面",0),

    SC_GAME_LIST("SC_GAME_LIST",3,"游戏列表",0),


    //------------------------------------------------------------------------------------------------------//

    C_GAME_WATCH("C_GAME_WATCH",4,"观战处理",0),

    C_SHOW_OPTION_SETTING("C_SHOW_OPTION_SETTING",4,"所有设置选项",0),

    ;

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
        return name;
    }

    @Override
    public String getEventDesc() {
        return desc;
    }

    @Override
    public int getEventGameId() {
        return gameId;
    }

    @Override
    public int getEventDir() {
        return dir;
    }


}
