package org.nico.ratel.landlords.server.event;

import java.util.ArrayList;
import java.util.List;

import org.nico.noson.Noson;
import org.nico.ratel.ServerEventCode;
import org.nico.ratel.BasicEventCode;
import org.nico.ratel.BattleRoleType;
import org.nico.ratel.games.poker.doudizhu.DouDiZhuActorRoomState;
import org.nico.ratel.games.poker.doudizhu.DouDiZhuRoleType;
import org.nico.ratel.games.poker.doudizhu.DouDiZhuSellType;
import org.nico.ratel.room.enums.RoomStatus;
import org.nico.ratel.utils.ChannelUtils;
import org.nico.ratel.clientactor.ClientSide;
import org.nico.ratel.games.poker.Poker;
import org.nico.ratel.games.poker.doudizhu.DouDiZhuPokerSell;
import org.nico.ratel.room.Room;
import org.nico.ratel.helper.FeaturesHelper;
import org.nico.ratel.helper.MapHelper;
import org.nico.ratel.helper.PokerHelper;
import org.nico.ratel.print.SimplePrinter;
import org.nico.ratel.landlords.server.ServerContains;
import org.nico.ratel.landlords.server.robot.RobotEventListener;
import org.nico.ratel.utils.LastCardsUtils;

public class ServerEventListener_CODE_GAME_POKER_PLAY implements ServerEventListener {

	@Override
	public void call(ClientSide clientSide, String data) {
		Room room = ServerContains.getRoom(clientSide.getRoomId());
		if (room == null) {
			return;
		}
		if (room.getCurrentSellClient() != clientSide.getId()) {
			ChannelUtils.pushToClient(clientSide.getChannel(), BasicEventCode.CODE_GAME_POKER_PLAY_ORDER_ERROR, null);
			return;
		}
		Character[] options = Noson.convert(data, Character[].class);
		int[] indexes = PokerHelper.getIndexes(options, clientSide.getPokers());
		if (!PokerHelper.checkPokerIndex(indexes, clientSide.getPokers())) {
			ChannelUtils.pushToClient(clientSide.getChannel(), BasicEventCode.CODE_GAME_POKER_PLAY_INVALID, null);
			return;
		}

		boolean sellFlag = true;
		List<Poker> currentPokers = PokerHelper.getPoker(indexes, clientSide.getPokers());
		DouDiZhuPokerSell currentDouDiZhuPokerSell = PokerHelper.checkPokerType(currentPokers);
		if (currentDouDiZhuPokerSell.getSellType() == DouDiZhuSellType.ILLEGAL) {
			ChannelUtils.pushToClient(clientSide.getChannel(), BasicEventCode.CODE_GAME_POKER_PLAY_INVALID, null);
			return;
		}
		if (room.getLastSellClient() != clientSide.getId() && room.getLastPokerShell() != null) {
			DouDiZhuPokerSell lastDouDiZhuPokerSell = room.getLastPokerShell();

			if ((lastDouDiZhuPokerSell.getSellType() != currentDouDiZhuPokerSell.getSellType() || lastDouDiZhuPokerSell.getSellPokers().size() != currentDouDiZhuPokerSell.getSellPokers().size()) && currentDouDiZhuPokerSell.getSellType() != DouDiZhuSellType.BOMB && currentDouDiZhuPokerSell.getSellType() != DouDiZhuSellType.KING_BOMB) {
				String result = MapHelper.newInstance()
						.put("playType", currentDouDiZhuPokerSell.getSellType())
						.put("playCount", currentDouDiZhuPokerSell.getSellPokers().size())
						.put("preType", lastDouDiZhuPokerSell.getSellType())
						.put("preCount", lastDouDiZhuPokerSell.getSellPokers().size())
						.json();
				ChannelUtils.pushToClient(clientSide.getChannel(), BasicEventCode.CODE_GAME_POKER_PLAY_MISMATCH, result);
				return;
			}
			if (lastDouDiZhuPokerSell.getScore() >= currentDouDiZhuPokerSell.getScore()) {
				String result = MapHelper.newInstance()
						.put("playScore", currentDouDiZhuPokerSell.getScore())
						.put("preScore", lastDouDiZhuPokerSell.getScore())
						.json();
				ChannelUtils.pushToClient(clientSide.getChannel(), BasicEventCode.CODE_GAME_POKER_PLAY_LESS, result);
				return;
			}
		}

		ClientSide next = clientSide.getNext();

		clientSide.addRound();

		if (currentDouDiZhuPokerSell.getSellType() == DouDiZhuSellType.BOMB || currentDouDiZhuPokerSell.getSellType() == DouDiZhuSellType.KING_BOMB) {
			// 炸弹积分翻倍
			room.increaseRate();
		}

		room.setLastSellClient(clientSide.getId());
		room.setLastPokerShell(currentDouDiZhuPokerSell);
		room.setCurrentSellClient(next.getId());

		List<List<Poker>> lastPokerList = new ArrayList<>();
		for(int i = 0; i < room.getClientSideList().size(); i++){
			if(room.getClientSideList().get(i).getId() != clientSide.getId()){
				lastPokerList.add(room.getClientSideList().get(i).getPokers());
			}
		}
		String lastPokers = LastCardsUtils.getLastCards(lastPokerList);
		lastPokerList = new ArrayList<>();
		clientSide.getPokers().removeAll(currentPokers);
		MapHelper mapHelper = MapHelper.newInstance()
				.put("clientId", clientSide.getId())
				.put("clientNickname", clientSide.getNickname())
				.put("clientType", clientSide.getType())
				.put("pokers", currentPokers)
				.put("lastSellClientId", clientSide.getId())
				.put("lastSellPokers", currentPokers)
				.put("lastPokers",lastPokers);

		if (!clientSide.getPokers().isEmpty()) {
			mapHelper.put("sellClientNickname", next.getNickname());
		}

		String result = mapHelper.json();

		for (ClientSide client : room.getClientSideList()) {
			if (client.getRole() == BattleRoleType.PLAYER) {
				ChannelUtils.pushToClient(client.getChannel(), BasicEventCode.CODE_SHOW_POKERS, result);
			}
		}

		notifyWatcherPlayPoker(room, result);

		if (!clientSide.getPokers().isEmpty()) {
			if (next.getRole() == BattleRoleType.ROBOT) {
				RobotEventListener.get(BasicEventCode.CODE_GAME_POKER_PLAY).call(next, data);
			} else {
				ServerEventListener.get(ServerEventCode.CODE_GAME_POKER_PLAY_REDIRECT).call(next, result);
			}
			return;
		}

		gameOver(clientSide, room);
//        ServerEventListener.get(ServerEventCode.CODE_GAME_STARTING).call(clientSide, data);
	}

	private void setRoomClientScore(Room room, DouDiZhuRoleType winnerType) {
		int landLordScore = room.getScore() * 2;
		int peasantScore = room.getScore();
		// 输的一方分数为负
		if (winnerType == DouDiZhuRoleType.LANDLORD) {
			peasantScore = -peasantScore;
		} else {
			landLordScore = -landLordScore;
		}
		for (ClientSide client : room.getClientSideList()) {
			if (client.getType() == DouDiZhuRoleType.LANDLORD) {
				client.addScore(landLordScore);
			} else {
				client.addScore(peasantScore);
			}
		}
	}

	private void gameOver(ClientSide winner, Room room) {
		DouDiZhuRoleType winnerType = winner.getType();
		if (isSpring(winner, room)) {
			room.increaseRate();
		}

		setRoomClientScore(room, winnerType);

		ArrayList<Object> clientScores = new ArrayList<>();
		for (ClientSide client : room.getClientSideList()) {
			MapHelper score = MapHelper.newInstance()
					.put("clientId", client.getId())
					.put("nickName", client.getNickname())
					.put("score", client.getScore())
					.put("scoreInc", client.getScoreInc())
					.put("pokers", client.getPokers());
			clientScores.add(score.map());
		}

		SimplePrinter.serverLog(clientScores.toString());
		String result = MapHelper.newInstance()
				.put("winnerNickname", winner.getNickname())
				.put("winnerType", winner.getType())
				.put("scores", clientScores)
				.json();

		boolean supportReady = true;
		for (ClientSide client : room.getClientSideList()) {
			if (client.getRole() == BattleRoleType.ROBOT || ! FeaturesHelper.supported(client.getVersion(), FeaturesHelper.READY)) {
				supportReady = false;
				break;
			}
		}
		if (supportReady){
			room.setStatus(RoomStatus.WAIT);
			room.initScoreRate();
			for (ClientSide client : room.getClientSideList()) {
				client.setStatus(DouDiZhuActorRoomState.NO_READY);
				ChannelUtils.pushToClient(client.getChannel(), BasicEventCode.CODE_GAME_OVER, result);
			}
		}else{
			ServerEventListener.get(ServerEventCode.CODE_CLIENT_EXIT).call(winner, "");
		}
		notifyWatcherGameOver(room, result);
	}

	private boolean isSpring(ClientSide winner, Room room) {
		boolean isSpring = true;
		for (ClientSide client: room.getClientSideList()) {
			if (client.getType() == winner.getType()) {
				continue;
			}
			if (client.getType() == DouDiZhuRoleType.PEASANT && client.getRound() > 0) {
				isSpring = false;
			}
			if (client.getType() == DouDiZhuRoleType.LANDLORD && client.getRound() > 1) {
				isSpring = false;
			}
		}
		return isSpring;
	}

	/**
	 * 通知观战者出牌信息
	 *
	 * @param room	房间
	 * @param result	出牌信息
	 */
	private void notifyWatcherPlayPoker(Room room, String result) {
		for (ClientSide watcher : room.getWatcherList()) {
			ChannelUtils.pushToClient(watcher.getChannel(), BasicEventCode.CODE_SHOW_POKERS, result);
		}
	}

	/**
	 * 通知观战者游戏结束
	 *
	 * @param room	房间
	 * @param result	出牌信息
	 */
	private void notifyWatcherGameOver(Room room, String  result) {
		for (ClientSide watcher : room.getWatcherList()) {
			ChannelUtils.pushToClient(watcher.getChannel(), BasicEventCode.CODE_GAME_OVER, result);
		}
	}
}
