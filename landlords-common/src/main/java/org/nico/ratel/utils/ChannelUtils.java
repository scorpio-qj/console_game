package org.nico.ratel.utils;

import org.nico.ratel.BasicEventCode;
import org.nico.ratel.ServerEventCode;
import org.nico.ratel.proto.ClientTransferData;
import org.nico.ratel.msg.Msg;
import org.nico.ratel.proto.ServerTransferData;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class ChannelUtils {

	public static void pushToClient(Channel channel, BasicEventCode code, String data) {
		pushToClient(channel, code, data, null);
	}

	public static void pushToClient(Channel channel, BasicEventCode code, String data, String info) {
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
