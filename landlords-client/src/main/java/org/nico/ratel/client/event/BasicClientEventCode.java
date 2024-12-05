package org.nico.ratel.client.event;

import org.nico.ratel.commons.event.BasicClientEventHandler;
import org.nico.ratel.commons.event.ClientEvents;
import org.nico.ratel.games.poker.doudizhu.event.client.ClientEventListener_CODE_CLIENT_CONNECT;

/**
 * @author 柴奇君
 * @create 2024/12/5
 * @desc
 */
public enum BasicClientEventCode implements ClientEvents {

    CLIENT_CONNECT("客户端连接成功",new ClientBasicHandler_CLIENT_CONNECT()),

    CLIENT_DISCONNECT("客户端断开连接",new ClientBasicHandler_CLIENT_DISCONNECT()),

    CLIENT_KICK("客户端被踢出",new ClientEventListener_CODE_CLIENT_CONNECT()),

    ;


    private String msg;

    private BasicClientEventHandler listener;

    BasicClientEventCode(String msg, BasicClientEventHandler listener) {
        this.msg = msg;
        this.listener=listener;
    }

    public final String getMsg() {
        return msg;
    }

    public BasicClientEventHandler getListener() {
        return listener;
    }

    ;


    public static BasicClientEventHandler getEventListener(String event){
        //BasicClientEventCode basicClientEventCode=Enum.valueOf(BasicClientEventCode.class,event);
        BasicClientEventCode basicClientEventCode=BasicClientEventCode.valueOf(event);
        return basicClientEventCode.getEventListener();
    }

    @Override
    public BasicClientEventHandler getEventListener() {
        return null;
    }

    @Override
    public String getEventName() {
        return null;
    }
}
