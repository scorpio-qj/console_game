package org.nico.ratel.server;

import org.nico.ratel.commons.ServerEventCode;

public class SimpleServer {

	public static void main(String[] args) throws InterruptedException {

		ServerEventCode code= ServerEventCode.valueOf("CODE_CLIENT_EXIT");
		System.out.println(code);

		/*
		if (args != null && args.length > 1) {
			if (args[0].equalsIgnoreCase("-p") || args[0].equalsIgnoreCase("-port")) {
				ServerContains.port = Integer.parseInt(args[1]);
			}
		}
		new Thread(() -> {
			try {
				new ProtobufProxy().start(ServerContains.port);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		new WebsocketProxy().start(ServerContains.port + 1);
		*/


	}
}
