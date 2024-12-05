package org.nico.ratel.commons.robot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.nico.ratel.commons.clientactor.ClientSide;
import org.nico.ratel.games.poker.Poker;
import org.nico.ratel.games.poker.doudizhu.DouDiZhuPokerSell;
import org.nico.ratel.games.poker.doudizhu.DouDiZhuSellType;
import org.nico.ratel.commons.helper.PokerHelper;


/**
 * Trial game algorithm
 *
 * @author nico
 * @date 2020-12-19 16:36
 */
public class MediumRobotDecisionMakers extends AbstractRobotDecisionMakers {

	private static final Long DEDUCE_LIMIT = 100 * 3L;

	public MediumRobotDecisionMakers() {}

	@Override
	public DouDiZhuPokerSell howToPlayPokers(DouDiZhuPokerSell lastDouDiZhuPokerSell, ClientSide robot) {
		if(lastDouDiZhuPokerSell != null && lastDouDiZhuPokerSell.getSellType() == DouDiZhuSellType.KING_BOMB) {
			return null;
		}
		List<Poker> selfPoker = PokerHelper.clonePokers(robot.getPokers());
		List<Poker> leftPoker = PokerHelper.clonePokers(robot.getPre().getPokers());
		List<Poker> rightPoker = PokerHelper.clonePokers(robot.getNext().getPokers());
		PokerHelper.sortPoker(selfPoker);
		PokerHelper.sortPoker(leftPoker);
		PokerHelper.sortPoker(rightPoker);

		List<List<Poker>> pokersList = new ArrayList<List<Poker>>();
		pokersList.add(selfPoker);
		pokersList.add(rightPoker);
		pokersList.add(leftPoker);

		List<DouDiZhuPokerSell> sells = PokerHelper.validSells(lastDouDiZhuPokerSell, selfPoker);
		if(sells.size() == 0) {
			return null;
		}
		DouDiZhuPokerSell bestSell = null;
		Long weight = null;
		for(DouDiZhuPokerSell sell: sells) {
			List<Poker> pokers = PokerHelper.clonePokers(selfPoker);
			pokers.removeAll(sell.getSellPokers());
			if(pokers.size() == 0) {
				return sell;
			}
			pokersList.set(0, pokers);
			AtomicLong counter = new AtomicLong();
			deduce(0, sell, 1, pokersList, counter);
			if(weight == null) {
				bestSell = sell;
				weight = counter.get();
			}else if (counter.get() > weight){
				bestSell = sell;
				weight = counter.get();
			}
			pokersList.set(0, selfPoker);
		}
		return bestSell;
	}

	private Boolean deduce(int sellCursor, DouDiZhuPokerSell lastDouDiZhuPokerSell, int cursor, List<List<Poker>> pokersList, AtomicLong counter) {
		if(cursor > 2) {
			cursor = 0;
		}
		if(sellCursor == cursor) {
			lastDouDiZhuPokerSell = null;
		}

		List<Poker> original = pokersList.get(cursor);
		List<DouDiZhuPokerSell> sells = PokerHelper.validSells(lastDouDiZhuPokerSell, original);
		if(sells.size() == 0) {
			if(sellCursor != cursor) {
				return deduce(sellCursor, lastDouDiZhuPokerSell, cursor + 1, pokersList, counter);
			}
		}
		for(DouDiZhuPokerSell sell: sells) {
			List<Poker> pokers = PokerHelper.clonePokers(original);
			pokers.removeAll(sell.getSellPokers());
			if(pokers.size() == 0) {
				return cursor == 0;
			}else {
				pokersList.set(cursor, pokers);

				Boolean suc = deduce(cursor, sell, cursor + 1, pokersList, counter);
				if(cursor != 0) {
					pokersList.set(cursor, original);
					return suc;
				}
				if(Math.abs(counter.get()) > DEDUCE_LIMIT) {
					pokersList.set(cursor, original);
					return counter.get() > DEDUCE_LIMIT;
				}
				if(suc != null) {
					counter.addAndGet((long)(suc ? 1 : -1));
				}
				pokersList.set(cursor, original);
			}
		}
		return null;
	}

	private static String serialPokers(List<Poker> pokers){
		if(pokers == null || pokers.size() == 0) {
			return "n";
		}
		StringBuilder builder = new StringBuilder();
		for(int index = 0; index < pokers.size(); index ++) {
			builder.append(pokers.get(index).getDesc().getLevel()).append(index == pokers.size() - 1 ? "" : "_");
		}
		return builder.toString();
	}

	private static String serialPokersList(List<List<Poker>> pokersList){
		StringBuilder builder = new StringBuilder();
		for(int index = 0; index < pokersList.size(); index ++) {
			List<Poker> pokers = pokersList.get(index);
			builder.append(serialPokers(pokers)).append(index == pokersList.size() - 1 ? "" : "m");
		}
		return builder.toString();
	}

	@Override
	public int getLandlordScore(List<Poker> leftPokers, List<Poker> rightPokers, List<Poker> myPokers) {
		int leftScore = PokerHelper.parsePokerColligationScore(leftPokers);
		int rightScore = PokerHelper.parsePokerColligationScore(rightPokers);
		int myScore = PokerHelper.parsePokerColligationScore(myPokers);
		int expectedScore = 0;
		if (myScore >= Math.min(leftScore, rightScore)) {
			++expectedScore;
		}

		if (myScore * 2 >= leftScore + rightScore) {
			++expectedScore;
		}

		if (myScore >= Math.max(leftScore, rightScore)) {
			++expectedScore;
		}

		return expectedScore;
	}

}
