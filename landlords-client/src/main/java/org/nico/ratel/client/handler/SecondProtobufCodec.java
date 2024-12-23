package org.nico.ratel.client.handler;

import java.util.List;

import org.nico.ratel.commons.proto.ClientTransferData.ClientTransferDataProtoc;

import com.google.protobuf.MessageLite;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

public class SecondProtobufCodec extends MessageToMessageCodec<ClientTransferDataProtoc, MessageLite> {

	@Override
	protected void encode(ChannelHandlerContext ctx, MessageLite msg, List<Object> out) {
		out.add(msg);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ClientTransferDataProtoc msg, List<Object> out) {
		out.add(msg);
	}

}
