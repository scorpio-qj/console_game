package org.nico.ratel.commons.utils;

import org.nico.ratel.commons.ClientEventCode;
import org.nico.ratel.commons.ServerEventCode;
import org.nico.ratel.commons.proto.ClientTransferData;
import org.nico.ratel.commons.msg.Msg;
import org.nico.ratel.commons.proto.ServerTransferData;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class ChannelUtils {

	public static void pushToClient(Channel channel, ClientEventCode code, String data) {
		pushToClient(channel, code, data, null);
	}

	public static void pushToClient(Channel channel, ClientEventCode code, String data, String info) {
		if (channel != null) {
			if (channel.pipeline().get("ws") != null) {
				Msg msg = new Msg();
				msg.setCode(code.toString());
				msg.setData(data);
				msg.setInfo(info);
				channel.writeAndFlush(new TextWebSocketFrame(JsonUtils.toJson(msg)));
			} else {
				ClientTransferData.ClientTransferDataProtoc.Builder clientTransferData = ClientTransferData.ClientTransferDataProtoc.newBuilder();
				if (code != null) {
					clientTransferData.setCode(code.toString());
				}
				if (data != null) {
					clientTransferData.setData(data);
				}
				if (info != null) {
					clientTransferData.setInfo(info);
				}
				channel.writeAndFlush(clientTransferData);
			}
		}
	}

	public static ChannelFuture pushToServer(Channel channel, ServerEventCode code, String data) {
		if (channel.pipeline().get("ws") != null) {
			Msg msg = new Msg();
			msg.setCode(code.toString());
			msg.setData(data);
			return channel.writeAndFlush(new TextWebSocketFrame(JsonUtils.toJson(msg)));
		} else {
			ServerTransferData.ServerTransferDataProtoc.Builder serverTransferData = ServerTransferData.ServerTransferDataProtoc.newBuilder();
			if (code != null) {
				serverTransferData.setCode(code.toString());
			}
			if (data != null) {
				serverTransferData.setData(data);
			}
			return channel.writeAndFlush(serverTransferData);
		}
	}

}
