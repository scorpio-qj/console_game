package org.nico.ratel.server.event;

import org.nico.ratel.commons.clientactor.BasicActor;
import org.nico.ratel.commons.event.BasicEventHandler;
import org.nico.ratel.commons.utils.JsonUtils;

import java.util.Map;

public class ServerBasicHandler_SET_INFO extends BasicEventHandler {

    @Override
    public void call(BasicActor actor, String data) {

        Map<?,?> infos = JsonUtils.fromJson(data, Map.class);
        if (infos.containsKey("version")){
            actor.setVersion(String.valueOf(infos.get("version")));
        }
    }
}
