package org.nico.ratel.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.nico.ratel.client.entity.User;
import org.nico.ratel.commons.utils.ChannelUtils;
import org.nico.ratel.commons.msg.Msg;
import org.nico.ratel.commons.ClientEventCode;
import org.nico.ratel.commons.ServerEventCode;
import org.nico.ratel.commons.print.SimplePrinter;
import org.nico.ratel.commons.utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;

public class WebsocketTransferHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame)  {
		//Msg msg = JsonUtils.fromJson(frame.text(), Msg.class);
		//if(msg.getInfo() != null && ! msg.getInfo().isEmpty()) {
		//	SimplePrinter.printNotice(msg.getInfo());
		//}
		//ClientEventCode code = ClientEventCode.valueOf(msg.getCode());
		//if (User.INSTANCE.isWatching()) {
		//	Map<String, Object> wrapMap = new HashMap<>(3);
		//	wrapMap.put("code", code);
		//	wrapMap.put("data", msg.getData());
		//	ClientEventListener.get(ClientEventCode.CODE_GAME_WATCH).call(ctx.channel(), JsonUtils.toJson(wrapMap));
		//} else {
		//	ClientEventListener.get(code).call(ctx.channel(), msg.getData());
		//}
	}

	@Override  
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {  
		//if (evt instanceof IdleStateEvent) {
		//	IdleStateEvent event = (IdleStateEvent) evt;
		//	if (event.state() == IdleState.WRITER_IDLE) {
		//		ChannelUtils.pushToServer(ctx.channel(), ServerEventCode.CODE_CLIENT_HEAD_BEAT, "heartbeat");
		//	}
		//}
	} 
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		//if(cause instanceof java.io.IOException) {
		//	SimplePrinter.printNotice("The network is not good or did not operate for a long time, has been offline");
		//	System.exit(0);
		//}
	}

}
