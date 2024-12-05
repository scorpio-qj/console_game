package org.nico.ratel.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.nico.ratel.commons.utils.ChannelUtils;
import org.nico.ratel.commons.clientactor.ClientSide;
import org.nico.ratel.commons.msg.Msg;
import org.nico.ratel.commons.ClientEventCode;
import org.nico.ratel.commons.BattleRoleType;
import org.nico.ratel.games.poker.doudizhu.DouDiZhuActorRoomState;
import org.nico.ratel.commons.ServerEventCode;
import org.nico.ratel.commons.print.SimplePrinter;
import org.nico.ratel.server.ServerContains;
import org.nico.ratel.server.event.ServerEventListener;
import org.nico.ratel.commons.utils.JsonUtils;

import java.util.Objects;

public class WebsocketTransferHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
        Msg msg = JsonUtils.fromJson(frame.text(), Msg.class);
        ServerEventCode code = ServerEventCode.valueOf(msg.getCode());
        if (!Objects.equals(code, ServerEventCode.CODE_CLIENT_HEAD_BEAT)) {
            ClientSide client = ServerContains.CLIENT_SIDE_MAP.get(getId(ctx.channel()));
            SimplePrinter.serverLog(client.getId() + " | " + client.getNickname() + " do:" + code.getMsg());
            ServerEventListener.get(code).call(client, msg.getData());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof java.io.IOException) {
            clientOfflineEvent(ctx.channel());
        } else {
            SimplePrinter.serverLog("ERROR：" + cause.getMessage());
            cause.printStackTrace();
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
                } catch (Exception e) {
                }
            }
        } else if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            Channel ch = ctx.channel();
            // init client info
            ClientSide clientSide = new ClientSide(getId(ctx.channel()), DouDiZhuActorRoomState.TO_CHOOSE, ch);
            clientSide.setNickname(String.valueOf(clientSide.getId()));
            clientSide.setRole(BattleRoleType.PLAYER);

            ServerContains.CLIENT_SIDE_MAP.put(clientSide.getId(), clientSide);
            SimplePrinter.serverLog("Has client connect to the server：" + clientSide.getId());
            new Thread(() -> {
                try {
                    Thread.sleep(2000L);
                    ChannelUtils.pushToClient(ch, ClientEventCode.CODE_CLIENT_CONNECT, String.valueOf(clientSide.getId()));
                    ChannelUtils.pushToClient(ch, ClientEventCode.CODE_CLIENT_NICKNAME_SET, null);
                } catch (InterruptedException ignored) {
                }
            }).start();
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
