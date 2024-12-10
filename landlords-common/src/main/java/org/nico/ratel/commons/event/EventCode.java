package org.nico.ratel.commons.event;

/**
 * @author 柴奇君
 * @create 2024/12/6
 * @desc
 */
public interface EventCode {


    String getEventName();

    String getEventDesc();

    int getEventGameId();

    int getEventDir();








    class EventDir{
        /**
         * 消息方向
         * 1 client to server
         * 2 server inner
         * 3 server to client
         * 4 client inner
         */
        public static final int CS=1;
        public static final int S=2;
        public static final int SC=3;
        public static final int C=4;
    }

    default boolean isClientEvent(){
        return getEventDir()==EventDir.SC || getEventDir()==EventDir.C;
    }

    default boolean isServerEvent(){
        return getEventDir()==EventDir.CS || getEventDir()==EventDir.S;
    }


}
