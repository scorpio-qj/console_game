package org.nico.ratel.client.handler;

import org.nico.noson.Noson;
import org.nico.ratel.commons.event.BasicEventCode;
import org.nico.ratel.client.event.ClientEventNavigation;
import org.nico.ratel.commons.utils.ChannelUtils;
import org.nico.ratel.client.entity.User;
import org.nico.ratel.commons.proto.ClientTransferData.ClientTransferDataProtoc;
import org.nico.ratel.commons.print.SimplePrinter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.HashMap;
import java.util.Map;

public class ProtobufTransferHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {

		if (msg instanceof ClientTransferDataProtoc) {
			ClientTransferDataProtoc ct = (ClientTransferDataProtoc) msg;
			if (!ct.getInfo().isEmpty()) {
				SimplePrinter.printNotice(ct.getInfo());
			}

			if (User.INSTANCE.isWatching()) {
				Map<String, Object> wrapMap = new HashMap<>(3);
				wrapMap.put("code", ct.getCode());
				wrapMap.put("data", ct.getData());

				ClientEventNavigation.getClientEventHandler(BasicEventCode.C_GAME_WATCH.getEventName()).call(ctx.channel(),Noson.reversal(wrapMap));
			} else {
				ClientEventNavigation.getClientEventHandler(ct.getGameId(),ct.getCode()).call(ctx.channel(),ct.getData());
			}
		}
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {

		ClientEventNavigation.getClientEventHandler(BasicEventCode.SC_DISCONNECT.name()).call(ctx.channel(),"disconnect");

	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.WRITER_IDLE) {
				ChannelUtils.pushToServer(ctx.channel(), BasicEventCode.CS_HEAD_BEAT, "heartbeat");
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		if (cause instanceof java.io.IOException) {
			SimplePrinter.printNotice("The network is not good or did not operate for a long time, has been offline");
			System.exit(0);
		}
	}

}
