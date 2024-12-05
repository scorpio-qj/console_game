package org.nico.ratel.client.event;

import org.nico.ratel.commons.event.BasicClientEventListener;
import org.nico.ratel.games.GameInfos;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 柴奇君
 * @create 2024/12/5
 * @desc
 */
public class ClientEventNavigation {

    /**
     * 公共命令字典
     */
    public static final Map<String, BasicClientEventListener> commonEvents=new ConcurrentHashMap<>();

    /**
     * 游戏命令字典 key gameId
     */
    public static final Map<Integer,Map<String,BasicClientEventListener>> gameEvents=new ConcurrentHashMap<>();



    public static BasicClientEventListener getClientEventListener(String event){
         return getClientEventListener(0,event);
    }


    public static BasicClientEventListener getClientEventListener(int gameId,String event){

        if(gameId==0){
            if(commonEvents.containsKey(event)){
                return commonEvents.get(event);
            }else {

                BasicClientEventListener listener=BasicClientEventCode.getEventListener(event);
                if(listener!=null){
                    commonEvents.putIfAbsent(event,listener);
                }
                return listener;
            }

        }else{
            if(gameEvents.containsKey(gameId) && gameEvents.get(gameId).containsKey(event)){
                return gameEvents.get(gameId).get(event);
            }else{

                BasicClientEventListener listener=GameInfos.getEventListener(gameId,event);
                if(listener!=null){
                    synchronized (gameEvents){
                        if(!gameEvents.containsKey(gameId)){
                            gameEvents.put(gameId,new ConcurrentHashMap<>());
                        }
                        if(!gameEvents.get(gameId).containsKey(event)){
                            gameEvents.get(gameId).put(event,listener);
                        }
                    }
                }
                return listener;

            }

        }

    }



}
