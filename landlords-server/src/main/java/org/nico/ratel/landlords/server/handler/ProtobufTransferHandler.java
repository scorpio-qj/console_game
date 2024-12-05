package org.nico.ratel.landlords.server.handler;

import org.nico.ratel.utils.ChannelUtils;
import org.nico.ratel.client.ClientSide;
import org.nico.ratel.proto.ServerTransferData.ServerTransferDataProtoc;
import org.nico.ratel.client.enums.ClientEventCode;
import org.nico.ratel.client.enums.ClientRole;
import org.nico.ratel.client.enums.ClientStatus;
import org.nico.ratel.ServerEventCode;
import org.nico.ratel.print.SimplePrinter;
import org.nico.ratel.landlords.server.ServerContains;
import org.nico.ratel.landlords.server.event.ServerEventListener;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class ProtobufTransferHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) {
		ClientSide client = ServerContains.CLIENT_SIDE_MAP.get(getId(ctx.channel()));
		SimplePrinter.serverLog("client " + client.getId() + "(" + client.getNickname() + ") disconnected");
		clientOfflineEvent(ctx.channel());
		ctx.channel().close();
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();

		//init client info
		ClientSide clientSide = new ClientSide(getId(ctx.channel()), ClientStatus.TO_CHOOSE, channel);
		clientSide.setNickname(String.valueOf(clientSide.getId()));
		clientSide.setRole(ClientRole.PLAYER);

		ServerContains.CLIENT_SIDE_MAP.put(clientSide.getId(), clientSide);
		SimplePrinter.serverLog("Has client connect to the server: " + clientSide.getId());

		ChannelUtils.pushToClient(channel, ClientEventCode.CODE_CLIENT_CONNECT, String.valueOf(clientSide.getId()));
		ChannelUtils.pushToClient(channel, ClientEventCode.CODE_CLIENT_NICKNAME_SET, null);
	}


	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof ServerTransferDataProtoc) {
			ServerTransferDataProtoc serverTransferData = (ServerTransferDataProtoc) msg;
			ServerEventCode code = ServerEventCode.valueOf(serverTransferData.getCode());
			if (code != ServerEventCode.CODE_CLIENT_HEAD_BEAT) {
				ClientSide client = ServerContains.CLIENT_SIDE_MAP.get(getId(ctx.channel()));
				SimplePrinter.serverLog(client.getId() + " | " + client.getNickname() + " do:" + code.getMsg());
				ServerEventListener.get(code).call(client, serverTransferData.getData());
			}
		}
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE) {
				try {
					clientOfflineEvent(ctx.channel());
					ctx.channel().close();
				} catch (Exception ignore) {
				}
			}
		} else {
			super.userEventTriggered(ctx, evt);
		}
	}

	private int getId(Channel channel) {
		String longId = channel.id().asLongText();
		Integer clientId = ServerContains.CHANNEL_ID_MAP.get(longId);
		if (null == clientId) {
			clientId = ServerContains.getClientId();
			ServerContains.CHANNEL_ID_MAP.put(longId, clientId);
		}
		return clientId;
	}

	private void clientOfflineEvent(Channel channel) {
		int clientId = getId(channel);
		ClientSide client = ServerContains.CLIENT_SIDE_MAP.get(clientId);
		if (client != null) {
			SimplePrinter.serverLog("Has client exit to the server：" + clientId + " | " + client.getNickname());
			ServerEventListener.get(ServerEventCode.CODE_CLIENT_OFFLINE).call(client, null);
		}
	}
}
