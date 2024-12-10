package org.nico.ratel.server;

import org.nico.ratel.commons.ServerEventCode;
import org.nico.ratel.server.proxy.ProtobufProxy;
import org.nico.ratel.server.proxy.WebsocketProxy;

public class SimpleServer {

	public static void main(String[] args) throws InterruptedException {


		if (args != null && args.length > 1) {
			if (args[0].equalsIgnoreCase("-p") || args[0].equalsIgnoreCase("-port")) {
				ServerConfig.port = Integer.parseInt(args[1]);
			}
		}
		new Thread(() -> {
			try {
				new ProtobufProxy().start(ServerConfig.port);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		new WebsocketProxy().start(ServerConfig.port + 1);



	}
}
