package org.nico.ratel.commons.event;

import io.netty.channel.Channel;
import org.nico.ratel.commons.clientactor.BasicActor;

/**
 * @author 柴奇君
 * @create 2024/12/5
 * @desc
 */
public abstract class BasicEventHandler {

    public void call(Channel channel, String data){
        throw new RuntimeException("客户端需要实现具体消息处理逻辑");
    }

    public void call(BasicActor actor,String param){
        throw new RuntimeException("客户端需要实现具体消息处理逻辑");
    }

    public void initHandler() {}

    public static <T extends BasicEventHandler> T createHandler(Class<T> clazz) {
        try{
             T handler=clazz.newInstance();
             handler.initHandler();
             return handler;
        }catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        return null;

    }

}
