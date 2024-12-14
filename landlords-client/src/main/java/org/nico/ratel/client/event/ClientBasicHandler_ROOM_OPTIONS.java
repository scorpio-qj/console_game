package org.nico.ratel.client.event;

import io.netty.channel.Channel;
import org.nico.noson.Noson;
import org.nico.noson.entity.NoType;
import org.nico.ratel.client.entity.User;
import org.nico.ratel.commons.clientactor.BasicActor;
import org.nico.ratel.commons.event.BasicEventCode;
import org.nico.ratel.commons.event.BasicEventHandler;
import org.nico.ratel.commons.print.FormatPrinter;
import org.nico.ratel.commons.print.SimplePrinter;
import org.nico.ratel.commons.print.SimpleWriter;
import org.nico.ratel.commons.utils.ChannelUtils;
import org.nico.ratel.commons.utils.OptionsUtils;
import org.nico.ratel.commons.utils.ProtoDataUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientBasicHandler_ROOM_OPTIONS extends BasicEventHandler {

    @Override
    public void call(Channel channel, String data) {

        Map<String,String> gameInfo = ProtoDataUtils.toObject(data, Map.class);
        if(gameInfo!=null && !gameInfo.isEmpty()){
            String gameId=gameInfo.get("gameId");
            String name=gameInfo.get("name");
            SimplePrinter.printNotice("{} Options: ",name);
            SimplePrinter.printNotice("1. Create Room");
            SimplePrinter.printNotice("2. Room List");
            SimplePrinter.printNotice("2. Join Room");
            SimplePrinter.printNotice("please input number to choose or input back");
            String line = SimpleWriter.write(User.INSTANCE.getNickname(), "");

            if(OptionsUtils.CMD_BACK(line)) {
                ChannelUtils.pushToServer(channel, BasicEventCode.CS_GET_GAME_LIST,null);
            } else {
                int choose = OptionsUtils.getOptions(line);
                if(choose==OptionsUtils.OPTIONS_NUMBER.ONE_1) {
                    ChannelUtils.pushToServer(channel,BasicEventCode.CS_CREATE_ROOM,ProtoDataUtils.toString(gameId));
                }else if(choose==OptionsUtils.OPTIONS_NUMBER.TWO_2) {
                    ChannelUtils.pushToServer(channel,BasicEventCode.CS_GET_ROOM_LIST,ProtoDataUtils.toString(gameId));
                }else if(choose==OptionsUtils.OPTIONS_NUMBER.THREE_3) {

                }

            }
        }else{
            SimplePrinter.printNotice("No available game. Please wait update!");
            ChannelUtils.pushToServer(channel, BasicEventCode.CS_GET_GAME_LIST,null);
        }


    }
}
