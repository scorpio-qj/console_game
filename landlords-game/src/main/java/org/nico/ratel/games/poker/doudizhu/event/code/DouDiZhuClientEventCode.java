package org.nico.ratel.games.poker.doudizhu.event.code;

import org.nico.ratel.commons.event.BasicEventHandler;
import org.nico.ratel.commons.event.EventCode;

import java.io.Serializable;

/**
 * @author 柴奇君
 * @create 2024/12/5
 * @desc
 */
public enum DouDiZhuClientEventCode implements EventCode, Serializable {


    CODE_CLIENT_NICKNAME_SET("设置昵称",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_CLIENT_EXIT("客户端退出",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_CLIENT_KICK("客户端被踢出",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_CLIENT_CONNECT("客户端加入成功",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_SHOW_OPTIONS("全局选项列表",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_SHOW_OPTIONS_SETTING("设置选项",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_SHOW_OPTIONS_PVP("玩家对战选项",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_SHOW_OPTIONS_PVE("人机对战选项",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_SHOW_ROOMS("展示房间列表",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_SHOW_POKERS("展示Poker",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_ROOM_CREATE_SUCCESS("创建房间成功",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_ROOM_JOIN_SUCCESS("加入房间成功",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_ROOM_JOIN_FAIL_BY_FULL("房间人数已满",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_ROOM_JOIN_FAIL_BY_INEXIST("加入-房间不存在",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_ROOM_PLAY_FAIL_BY_INEXIST1("出牌-房间不存在",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_GAME_STARTING("开始游戏",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_GAME_LANDLORD_ELECT("抢地主",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_GAME_LANDLORD_CONFIRM("地主确认",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_GAME_LANDLORD_CYCLE("地主一轮确认结束",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_GAME_POKER_PLAY("出牌回合",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_GAME_POKER_PLAY_REDIRECT("出牌重定向",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_GAME_POKER_PLAY_MISMATCH("出牌不匹配",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_GAME_POKER_PLAY_LESS("出牌太小",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_GAME_POKER_PLAY_PASS("不出",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_GAME_POKER_PLAY_CANT_PASS("不允许不出",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_GAME_POKER_PLAY_INVALID("无效",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_GAME_POKER_PLAY_ORDER_ERROR("顺序错误",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_GAME_OVER("游戏结束",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_PVE_DIFFICULTY_NOT_SUPPORT("人机难度不支持",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_GAME_READY("准备开始游戏",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_GAME_WATCH("观战",new ClientEventListener_CODE_CLIENT_CONNECT()),

    CODE_GAME_WATCH_SUCCESSFUL("观战成功",new ClientEventListener_CODE_CLIENT_CONNECT());

    private String msg;

    private BasicEventHandler listener;

    DouDiZhuClientEventCode(String msg, BasicEventHandler listener) {
        this.msg = msg;
        this.listener=listener;
    }

    public final String getEventDesc() {
        return msg;
    }

    public BasicEventHandler getListener() {
        return listener;
    }


    @Override
    public String getEventName() {
        return name();
    }
}
