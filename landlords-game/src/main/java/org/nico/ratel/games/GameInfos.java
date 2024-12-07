package org.nico.ratel.games;

import org.nico.ratel.commons.BasicGameRule;
import org.nico.ratel.commons.event.BasicEventHandler;
import org.nico.ratel.commons.event.EventCode;
import org.nico.ratel.games.poker.doudizhu.DouDiZhuGameRule;
import org.nico.ratel.games.poker.doudizhu.event.code.DouDiZhuClientEventCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private Class<? extends EventCode> clazz;

    GameInfos(int gameId, int gameType, int gameSubType, BasicGameRule gameRule,Class<? extends EventCode> clazz) {
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

    public Class<? extends EventCode> getClazz() {
        return clazz;
    }

    private static final Logger LOGGER= LoggerFactory.getLogger(GameInfos.class);

    /**
     * 游戏idMap
     */
    private static final Map<Integer,GameInfos> idMap=new HashMap<>();

    /**
     * 游戏处理类
     */
    private static final Map<Integer,Map<String,BasicEventHandler>> gameHandlers=new HashMap<>();

    static {
        for(GameInfos info:GameInfos.values()){
            idMap.put(info.getGameId(),info);
            gameHandlers.put(info.getGameId(),new HashMap<>());
        }
    }

    /**
     * 为事件注册handler
     * @param gameId
     * @param name
     * @param handler
     */
    public static void registerGameHandler(int gameId,String name,BasicEventHandler handler){
        if(!gameHandlers.containsKey(gameId)){
            return;
        }
        if(getEventCode(gameId,name)==null){
            return;
        }
        gameHandlers.get(gameId).put(name,handler);

    }

    public static BasicEventHandler getHandler(int gameId,String name){

        if(!gameHandlers.containsKey(gameId)){
            return null;
        }
        return gameHandlers.get(gameId).get(name);

    }


    public static EventCode getEventCode(int gameId, String event) {

        if(!idMap.containsKey(gameId)){
            return null;
        }
        Class<?> clazz=idMap.get(gameId).getClazz();

        EventCode[] events= (EventCode[]) clazz.getEnumConstants();
        for(EventCode item:events){

            if(item.getEventName().equals(event)){
                return item;
            }

        }

        return null;

    }





    public static class GAME_TYPE{

        //扑克类
        public static final int POKER=100;

        //麻将类
        public static final int MAHJONG=101;
    }
}
