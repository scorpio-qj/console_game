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


    ;

    private int dir;

    private String desc;

    private String name;

    private int gameId;

    DouDiZhuClientEventCode(int dir, String desc, String name, int gameId) {
        this.dir = dir;
        this.desc = desc;
        this.name = name;
        this.gameId = gameId;
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
    public int getEventDir() { return dir; }

    @Override
    public String getEventName() {
        return name;
    }
}
