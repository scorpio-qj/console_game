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

    private String msg;

    private BasicEventHandler listener;

    DouDiZhuClientEventCode(String msg, BasicEventHandler listener) {
        this.msg = msg;
        this.listener=listener;
    }

    public final String getEventDesc() {
        return msg;
    }

    @Override
    public int getEventGameId() {
        return 0;
    }

    public BasicEventHandler getListener() {
        return listener;
    }


    @Override
    public String getEventName() {
        return name();
    }
}
