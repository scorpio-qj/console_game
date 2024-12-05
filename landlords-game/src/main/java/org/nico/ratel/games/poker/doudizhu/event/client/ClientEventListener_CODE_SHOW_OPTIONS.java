package org.nico.ratel.games.poker.doudizhu.event.client;

import org.nico.ratel.client.entity.User;
import org.nico.ratel.commons.ClientEventCode;
import org.nico.ratel.commons.print.SimplePrinter;
import org.nico.ratel.commons.print.SimpleWriter;
import org.nico.ratel.commons.utils.OptionsUtils;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_SHOW_OPTIONS extends ClientEventListener {

	@Override
	public void call(Channel channel, String data) {
		SimplePrinter.printNotice("Options: ");
		SimplePrinter.printNotice("1. PvP");
		SimplePrinter.printNotice("2. PvE");
		SimplePrinter.printNotice("3. Settings");
		SimplePrinter.printNotice("Please select an option above (enter [exit|e] to log out)");
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
