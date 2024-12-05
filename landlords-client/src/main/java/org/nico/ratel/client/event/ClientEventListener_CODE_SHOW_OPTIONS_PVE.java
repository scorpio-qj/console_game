package org.nico.ratel.client.event;

import org.nico.ratel.client.entity.User;
import org.nico.ratel.commons.BasicEventCode;
import org.nico.ratel.commons.ServerEventCode;
import org.nico.ratel.commons.print.SimplePrinter;
import org.nico.ratel.commons.print.SimpleWriter;
import org.nico.ratel.commons.utils.OptionsUtils;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_SHOW_OPTIONS_PVE extends ClientEventListener {

	@Override
	public void call(Channel channel, String data) {
		SimplePrinter.printNotice("PVE: ");
		SimplePrinter.printNotice("1. Easy Mode");
		SimplePrinter.printNotice("2. Medium Mode");
		SimplePrinter.printNotice("3. Hard Mode");
		SimplePrinter.printNotice("Please select an option above (enter [back|b] to return to options list)");
		String line = SimpleWriter.write(User.INSTANCE.getNickname(), "pve");

		if(line.equalsIgnoreCase("back") ||  line.equalsIgnoreCase("b")) {
			get(BasicEventCode.CODE_SHOW_OPTIONS).call(channel, data);
			return;
		}

		int choose = OptionsUtils.getOptions(line);

		if (choose < 0 || choose >= 4) {
			SimplePrinter.printNotice("Invalid option, please choose again：");
			call(channel, data);
			return;
		}
		initLastSellInfo();
		pushToServer(channel, ServerEventCode.CODE_ROOM_CREATE_PVE, String.valueOf(choose));
	}
}
