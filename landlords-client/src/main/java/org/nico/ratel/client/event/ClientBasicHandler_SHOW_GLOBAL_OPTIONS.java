package org.nico.ratel.client.event;

import io.netty.channel.Channel;
import org.nico.ratel.client.entity.User;
import org.nico.ratel.commons.ClientEventCode;
import org.nico.ratel.commons.event.BasicEventHandler;
import org.nico.ratel.commons.print.SimplePrinter;
import org.nico.ratel.commons.print.SimpleWriter;
import org.nico.ratel.commons.utils.OptionsUtils;
import org.nico.ratel.games.poker.doudizhu.event.client.ClientEventListener;

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

        if(line.equalsIgnoreCase("exit") || line.equalsIgnoreCase("e")) {
            System.exit(0);
        } else {
            int choose = OptionsUtils.getOptions(line);
            if (choose == 1) {
                ClientEventListener.get(ClientEventCode.CODE_SHOW_OPTIONS_PVP).call(channel, data);
            } else if (choose == 2) {
                ClientEventListener.get(ClientEventCode.CODE_SHOW_OPTIONS_PVE).call(channel, data);
            } else if (choose == 3) {
                ClientEventListener.get(ClientEventCode.CODE_SHOW_OPTIONS_SETTING).call(channel, data);
            } else {
                SimplePrinter.printNotice("Invalid option, please choose again：");
                call(channel, data);
            }
        }
    }
}
