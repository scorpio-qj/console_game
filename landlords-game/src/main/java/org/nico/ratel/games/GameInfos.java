package org.nico.ratel.games;

import org.nico.ratel.commons.BasicGameRule;
import org.nico.ratel.commons.ClientEventCode;
import org.nico.ratel.commons.event.BasicClientEventListener;
import org.nico.ratel.commons.event.ClientEvents;
import org.nico.ratel.games.poker.doudizhu.DouDiZhuGameRule;
import org.nico.ratel.games.poker.doudizhu.event.code.DouDiZhuClientEventCode;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 柴奇君
 * @create 2024/12/5
 * @desc 游戏信息类
 */
public enum GameInfos {

    /**
     * ----
     * gameId计算方式 gameType*10000+gameSubType
     * ----
     */

    /**
     * 斗地主
     */
    DOU_DI_ZHU(1000001,GAME_TYPE.POKER,1,new DouDiZhuGameRule(), DouDiZhuClientEventCode.class),


    ;


    private int gameId;

    private int gameType;

    private int gameSubType;

    private BasicGameRule gameRule;

    private Class<? extends ClientEvents> clazz;

    GameInfos(int gameId, int gameType, int gameSubType, BasicGameRule gameRule,Class<? extends ClientEvents> clazz) {
        this.gameId = gameId;
        this.gameType = gameType;
        this.gameSubType = gameSubType;
        this.gameRule = gameRule;
    }

    public int getGameId() {
        return gameId;
    }

    public int getGameType() {
        return gameType;
    }

    public int getGameSubType() {
        return gameSubType;
    }

    public <T> T getGameRule(Class<T> clazz) {
        return (T)gameRule;
    }

    public Class<? extends ClientEvents> getClazz() {
        return clazz;
    }

    private static final Map<Integer,GameInfos> idMap=new HashMap<>();

    static {
        for(GameInfos info:GameInfos.values()){
            idMap.put(info.getGameId(),info);
        }
    }

    public static BasicClientEventListener getEventListener(int gameId,String event){

        if(!idMap.containsKey(gameId)){
            return null;
        }
        Class<?> clazz=idMap.get(gameId).getClazz();

        ClientEvents[] events= (ClientEvents[]) clazz.getEnumConstants();
        for(ClientEvents item:events){

            if(item.getEventName().equals(event)){
                return item.getEventListener();
            }

        }

    }



    public static class GAME_TYPE{

        //扑克类
        public static final int POKER=100;

        //麻将类
        public static final int MAHJONG=101;
    }
}
