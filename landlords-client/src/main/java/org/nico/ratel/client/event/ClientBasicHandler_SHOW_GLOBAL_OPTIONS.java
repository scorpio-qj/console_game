package org.nico.ratel.client.event;

import io.netty.channel.Channel;
import org.nico.ratel.client.entity.User;
import org.nico.ratel.commons.event.BasicEventCode;
import org.nico.ratel.commons.event.BasicEventHandler;
import org.nico.ratel.commons.print.SimplePrinter;
import org.nico.ratel.commons.print.SimpleWriter;
import org.nico.ratel.commons.utils.ChannelUtils;
import org.nico.ratel.commons.utils.OptionsUtils;

/**
 * @author 柴奇君
 * @create 2024/12/9
 * @desc 起始界面选项
 */
public class ClientBasicHandler_SHOW_GLOBAL_OPTIONS extends BasicEventHandler {

    @Override
    public void call(Channel channel, String data) {
        SimplePrinter.printNotice("Options: ");
        SimplePrinter.printNotice("1. GameList");
        SimplePrinter.printNotice("2. Settings");
        SimplePrinter.printNotice("Please select an option above (enter [exit|e] to quit game)");
        String line = SimpleWriter.write(User.INSTANCE.getNickname(), "selection");

        if(OptionsUtils.CMD_EXIT(line)) {
            System.exit(0);
        } else {
            int choose = OptionsUtils.getOptions(line);
            if (choose == OptionsUtils.OPTIONS_NUMBER.ONE_1) {
                ChannelUtils.pushToServer(channel, BasicEventCode.CS_GET_GAME_LIST,null);
            } else if (choose == OptionsUtils.OPTIONS_NUMBER.TWO_2) {
                ClientEventNavigation.getClientEventHandler(BasicEventCode.C_SHOW_OPTION_SETTING.getEventName()).call(channel,data);
            } else {
                SimplePrinter.printNotice("Invalid option, please choose again：");
                call(channel, data);
            }
        }
    }
}
