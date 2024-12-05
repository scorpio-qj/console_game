package org.nico.ratel.commons;

import org.nico.ratel.games.poker.doudizhu.DouDiZhuGameRule;

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
    DOU_DI_ZHU(1000001,GAME_TYPE.POKER,1,new DouDiZhuGameRule()),
    ;


    private int gameId;

    private int gameType;

    private int gameSubType;

    private BasicGameRule gameRule;

    GameInfos(int gameId, int gameType, int gameSubType, BasicGameRule gameRule) {
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

    public static class GAME_TYPE{

        //扑克类
        public static final int POKER=100;

        //麻将类
        public static final int MAHJONG=101;
    }
}
