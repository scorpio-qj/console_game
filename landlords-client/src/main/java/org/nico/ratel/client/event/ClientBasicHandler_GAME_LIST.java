package org.nico.ratel.client.event;

import io.netty.channel.Channel;
import org.nico.noson.Noson;
import org.nico.noson.entity.NoType;
import org.nico.ratel.client.entity.User;
import org.nico.ratel.commons.event.BasicEventCode;
import org.nico.ratel.commons.event.BasicEventHandler;
import org.nico.ratel.commons.print.FormatPrinter;
import org.nico.ratel.commons.print.SimplePrinter;
import org.nico.ratel.commons.print.SimpleWriter;
import org.nico.ratel.commons.utils.OptionsUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 柴奇君
 * @create 2024/12/10
 * @desc
 */
public class ClientBasicHandler_GAME_LIST extends BasicEventHandler {

    @Override
    public void call(Channel channel, String data) {

        List<Map<String, Object>> gameList = Noson.convert(data, new NoType<List<Map<String, Object>>>() {});
        if (gameList != null && !gameList.isEmpty()) {

            String format = "#\t%s\t|\t%s\t|\t%s\t#\n";
            FormatPrinter.printNotice(format, "NUM", "ID", "NAME");
            Map<Integer,Map<String,Object>> temp=new HashMap<>();
            for(int i=1;i<=gameList.size();i++){
                Map<String,Object> game= gameList.get(i);
                temp.put(i,game);
                FormatPrinter.printNotice(format, i, game.get("gameId"), game.get("name"));
            }
            SimplePrinter.printNotice("");
            SimplePrinter.printNotice("please input number to choose game or back");
            String line = SimpleWriter.write(User.INSTANCE.getNickname(), "choose");

            if(OptionsUtils.CMD_BACK(line)) {
                ClientEventNavigation.getClientEventHandler(BasicEventCode.SC_SHOW_GLOBAL_OPTIONS.getEventName()).call(channel, data);
            } else {
                int choose = OptionsUtils.getOptions(line);
                if(!temp.containsKey(choose)){
                    SimplePrinter.printNotice("Invalid option, please choose again：");
                    call(channel, data);
                    return;
                }

                //todo 发送选择游戏协议
            }

        } else {
            SimplePrinter.printNotice("No available game. Please wait update!");
            ClientEventNavigation.getClientEventHandler(BasicEventCode.SC_SHOW_GLOBAL_OPTIONS.getEventName()).call(channel, data);
        }


    }
}
