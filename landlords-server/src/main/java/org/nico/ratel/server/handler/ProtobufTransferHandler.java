package org.nico.ratel.server.handler;

import org.nico.ratel.commons.clientactor.BasicActor;
import org.nico.ratel.commons.clientactor.PlayerActor;
import org.nico.ratel.commons.event.BasicEventCode;
import org.nico.ratel.commons.event.EventCode;
import org.nico.ratel.commons.utils.ChannelUtils;
import org.nico.ratel.commons.proto.ServerTransferData.ServerTransferDataProtoc;
import org.nico.ratel.commons.print.SimplePrinter;
import org.nico.ratel.server.ServerContains;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.nico.ratel.server.event.ServerEventNavigation;
import org.nico.ratel.server.utils.ChannelAttributeKey;
import org.nico.ratel.server.utils.ChannelAttributeUtil;


public class ProtobufTransferHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) {

		BasicActor client=ServerContains.CLIENT_MAP.get(ChannelAttributeUtil.get(ctx.channel(),ChannelAttributeKey.CID));

		SimplePrinter.serverLog("client " + client.getId() + "(" + client.getNickName() + ") disconnected");
		clientOfflineEvent(ctx.channel());
		ctx.channel().close();
	}



	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();

		long clientId=ServerContains.getClientId(channel);
		PlayerActor playerActor=new PlayerActor(clientId,"",channel);
		ServerContains.CLIENT_MAP.put(clientId,playerActor);

		ChannelAttributeUtil.set(channel, ChannelAttributeKey.CID,clientId);

		SimplePrinter.serverLog("Has client connect to the server: " + clientId);

		ChannelUtils.pushToClient(channel, BasicEventCode.SC_CONNECT, String.valueOf(clientId));
		ChannelUtils.pushToClient(channel, BasicEventCode.SC_SET_NICKNAME, null);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof ServerTransferDataProtoc) {
			ServerTransferDataProtoc sd = (ServerTransferDataProtoc) msg;
			EventCode eventCode=ServerEventNavigation.getEventCode(sd.getGameId(),sd.getCode());
			//如果不为心跳包
			if (eventCode!=BasicEventCode.CS_HEAD_BEAT){
				BasicActor client = ServerContains.CLIENT_MAP.get(ServerContains.getClientId(ctx.channel()));
				SimplePrinter.serverLog(client.getId() + " | " + client.getNickName() + " do:" + eventCode.getEventDesc());
				ServerEventNavigation.getServerEventHandler(sd.getGameId(), sd.getCode()).call(client, sd.getData());
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


	private void clientOfflineEvent(Channel channel) {
		long clientId=ChannelAttributeUtil.get(channel,ChannelAttributeKey.CID);
		BasicActor client = ServerContains.CLIENT_MAP.get(clientId);
		if (client != null) {
			SimplePrinter.serverLog("Has client exit to the server：" + clientId + " | " + client.getNickName());
			ServerEventNavigation.getServerEventHandler(BasicEventCode.S_READ_IDLE_STATE_TIME_OUT.getEventName()).call(client,null);
		}
	}
}
