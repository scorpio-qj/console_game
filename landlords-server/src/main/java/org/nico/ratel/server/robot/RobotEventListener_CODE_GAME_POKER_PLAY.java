package org.nico.ratel.server.robot;

import org.nico.noson.Noson;
import org.nico.ratel.commons.clientactor.ClientSide;
import org.nico.ratel.games.poker.doudizhu.DouDiZhuPokerSell;
import org.nico.ratel.commons.room.Room;
import org.nico.ratel.games.poker.doudizhu.DouDiZhuSellType;
import org.nico.ratel.commons.ServerEventCode;
import org.nico.ratel.commons.helper.PokerHelper;
import org.nico.ratel.commons.helper.TimeHelper;
import org.nico.ratel.commons.print.SimplePrinter;
import org.nico.ratel.commons.robot.RobotDecisionMakers;
import org.nico.ratel.server.ServerContains;
import org.nico.ratel.server.event.ServerEventListener;

public class RobotEventListener_CODE_GAME_POKER_PLAY implements RobotEventListener {

	@Override
	public void call(ClientSide robot, String data) {
		ServerContains.THREAD_EXCUTER.execute(() -> {
			Room room = ServerContains.getRoom(robot.getRoomId());

			DouDiZhuPokerSell lastDouDiZhuPokerSell = null;
			DouDiZhuPokerSell douDiZhuPokerSell = null;
			if (room.getLastSellClient() != robot.getId()) {
				lastDouDiZhuPokerSell = room.getLastPokerShell();
				douDiZhuPokerSell = RobotDecisionMakers.howToPlayPokers(room.getDifficultyCoefficient(), lastDouDiZhuPokerSell, robot);
			} else {
				douDiZhuPokerSell = RobotDecisionMakers.howToPlayPokers(room.getDifficultyCoefficient(), null, robot);
			}

			if (douDiZhuPokerSell != null && lastDouDiZhuPokerSell != null) {
				SimplePrinter.serverLog("Robot monitoring[room:" + room.getId() + "]");
				SimplePrinter.serverLog("last  sell  -> " + lastDouDiZhuPokerSell.toString());
				SimplePrinter.serverLog("robot sell  -> " + douDiZhuPokerSell.toString());
				SimplePrinter.serverLog("robot poker -> " + PokerHelper.textOnlyNoType(robot.getPokers()));
			}

			TimeHelper.sleep(300);

			if (douDiZhuPokerSell == null || douDiZhuPokerSell.getSellType() == DouDiZhuSellType.ILLEGAL) {
				ServerEventListener.get(ServerEventCode.CODE_GAME_POKER_PLAY_PASS).call(robot, data);
			} else {
				Character[] cs = new Character[douDiZhuPokerSell.getSellPokers().size()];
				for (int index = 0; index < cs.length; index++) {
					cs[index] = douDiZhuPokerSell.getSellPokers().get(index).getDesc().getAlias()[0];
				}
				ServerEventListener.get(ServerEventCode.CODE_GAME_POKER_PLAY).call(robot, Noson.reversal(cs));
			}
		});
	}
}
