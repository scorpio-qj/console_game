package org.nico.ratel.client.event;

import io.netty.channel.Channel;
import org.nico.ratel.client.entity.User;
import org.nico.ratel.commons.event.BasicEventCode;
import org.nico.ratel.commons.event.BasicEventHandler;
import org.nico.ratel.commons.print.SimplePrinter;
import org.nico.ratel.commons.print.SimpleWriter;
import org.nico.ratel.commons.utils.OptionsUtils;

public class ClientBasicHandler_SHOW_OPTION_SETTING extends BasicEventHandler {

    @Override
    public void call(Channel channel, String data) {
        SimplePrinter.printNotice("Setting: ");
        SimplePrinter.printNotice("1. Card with shape edges (Default)");
        SimplePrinter.printNotice("2. Card with rounded edges");
        SimplePrinter.printNotice("3. Text Only with types");
        SimplePrinter.printNotice("4. Text Only without types");
        SimplePrinter.printNotice("5. Unicode Cards");

        SimplePrinter.printNotice("Please select an option above (enter [BACK] to return to options list)");
        String line = SimpleWriter.write(User.INSTANCE.getNickname(), "setting");

        if (OptionsUtils.CMD_BACK(line)) {
            ClientEventNavigation.getClientEventHandler(BasicEventCode.SC_SHOW_GLOBAL_OPTIONS.getEventName()).call(channel,data);
        } else {
            int choose = OptionsUtils.getOptions(line);

            if (choose >= OptionsUtils.OPTIONS_NUMBER.ONE_1 && choose <= OptionsUtils.OPTIONS_NUMBER.FIVE_5) {
                //PokerHelper.pokerPrinterType = choose - 1;
                ClientEventNavigation.getClientEventHandler(BasicEventCode.SC_SHOW_GLOBAL_OPTIONS.getEventName()).call(channel,data);
            } else {
                SimplePrinter.printNotice("Invalid setting, please choose again：");
                call(channel, data);
            }
        }
    }
}
