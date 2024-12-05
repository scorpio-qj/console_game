package org.nico.ratel.robot;

import java.util.List;
import java.util.Random;

import org.nico.ratel.clientactor.ClientSide;
import org.nico.ratel.games.poker.Poker;
import org.nico.ratel.games.poker.doudizhu.DouDiZhuPokerSell;
import org.nico.ratel.games.poker.doudizhu.DouDiZhuSellType;
import org.nico.ratel.helper.PokerHelper;

/**
 * @author nico
 * @date 2018-11-15 12:13:49
 */
public class EasyRobotDecisionMakers extends AbstractRobotDecisionMakers {

	private static Random random = new Random();

	@Override
	public DouDiZhuPokerSell howToPlayPokers(DouDiZhuPokerSell lastDouDiZhuPokerSell, ClientSide robot) {
		if (lastDouDiZhuPokerSell != null && lastDouDiZhuPokerSell.getSellType() == DouDiZhuSellType.KING_BOMB) {
			return null;
		}

		List<DouDiZhuPokerSell> sells = PokerHelper.parsePokerSells(robot.getPokers());
		if (lastDouDiZhuPokerSell == null) {
			return sells.get(random.nextInt(sells.size()));
		}

		for (DouDiZhuPokerSell sell : sells) {
			if (sell.getSellType() == lastDouDiZhuPokerSell.getSellType()) {
				if (sell.getScore() > lastDouDiZhuPokerSell.getScore() && sell.getSellPokers().size() == lastDouDiZhuPokerSell.getSellPokers().size()) {
					return sell;
				}
			}
		}
		if (lastDouDiZhuPokerSell.getSellType() != DouDiZhuSellType.BOMB) {
			for (DouDiZhuPokerSell sell : sells) {
				if (sell.getSellType() == DouDiZhuSellType.BOMB) {
					return sell;
				}
			}
		}
		for (DouDiZhuPokerSell sell : sells) {
			if (sell.getSellType() == DouDiZhuSellType.KING_BOMB) {
				return sell;
			}
		}
		return null;
	}

	@Override
	public int getLandlordScore(List<Poker> leftPokers, List<Poker> rightPokers, List<Poker> myPokers) {
		List<DouDiZhuPokerSell> leftSells = PokerHelper.parsePokerSells(leftPokers);
		List<DouDiZhuPokerSell> mySells = PokerHelper.parsePokerSells(myPokers);
		List<DouDiZhuPokerSell> rightSells = PokerHelper.parsePokerSells(rightPokers);
		int expectedScore = 0;
		if (mySells.size() > leftSells.size()) {
			++expectedScore;
		}

		if (mySells.size() > rightSells.size()) {
			++expectedScore;
		}

		if (expectedScore != 0) {
			++expectedScore;
		}

		return expectedScore;
	 }
}
