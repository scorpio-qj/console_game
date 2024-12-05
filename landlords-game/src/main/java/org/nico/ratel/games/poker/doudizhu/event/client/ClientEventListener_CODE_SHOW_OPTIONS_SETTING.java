package org.nico.ratel.games.poker.doudizhu.event.client;

import org.nico.ratel.client.entity.User;
import org.nico.ratel.commons.ClientEventCode;
import org.nico.ratel.commons.helper.PokerHelper;
import org.nico.ratel.commons.print.SimplePrinter;
import org.nico.ratel.commons.print.SimpleWriter;
import org.nico.ratel.commons.utils.OptionsUtils;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_SHOW_OPTIONS_SETTING extends ClientEventListener {

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

		if (line.equalsIgnoreCase("BACK")) {
			ClientEventListener.get(ClientEventCode.CODE_SHOW_OPTIONS).call(channel, data);
		} else {
			int choose = OptionsUtils.getOptions(line);

			if (choose >= 1 && choose <= 5) {
				PokerHelper.pokerPrinterType = choose - 1;
				ClientEventListener.get(ClientEventCode.CODE_SHOW_OPTIONS).call(channel, data);
			} else {
				SimplePrinter.printNotice("Invalid setting, please choose again：");
				call(channel, data);
			}
		}
	}


}
